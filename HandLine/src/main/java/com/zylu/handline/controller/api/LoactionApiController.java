package com.zylu.handline.controller.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zylu.handline.data.param.GetLocationParam;
import com.zylu.handline.data.param.SendLocationParam;
import com.zylu.handline.data.result.CommonResult;
import com.zylu.handline.service.ILocationService;

@RestController
@Scope("request")
public class LoactionApiController {
	Logger log = Logger.getLogger(LoactionApiController.class);

	@Resource
	private ILocationService locationService;
	
	/**
	 * 索要位置信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/getLocation")
	public CommonResult getLocation(@RequestBody GetLocationParam param) {
		log.debug("getLocation");
		return locationService.getLocation(param);
	}
	
	/**
	 * 发送位置信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/sendLocation")
	public CommonResult sendLocation(@RequestBody SendLocationParam param) {
		return locationService.sendLocation(param);
	}
}
