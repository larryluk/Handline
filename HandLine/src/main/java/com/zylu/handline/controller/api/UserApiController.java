package com.zylu.handline.controller.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zylu.handline.data.param.AddFriendParam;
import com.zylu.handline.data.param.CommonParam;
import com.zylu.handline.data.param.LoginParam;
import com.zylu.handline.data.param.RegistParam;
import com.zylu.handline.data.param.SearchUserParam;
import com.zylu.handline.data.result.CommonResult;
import com.zylu.handline.data.result.GetFriendsResult;
import com.zylu.handline.data.result.LoginResult;
import com.zylu.handline.service.IUserService;

@RestController
@Scope("request")
public class UserApiController {
	
	@Resource
	private IUserService userService;
	
	@RequestMapping(value = "/api/login")
	public LoginResult login(HttpServletRequest request ,@RequestBody LoginParam param){
		String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath();
		String frontUrl = contextpath + "/static/";
		
		param.setBasePath(frontUrl);
		return userService.getUserByUn(param);
		
	}
	
	@RequestMapping(value = "/api/regist")
	public LoginResult regist(@RequestBody RegistParam param) {
		return userService.registUser(param);
	}
	
	
	@RequestMapping(value = "/api/searchUser")
	public LoginResult searchUser(@RequestBody SearchUserParam param){
		
		return userService.searchUser(param);
	}

	/**
	 * 确认好友接口
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/confirmFriend")
	public CommonResult confirmFriend(@RequestBody AddFriendParam param){
		return userService.confirmFriend(param);
	}
	
	/**
	 * 添加好友
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/addFriend")
	public CommonResult addFriend(@RequestBody AddFriendParam param) {
		return userService.addFriends(param);
	}
	
	/**
	 * 获取好友列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/getFriends")
	public GetFriendsResult getFriends(@RequestBody CommonParam param) {
		return userService.getFriends(param);
	}
	
	/**
	 * 更新个人信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/updateInfo")
	public CommonResult updateInfo(@RequestBody RegistParam param) {

		return userService.updateInfo(param);
	}
	
	
}
