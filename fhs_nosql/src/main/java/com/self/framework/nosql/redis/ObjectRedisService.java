package com.self.framework.nosql.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @des redis对象化操作
 * @author qiuhang
 * @version v1.0
 * @refer  https://blog.csdn.net/a183400826/article/details/84789766
 * @param <T>
 */
@Service
public class ObjectRedisService<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisTemplate<String, T> redisTemplate;

    /**
     * redis 数据插入
     * @param key
     * @param v
     */
    public void insertObj(String key, T v){
        try {
            redisTemplate.opsForValue().set(key, v);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", JSON.toJSONString(v), e.getMessage());
        }
    }

    /**
     * redis 数据插入带失效时间
     * @param key
     * @param v
     */
    public void insertObj(String key, Long time, T v){
        try {
            redisTemplate.opsForValue().set(key, v);
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", JSON.toJSONString(v), e.getMessage());
        }
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public Boolean isExist(String key){
        try {
            return redisTemplate.hasKey(key);
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
    public T getObj(String key){
        try {
            if (isExist(key)){
                return redisTemplate.opsForValue().get(key);
            }
        }catch (Exception e){
            logger.error("数据查询异常!");
        }
        return null;
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
}
