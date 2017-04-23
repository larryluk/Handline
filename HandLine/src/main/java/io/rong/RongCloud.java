/**
 * 融云 Server API java 客户端
 * create by kitName
 * create datetime : 2017-02-09 
 * 
 * v2.0.1
 */
package io.rong;

import java.util.concurrent.ConcurrentHashMap;

import com.zylu.handline.util.Constants;

import io.rong.methods.*;

public class RongCloud {

	private static ConcurrentHashMap<String, RongCloud> rongCloud = new ConcurrentHashMap<String,RongCloud>();
	
	public User user;
	public Message message;
	public Wordfilter wordfilter;
	public Group group;
	public Chatroom chatroom;
	public Push push;
	public SMS sms;

	private RongCloud(String appKey, String appSecret) {
		user = new User(appKey, appSecret);
		message = new Message(appKey, appSecret);
		wordfilter = new Wordfilter(appKey, appSecret);
		group = new Group(appKey, appSecret);
		chatroom = new Chatroom(appKey, appSecret);
		push = new Push(appKey, appSecret);
		sms = new SMS(appKey, appSecret);

	}

	public static RongCloud getInstance() {
		if (null == rongCloud.get(Constants.appKey)) {
			rongCloud.putIfAbsent(Constants.appKey, new RongCloud(Constants.appKey, Constants.appSecret));
		}
		return rongCloud.get(Constants.appKey);
	}
	 
}