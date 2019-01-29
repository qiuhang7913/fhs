package com.self.framework.spring.extend.jpa;

import com.self.framework.annotation.*;
import com.self.framework.base.BaseBean;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.exception.BusinessException;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import com.self.framework.utils.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @des0 sping jpa data 规范查询扩展，用于多条件特殊查询
 * @author qiuhang
 * @version v1.0
 */
@Component
public class SpecificationQueryExtend<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String BASE_BEAN_CLASS_NAME = "com.self.framework.base.BaseBean";
    private static final String LIKE_SQL_STR = "{1}{2}{3}";//1:left 2:val 3:rigth

    /**
     * @des: between 条件构造
     * @return
     */
    private Predicate obtainBetweenSqlWhere(Root<T> root, CriteriaBuilder cb, Map betweenMap, String fieldName){
        String startValue = ConvertDataUtil.convertStr(betweenMap.get("start_" + fieldName));
        String endValue = ConvertDataUtil.convertStr(betweenMap.get("end_" + fieldName).toString());
        return cb.between(root.get(fieldName).as(String.class), startValue, endValue);

    }

    /**
     * @des: Like 条件构造
     * @return
     */
    private Predicate obtainLikeSqlWhere(Root<T> root, CriteriaBuilder cb, String value, String fieldName){
        return cb.like(root.get(fieldName).as(String.class), LIKE_SQL_STR.replace("{1}", BusinessCommonConstamt.PERCENT_SIGN_STR)
                                                        .replace("{2}", value)
                                                        .replace("{3}", BusinessCommonConstamt.PERCENT_SIGN_STR));
    }

    /**
     * @des: left Like 条件构造
     * @return
     */
    private Predicate obtainLeftLikeSqlWhere(Root<T> root, CriteriaBuilder cb, String value, String fieldName){
        return cb.like(root.get(fieldName).as(String.class), LIKE_SQL_STR.replace("{1}", BusinessCommonConstamt.PERCENT_SIGN_STR)
                                                        .replace("{2}", value)
                                                        .replace("{3}", BusinessCommonConstamt.EMPTY_STR));
    }

    /**
     * @des: right Like 条件构造
     * @return
     */
    private Predicate obtainRigthLikeSqlWhere(Root<T> root, CriteriaBuilder cb, String value, String fieldName){
        return cb.like(root.get(fieldName).as(String.class), LIKE_SQL_STR.replace("{1}", BusinessCommonConstamt.EMPTY_STR)
                                                        .replace("{2}", value)
                                                        .replace("{3}", BusinessCommonConstamt.PERCENT_SIGN_STR));
    }

    /**
     * 一对一构造
     * @param root
     * @param cb
     * @param toOneClass
     * @param conditionFilenName
     * @return
     */
    private Predicate obtainOneToOneSqlWhere(Root<T> root, CriteriaBuilder cb, BaseBean toOneClass, String conditionFilenName) throws Exception{
        String tableName = toOneClass.getClass().getAnnotation(Entity.class).name();
        root.join(root.getModel().getSingularAttribute(tableName, toOneClass.getClass()), JoinType.LEFT);
        return cb.equal(root.get(conditionFilenName).as(ReflectUtil.reflectObjObtainFileClassType(toOneClass.getClass(), conditionFilenName).getClass()),
                ConvertDataUtil.convertStr(ReflectUtil.reflectObjObtainFileMethod(toOneClass, conditionFilenName).invoke(toOneClass)));
    }

    /**
     * 一对多构造
     * @param root
     * @param cb
     * @param toOneClass
     * @param conditionFilenName
     * @return
     */
    private Predicate obtainOneToManySqlWhere(Root<T> root, CriteriaBuilder cb, BaseBean toOneClass, String conditionFilenName) throws Exception{
        if (ReflectUtil.reflectObjObtainFileClassType(toOneClass.getClass(),conditionFilenName).newInstance() instanceof String){//如果为string则使用like匹配
            return obtainLikeSqlWhere(root, cb, ConvertDataUtil.convertStr(ReflectUtil.reflectObjObtainFileMethod(toOneClass, conditionFilenName).invoke(toOneClass)), conditionFilenName);
        }
        return obtainOneToOneSqlWhere(root, cb, toOneClass, conditionFilenName);
    }

    /**
     * 动态生成where语句
     * @param obj
     * @return
     */
    public Specification<T> getWhereClause(Object obj){
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!ObjectCheckUtil.checkIsNullOrEmpty(obj)){
                    Class<?> clz = obj.getClass();
                    Field[] fields = clz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            Object filedVal = field.get(obj);//反射获取属性值
                            if (ObjectCheckUtil.checkIsNullOrEmpty(filedVal)){
                                continue;
                            }

                            if (field.isAnnotationPresent(NoSpecificationQuery.class)){//当前字段不需要匹配条件
                                continue;
                            }

                            if (field.isAnnotationPresent(Between.class)){
                                predicates.add(obtainBetweenSqlWhere(root, cb, ((BaseBean)obj).getBetween(), field.getName()));
                            }else if (field.isAnnotationPresent(Like.class)){
                                if (filedVal instanceof String){
                                    predicates.add(obtainLikeSqlWhere(root, cb, String.valueOf(filedVal), field.getName()));
                                }
                            }else if (field.isAnnotationPresent(RLike.class)){
                                if (filedVal instanceof String){
                                    predicates.add(obtainRigthLikeSqlWhere(root, cb, String.valueOf(filedVal), field.getName()));
                                }
                            }else if (field.isAnnotationPresent(LLike.class)){
                                if (filedVal instanceof String){
                                    predicates.add(obtainLeftLikeSqlWhere(root, cb, String.valueOf(filedVal), field.getName()));
                                }
                            }else if (field.isAnnotationPresent(OneToOne.class) && field.isAnnotationPresent(OneToOneCondition.class)){
                                try {
                                    BaseBean toOneClass = (BaseBean) field.getType().newInstance();//关系类
                                    String conditionFilenName = field.getAnnotation(OneToOneCondition.class).conditionFile();
                                    if (toOneClass.getClass().isAnnotationPresent(Entity.class)){
                                        predicates.add(obtainOneToOneSqlWhere(root, cb, toOneClass, conditionFilenName));
                                    }
                                } catch (Exception e) {
                                    logger.error("当前字段{}one to one 异常!!", field.getName());
                                }
                            }else if (field.isAnnotationPresent(OneToMany.class) && field.isAnnotationPresent(OneToManyCondition.class)){
                                try {
                                    if (field.getType().newInstance() instanceof List ){
                                        List listObj = (List)field.getType().newInstance();
                                        if (listObj.get(0) instanceof BaseBean){
                                            BaseBean toOneClass = (BaseBean) listObj.get(0);//关系类
                                            String conditionFilenName = field.getAnnotation(OneToManyCondition.class).conditionFile();
                                            if (toOneClass.getClass().isAnnotationPresent(Entity.class)){
                                                predicates.add(obtainOneToManySqlWhere(root, cb, toOneClass, conditionFilenName));
                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                    logger.error("当前字段{}one to one 异常!!", field.getName());
                                    throw new BusinessException("error!");
                                }
                            }else{
                                Predicate equal = cb.equal(root.get(field.getName()).as(ReflectUtil.reflectObjObtainFileClassType(obj, field.getName())),
                                        ConvertDataUtil.convertStr(ReflectUtil.reflectObjObtainFileMethod(obj, field.getName()).invoke(obj)));
                                predicates.add(equal);
                            }
                        } catch (Exception e) {
                            logger.error("出现反射异常!", e);
                            throw new BusinessException("error!");
                        }
                    }
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }

    public static void main(String[] args) {

    }
}
