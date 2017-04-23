package com.zylu.handline.service.impl;

import io.rong.RongCloud;
import io.rong.messages.TxtMessage;
import io.rong.models.CodeSuccessReslut;
import io.rong.models.TokenReslut;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zylu.handline.bean.Friend;
import com.zylu.handline.bean.FriendRequest;
import com.zylu.handline.bean.User;
import com.zylu.handline.dao.IUserDao;
import com.zylu.handline.data.param.AddFriendParam;
import com.zylu.handline.data.param.CommonParam;
import com.zylu.handline.data.param.LoginParam;
import com.zylu.handline.data.param.RegistParam;
import com.zylu.handline.data.param.SearchUserParam;
import com.zylu.handline.data.result.CommonResult;
import com.zylu.handline.data.result.GetFriendsResult;
import com.zylu.handline.data.result.LoginResult;
import com.zylu.handline.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService{

	@Resource
	private IUserDao userDao;
	
	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	public LoginResult getUserByUn(LoginParam param) {
		
		User user = userDao.getUserByUn(param.getUserName());

		
		if(user != null) {
			if(user.getPassword().equals(param.getPassword())) {
				TokenReslut tr;
				try {
					tr = RongCloud.getInstance().user.getToken(user.getId(), user.getRealName(), param.getBasePath() + "logo.png");
				} catch (Exception e) {
					e.printStackTrace();
					return new LoginResult("连接融云失败");
				}
				
				if(tr == null || !tr.getCode().equals(200)) 
					return new LoginResult("连接融云失败");
				
				user.setPassword("");
				
				LoginResult lr = new LoginResult();
				lr.setUser(user);
				lr.setToken(tr.getToken());
				
				System.out.println(JSON.toJSONString(lr));
				return lr;
			} else {
				return new LoginResult("密码错误！");
			}
		}
		
		return new LoginResult("该账户不存在。");
	}

	@Override
	public LoginResult registUser(RegistParam param) {
		User user = param.getUser();
		
		User u = userDao.getUserByUn(user.getUserName());
		if(u == null) {
			user.setFriends(new ArrayList<Friend>());
			
			boolean flag = userDao.registUser(user);
			
			if(flag) return new LoginResult();
			
			return new LoginResult("添加用户失败，请稍后再试。");
		}
		
		return new LoginResult("该用户已存在");
	}

	@Override
	public LoginResult searchUser(SearchUserParam param) {
		User user = userDao.getUserByUn(param.getUserName());
		
		if(user != null) {
			LoginResult lr = new LoginResult();
			lr.setUser(user);
			
			return lr;
		}
		
		return new LoginResult("没有此用户");
	}

	@Override
	public CommonResult addFriends(AddFriendParam param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FriendRequest request = param.getRequest();
		User sUser = userDao.getUserByUn(request.getSponsor());
		User rUser = userDao.getUserByUn(request.getReceiver());
		
//		if(sUser.getId().equals(rUser.getId())) return new CommonResult("不能添加自己为好友");
		
		String content = "好友请求";
		Map<String,String> extra = new HashMap<String, String>();
		extra.put("userId", sUser.getId());
		extra.put("toUserId", rUser.getId());
		extra.put("msg", "收到" + sUser.getRealName() + "的好友请求");
		extra.put("time", sdf.format(new Date()));
		extra.put("sysId", "0");
		
		//融云推送
		RongCloud rongCloud = RongCloud.getInstance();
		String[] toUserId = {rUser.getId()};
		TxtMessage message = new TxtMessage(content, JSON.toJSONString(extra));
		CodeSuccessReslut messagePublishPrivateResult = null;
		try {
			messagePublishPrivateResult = rongCloud.message.publishPrivate("0", toUserId, message, "收到" + sUser.getRealName() + "的好友请求", "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
			
			if(messagePublishPrivateResult.getCode().intValue() != 200) {
				log.debug(messagePublishPrivateResult.getErrorMessage());
				return new CommonResult("信息发送失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(messagePublishPrivateResult.toString());
		}
		return new CommonResult();
	}

	@Override
	public CommonResult confirmFriend(AddFriendParam param) {
		boolean flag = userDao.confirmFriend(param.getRequest());
		
		if(flag) return new CommonResult();
		
		return new CommonResult("添加失败");
	}

	@Override
	public GetFriendsResult getFriends(CommonParam param) {
		
		List<User> friends = userDao.getFriends(param.getUserId());
		
		if(friends != null) return new GetFriendsResult(friends);
		
		return new GetFriendsResult("获取好友列表失败");
	}

	@Override
	public CommonResult updateInfo(RegistParam param) {
		User user = param.getUser();
		user.setId(param.getUserId());
		
		boolean updateInfo = userDao.updateInfo(user);
		
		if(updateInfo) return new CommonResult();
		
		return new CommonResult("网络异常，请稍候再试");
	}
	


}
