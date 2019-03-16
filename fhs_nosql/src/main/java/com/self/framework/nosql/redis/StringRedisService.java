package com.self.framework.nosql.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class StringRedisService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * @param key
     * @param value
     */
    public void insertObj(String key,String value){
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", "key:" + key + "value:" + value, e.getMessage());
        }
    }

    /**
     * redis 数据插入带失效时间
     * @param key
     * @param value
     */
    public void insertObj(String key, Long time, String value){
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", "key:" + key + "value:" + value, e.getMessage());
        }
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public Boolean isExist(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("数据操作异常!");
        }
        return false;
    }

    /**
     *
     * @param key
     * @return
     */
    public String getObj(String key){
        String value = "";
        try {
           if (isExist(key)){
               value = stringRedisTemplate.opsForValue().get(key);
           }
        }catch (Exception e){
            logger.error("数据查询异常!");
        }
        return value;
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

}
