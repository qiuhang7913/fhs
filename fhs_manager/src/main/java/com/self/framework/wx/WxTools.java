package com.self.framework.wx;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @Description: 微信工具类
 * @author  肖锐
 * @version [版本号, 2018-09-18]
 * @versio 1.0 深圳易联联盟科技有限公司  Copyright (c) 2018 All Rights Reserved.
 */
@Component
public class WxTools {

    /**
     *  mpservice
     */
    private WxMpService wxMpService;

    /**
     *  配置类
     */
    private WxMpInRedisConfigStorage config;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *  单例获取配置对象
     */
    public synchronized WxMpInRedisConfigStorage getWxMpConfig(){
        if (config == null){
            config = new WxMpInRedisConfigStorage(redisTemplate);
            config.setAppId("wx30681791f51bbdeb"); // 设置微信公众号的appid
            config.setSecret("77cd7d13b73319bb8cccab657f5551ac"); // 设置微信公众号的Secret
            config.setToken("mytoken");//token
            config.setAesKey("BhxlQpNVOY2drAJoVuLtnAWzi07MHlom4xu1imgsW8g");
         }
        return config;
    }

    /**
     *  单例获取MpService对象
     */
    public synchronized WxMpService getWxMpService(){
        if (wxMpService == null){

            wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(getWxMpConfig());
        }
        return wxMpService;
    }

}
