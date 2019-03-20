# imooc_miaosha
imooc秒杀项目学习笔记

# 目录结构
- sql/   
    存放sql脚本
- jmeter/  
    存放jmeter测试文件


# 已完成章节
## 第1章-课程介绍及项目框架搭建
## 第2章-实现用户登录以及分布式session功能
学习到了不使用已集成的session插件, 通过cookie完成分布式session功能. 将cookie存放到redis中.
## 第3章-秒杀功能开发及管理后台
学习到了基础的商品下单流程, 查库存,查是否重复下单,减库存,创建订单等过程
## 第4章-秒杀压测-Jmeter压力测试
学习使用jmeter做压力测试, 学习jmeter变量定义, redis压力测试工具
## 第5章-页面级高并发秒杀优化（Redis缓存+静态化分离）
页面缓存,URL缓存, 页面静态化, 为秒杀订单表添加唯一索引防止同一用户多次下单
## 第6章-服务级高并发秒杀优化（RabbitMQ+接口优化）
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