框架整体使用springboot

1：数据访问
2018/12/4 ----- 2018/12/14
spring jpa 数据库操作 %100
2019/1/18 ------新增基于任意sql的传入查询

2：安全框架
2018/12/14 ----- 2018/12/17(100%)
spring security 权限安全模块
2019/1/18 ----- 新增自定义csrf配置

3：nosql(使用redis，mongodb)

4：异步消息方案（jms、amqp）
（1）kafaka+zookper做后管日志
（2）使用amqp方案做相关异步业务

5：spring data reset支持

6：srping 缓存

7：不同系统之间通信
方案1：spring integration(暂时倾向于使用这个)
方案2：jsonp借口

8：监控

9：springcloud

/&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
2019/2/16
1.框架已经完成增删改查的基础封装【主要使用spirng data jpa 并且扩展使用了hibernate的相关功能】
2.spring security 权限安全框架的整合
3.角色-资源-用户 的动态权限关系已经完成
4.前端页面技术基本封装完成【主要使用到了bootstrap框架和jQuery类库，包括bootstrap table|bootstrap treeview|jquery confirm|izimodel 模态插件】
5.公版三方工具整合【fastjson整合|Kaptcha验证码整合|hibernate 参数校验器的整合......】

接下来主要任务:
1.nosql的整合【主要redis为主】
2.异步消息的整合【主要使用amqp方案】
3.搜索引擎es的整合
4.微服务扩展【主要使用springcloud】


