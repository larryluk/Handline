package com.zylu.handline.service;

import com.zylu.handline.data.param.GetLocationParam;
import com.zylu.handline.data.param.SendLocationParam;
import com.zylu.handline.data.result.CommonResult;

public interface ILocationService {

	/**
	 * 向对方询问位置信息
	 * @param param
	 * @return
	 */
	CommonResult getLocation(GetLocationParam param);
	
	/**
	 * 发送信息
	 * @param param
	 * @return
	 */
	CommonResult sendLocation(SendLocationParam param);
}
