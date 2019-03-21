# imooc_miaosha
该项目是本人学习慕课网[Java秒杀系统方案优化 高性能高并发实战](https://coding.imooc.com/class/chapter/168.html)
后上传的源代码以及课程笔记. 

# 目录结构

├─jmeter    存放jmeter测试文件  
├─sql       存放sql脚本  
├─src  
│  ├─main   存放项目源代码  
│  ├─test   存放单元测试  

# 章节笔记
## 第1章-课程介绍及项目框架搭建

### 知识点
- 使用spring boot 搭建项目基础框架  
- 使用Thymeleaf做页面展示,封装Result统一结果
- 集成 mybatis + druid 做数据操作
- 继承redis, 使用Jedis操作redis数据, 封装了统一的缓存key. 

## 第2章-实现用户登录以及分布式session功能

> 学习了自己通过 `cookie` 实现分布式session, 不使用spring boot默认提供的

### 知识点
- 数据库设计
- 明文密码两次md5处理
- `JSR303`参数校验 + 全局异常处理器
- 分布式session, 通过cookie完成分布式session功能. 将cookie存放到redis中.

## 第3章-秒杀功能开发及管理后台
> 学习到了基础的商品下单流程, 查库存,查是否重复下单,减库存,创建订单等过程

### 知识点
1. 数据库设计(业务第一原则, 无需太注意三大范式)
2. 完成商品列表页
3. 完成商品详情页
4. 完成订单详情页

## 第4章-秒杀压测-Jmeter压力测试
> 学习使用jmeter做压力测试, 学习jmeter变量定义, redis压力测试工具

### 知识点
1. jemter入门
2. 自定义变量模拟多用户(线程组 -> 添加 -> 配置元件 -> CVS数据文件设置)  
![](http://ww1.sinaimg.cn/large/005SWfHCgy1g1ai04c47uj30zk0k0jtl.jpg)
3. jmeter在命令行下的使用.
    - 下载tgz,解压缩并替换windows视窗下面配置的CVS信息路径
    - 执行脚本 
    ```bash
    ./bin/jmeter.sh -n -t xxx.jmx -l result.jtl
    ```
    - 完成后可以将result下载下来导入到 `聚合报告` 中查看
4. spring boot 打war包.
    1. 添加 plugins 插件
        ```xml
        <!--打war包插件-->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <configuration>
                <failOnMissingWebXml>false</failOnMissingWebXml>
            </configuration>
        </plugin>
        ```
    2. 添加tomcat依赖
        ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        ```
    3. 编写启动类
        ```java
        public class ServletApplication extends SpringBootServletInitializer {
            @Override
            protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
                return builder.sources(ServletApplication.class);
            }
        }
        ```

## 第5章-页面级高并发秒杀优化（Redis缓存+静态化分离）

> 将to_list页面加入到redis缓存中, 60秒有效期. 能够有效的降低商品列表页查询数据库的次数. 对业务也不会产生大的影响.  

### 知识点
1. 页面缓存,URL缓存, 页面静态化, 为秒杀订单表添加唯一索引防止同一用户多次下单
2. 页面静态化, 前后端分离
3. 静态资源优化
4. CDN优化

## 第6章-服务级高并发秒杀优化（RabbitMQ+接口优化）
> 系统加载的时候讲秒杀商品缓存在redis中. 后续秒杀时直接预减redis中的商品总数.  
系统秒杀时返回排队, 

### 知识点
1. redis预减缓存,减少对数据库访问 
2. 内存标记减少redis的访问
3. RabbitMQ队列缓存, 异步下单, 增强用户体验, 削峰.
4. RabbitMQ的安装与SpringBoot继承
5. 访问NGINX水平扩展(LVS)

### RabbitMQ的四种模式
linux安装rabbitmq  
rabbitmq的四种模式:
- direct模式,监听队列就能收到消息
- topic模式,类似于订阅, 通过Binding将交换机和队列绑定,通过routing key匹配, 能匹配到的收到消息
- fanout模式,广播, 通过Binding将交换机和队列绑定,所有队列都能收到消息.相当于匹配所有的routing key
- headers模式,通过绑定交换机和队列, 匹配 `headers` 的 `all` 或者 `any`.将消息发送出去  

可以简单的理解 `direct` 是直接发向队列  
`fanout` 是直接发向所有 **绑定了** 的队列  
`topic`,`headers`是通过绑定交换机和队列,然后匹配绑定时的规则后才发向符合规则的队列  
后面三种都是先发向交换机,然后通过绑定队列时的各种规则匹配到指定的队列.  


### 秒杀业务时尽最大可能减少数据库访问

秒杀减少数据库访问的思路:  
1. 系统初始化时, 把商品库存加入到redis中.
2. 收到请求, redis减库存, 库存不足时, 直接返回, 否则进入3
3. 请求入队, 返回排队中.(请求削峰)   

to_lilst页面缓存有60s, 所以如果秒杀完成退回首页,显示的可能还是秒杀前的总量.  

## 第7章-图形验证码及恶意防刷

### 学习总结
1. 学到了怎么样将秒杀地址进行隐藏. 每次点击 `秒杀` 先通过 `path` 接口拿到一个随机生成的 md5 值, 该值 **60s内有效**, 
将该值加入到 `do_miaosha` 接口用于秒杀下单.  
1. 通过 拦截器+注解 实现对特定接口(加了注解的接口)做请求次数判断. **一定时间内, 只允许该用户访问该接口多少次**

### 知识点
1. 生成验证码, 点击秒杀接口前需要先填写验证码结果.
2. 点击秒杀按钮, 首先通过 `path` 接口生成一个随机的md5字符, 用于访问真正的秒杀接口.
3. path接口判断该用户 `一定时间段` 内的访问次数. 防止恶意刷请求.
4. 增加注解, 为需要登录, 访问限制的接口添加拦截器.
  

# 回顾(总结)
1. 通过 `COOKIE` 实现分布式session
2. JMeter的基础使用
3. 页面缓存(将页面缓存到Redis中)
4. 秒杀请求通过Redis预减库存,下单操作放入RabbitMQ中,消费端拿到秒杀数据后 `从容的` 做秒杀
5. 排队中. 秒杀请求通过RabbitMQ消费端处理,前端轮训判断后台是否已经完成秒杀. 是否秒杀到了
6. 复习了RabbitMQ的四种模式.
7. 隐藏秒杀地址,每次点击 `秒杀` 先通过 `path` 接口拿到一个随机生成的 md5 值, 该值 **60s内有效**
8. 请求限制, **一定时间内, 只允许该用户访问该接口多少次**
9. <font color='red'>**重点:**</font> 学习了`HandlerMethodArgumentResolver`的使用. 自定义参数解析.这部分我记录在了 **[我的博客](http://blog.imyzt.top/article/61)**

# 不足
## 问题1 
Q: 请求处理(排队中), 不应该由前端 **轮询** 后端判断是否完成秒杀  
A: MqReceiver#miaoshaQueueReceiver()处理完成后, 通过 `WebSocket` 将秒杀结果返回给前端订阅.减少无意义的轮询

## 问题2 
代码缺少单元测试