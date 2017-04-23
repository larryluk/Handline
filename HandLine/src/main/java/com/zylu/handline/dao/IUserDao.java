package com.zylu.handline.dao;

import java.util.List;

import com.zylu.handline.bean.FriendRequest;
import com.zylu.handline.bean.User;

public interface IUserDao{

	/**
	 * 通过id获取用户
	 * @param id
	 * @return
	 */
	User getUserById(String id);
	
	/**
	 * 通过用户名查找用户
	 * @param s
	 * @return
	 */
	User getUserByUn(String s);
	
	/**
	 * 插入新用户
	 * @param user
	 * @return
	 */
	boolean registUser(User user);
	
	/**
	 * 确认好友
	 * @param param
	 * @return
	 */
	boolean confirmFriend(FriendRequest param);
	
	/**
	 * 获取好友列表
	 * @param userName
	 * @return
	 */
	List<User> getFriends(String userName);
	
	/**
	 * 
	 * @return
	 */
	boolean updateInfo(User param);
	
}
