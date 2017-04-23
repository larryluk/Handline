package com.zylu.handline.service;

import com.zylu.handline.data.param.AddFriendParam;
import com.zylu.handline.data.param.CommonParam;
import com.zylu.handline.data.param.LoginParam;
import com.zylu.handline.data.param.RegistParam;
import com.zylu.handline.data.param.SearchUserParam;
import com.zylu.handline.data.result.CommonResult;
import com.zylu.handline.data.result.GetFriendsResult;
import com.zylu.handline.data.result.LoginResult;

public interface IUserService {
	/**
	 * 登录
	 * @param param
	 * @return
	 */
	LoginResult getUserByUn(LoginParam param);
	
	
	/**
	 * 注册用户
	 * @param param
	 * @return
	 */
	LoginResult registUser(RegistParam param);
	
	/**
	 * 查询用户
	 * @param param
	 * @return
	 */
	LoginResult searchUser(SearchUserParam param);
	
	/**
	 * 发送好友请求
	 * @param param
	 * @return
	 */
	CommonResult addFriends(AddFriendParam param);
	
	/**
	 * 确认好友
	 * @param param
	 * @return
	 */
	CommonResult confirmFriend(AddFriendParam param);
	
	/**
	 * 获取当前用户的好友列表
	 * @param param
	 * @return
	 */
	GetFriendsResult getFriends(CommonParam param);
	
	/**
	 * 更新用户信息
	 * @param param
	 * @return
	 */
	CommonResult updateInfo(RegistParam param);
	
	
}
