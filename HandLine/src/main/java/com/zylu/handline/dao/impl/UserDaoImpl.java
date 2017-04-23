package com.zylu.handline.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.zylu.handline.bean.Friend;
import com.zylu.handline.bean.FriendRequest;
import com.zylu.handline.bean.User;
import com.zylu.handline.dao.IUserDao;
import com.zylu.handline.util.Util;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {
	
	@Resource
	private MongoTemplate mongoTemplate;  
	
	public User getUserByUn(String s) {
		Query query = new Query(Criteria.where("userName").is(s));
		User user =  mongoTemplate.findOne(query, User.class);
		return user;
	}

	@Override
	public boolean registUser(User user) {
		mongoTemplate.insert(user, "hlUser");
		return true;
	}

	@Override
	public boolean confirmFriend(FriendRequest param) {
		User sponsor = mongoTemplate.findById(param.getSponsor(), User.class);
		User receiver = mongoTemplate.findById(param.getReceiver(), User.class);
		
		if(sponsor != null && receiver != null) {
			if(sponsor.getFriends() == null) sponsor.setFriends(new ArrayList<Friend>());
			if(receiver.getFriends() == null) receiver.setFriends(new ArrayList<Friend>());
			
			//互相加为好友
			Friend f = new Friend(receiver.getId(), new String[]{"2"});
			sponsor.getFriends().add(f);
			f = new Friend(sponsor.getId(), new String[]{"2"});
			receiver.getFriends().add(f);
			
			//更新发送者用户信息
			mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(sponsor.getId())), 
					new Update().set("friends", sponsor.getFriends()), "hlUser");
			//更新接受者用户信息
			mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(receiver.getId())), 
					new Update().set("friends", receiver.getFriends()), "hlUser");
			
			return true;
		}
	
		
		return false;
	}

	@Override
	public List<User> getFriends(String userName) {
		User user = mongoTemplate.findById(userName, User.class);
		List<Friend> friends = user.getFriends();
		
		List<String> list = new ArrayList<String>();
		for(Friend f : friends) {
			list.add(f.getFriId());
		}
		
		Query query = new Query(Criteria.where("_id").in(list));
		List<User> fs = mongoTemplate.find(query, User.class);
		
		//过滤字段
		for(User s : fs) {
			s.setPassword("");
			s.setFriends(new ArrayList<Friend>());
		}
		
		return fs;
	}

	@Override
	public boolean updateInfo(User param) {
		Query query = new Query(Criteria.where("_id").is(param.getId()));
		Update update = null;
		if(!Util.isNullOrEmpty(param.getPassword())) {
			update = new Update().set("password", param.getPassword());
		}
		
		if(!Util.isNullOrEmpty(param.getRealName())) {
			update = new Update().set("realName", param.getRealName());
		}
		
		if(!Util.isNullOrEmpty(param.getEmail())) {
			update = new Update().set("email", param.getEmail());
		}
		
		if(update == null) return false;
		
		WriteResult updateFirst = mongoTemplate.updateFirst(query, update, "hlUser");
		
		return updateFirst.wasAcknowledged();
	}

	@Override
	public User getUserById(String id) {
		return mongoTemplate.findById(id, User.class, "hlUser");
	}
	
}
