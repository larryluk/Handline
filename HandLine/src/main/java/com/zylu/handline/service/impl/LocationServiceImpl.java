package com.zylu.handline.service.impl;

import io.rong.RongCloud;
import io.rong.messages.TxtMessage;
import io.rong.models.CheckOnlineReslut;
import io.rong.models.CodeSuccessReslut;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zylu.handline.bean.User;
import com.zylu.handline.dao.IUserDao;
import com.zylu.handline.data.param.GetLocationParam;
import com.zylu.handline.data.param.SendLocationParam;
import com.zylu.handline.data.result.CommonResult;
import com.zylu.handline.service.ILocationService;

@Service("locationService")
public class LocationServiceImpl implements ILocationService {

	Logger log = Logger.getLogger(LocationServiceImpl.class);
	
	@Resource
	private IUserDao userDao;
	
	@Override
	public CommonResult getLocation(GetLocationParam param) {
		//融云推送
		RongCloud rongCloud = RongCloud.getInstance();
		
		User user = userDao.getUserById(param.getUserId());
		User toUser = userDao.getUserById(param.getToUserId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,String> extra = new HashMap<String, String>();
		extra.put("userId", user.getId());
		extra.put("toUserId", toUser.getId());
		extra.put("msg", user.getRealName() + "查看了你的位置");
		extra.put("time", sdf.format(new Date()));
		extra.put("sysId", "1");
		

		
		String[] toUserId = {toUser.getId()};
		TxtMessage message = new TxtMessage(extra.get("msg"), JSON.toJSONString(extra));
		CodeSuccessReslut messagePublishPrivateResult = null;
		try {
			messagePublishPrivateResult = rongCloud.message.publishPrivate("1", toUserId, message, extra.get("msg"), "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
			
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
	public CommonResult sendLocation(SendLocationParam param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,String> extra = new HashMap<String, String>();
		
		extra.put("latitude", String.valueOf(param.getLatitude()));
		extra.put("longitude", String.valueOf(param.getLongitude()));
		extra.put("poiName", param.getPoiName());
		extra.put("cityName", param.getCityName());
		extra.put("time", sdf.format(new Date()));
		extra.put("sysId", "2");
		
		//融云推送
		RongCloud rongCloud = RongCloud.getInstance();
		String[] toUserId = {param.getUserId()};
		TxtMessage message = new TxtMessage(JSON.toJSONString(extra), JSON.toJSONString(extra));
		CodeSuccessReslut messagePublishPrivateResult = null;
		try {
			messagePublishPrivateResult = rongCloud.message.publishPrivate("2", toUserId, message, JSON.toJSONString(extra), "{\"pushData\":\"hello\"}", "4", 0, 0, 0, 0);
			
			if(messagePublishPrivateResult.getCode().intValue() != 200) {
				log.debug(messagePublishPrivateResult.getErrorMessage());
				return new CommonResult("信息发送失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(messagePublishPrivateResult.toString());
		}
		
		return null;
	}
	

}
