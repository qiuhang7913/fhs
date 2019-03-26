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
（1）使用分布式发布订阅消息系统kafaka+zookper做后管日志
（2）使用消息队列服务软件rabbitmq方案做相关异步队列业务

5：spring data reset支持

6：srping 缓存

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

/&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
2019/3/19
1.框架已经完成增删改查的基础封装【主要使用spirng data jpa 并且扩展使用了hibernate的相关功能】
2.spring security 权限安全框架的整合
3.角色-资源-用户 的动态权限关系已经完成
4.前端页面技术基本封装完成【主要使用到了bootstrap框架和jQuery类库，包括bootstrap table|bootstrap treeview|jquery confirm|izimodel 模态插件】
5.公版三方工具整合【fastjson整合|Kaptcha验证码整合|hibernate 参数校验器的整合......】
6.数据连接池使用阿里druid 做sql相关监控管理
7.nosql的整合，已完成redis的整合,暂时不整合mongodb

接下来主要任务:
1.异步消息整合(kafka 和 rabbitmq)
2.搜索引擎es的整合
3.暂时不用springcloud

/&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
2019/3/26
1.框架已经完成增删改查的基础封装【主要使用spirng data jpa 并且扩展使用了hibernate的相关功能】
2.spring security 权限安全框架的整合
3.角色-资源-用户 的动态权限关系已经完成
4.前端页面技术基本封装完成【主要使用到了bootstrap框架和jQuery类库，包括bootstrap table|bootstrap treeview|jquery confirm|izimodel 模态插件】
5.公版三方工具整合【fastjson整合|Kaptcha验证码整合|hibernate 参数校验器的整合......】
6.数据连接池使用阿里druid 做sql相关监控管理
7.nosql的整合，已完成redis的整合,暂时不整合mongodb
8.异步消息整合(kafka 和 rabbitmq)
9.代码生成器[目前仅实现javabean]

接下来主要任务:
1.代码生成器[暂时不实现action servie dao]
2.搜索引擎es的整合
3.暂时不用springcloud
