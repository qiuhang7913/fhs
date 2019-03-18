package com.self.framework.base;

import com.self.framework.annotation.Trans;
import com.self.framework.nosql.redis.StringRedisService;
import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

@Service
public class TransServiceImpl<T extends BaseBean> implements TransService {

    @Autowired
    private StringRedisService redisService;

    @Override
    public void transMore(List listData) {
        listData.forEach(data ->{
            transOne((BaseBean) data);
        });
    }

    @Override
    public void transOne(BaseBean data) {
        boolean isBeTrans = data.getClass().isAnnotationPresent(Trans.class);
        if (isBeTrans){
            Field[] fields = data.getClass().getDeclaredFields();
            for (Field field:fields) {
                Trans trans = field.getAnnotation(Trans.class);
                if (ObjectCheckUtil.checkIsNullOrEmpty(trans)){
                    continue;
                }
                String redisQueryKey = trans.transKey() + data.getPkFileValue();
                String value = "";
                if (redisService.isExist(redisQueryKey)){
                    value = redisService.getObj(redisQueryKey);
                }
                data.setTransMap(new HashMap<>());
                data.getTransMap().put(field.getName() + "ToTransValue", value);
            }
        }
    }
}
