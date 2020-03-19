# **基于SpringBoot开发的打卡小程序后端**

## 项目结构
```
├─src
│  ├─main   主要代码
│  │  ├─docker
│  │  ├─java
│  │  │  └─com.github.valerie.wx.miniapp
│  │  │     ├─config   配置
│  │  │     ├─controller
│  │  │     ├─exception   异常处理
│  │  │     ├─mapper
│  │  │     ├─model
│  │  │     ├─service
│  │  │     │  └─impl
│  │  │     └─utils   工具类
│  │  │        ├─response   响应数据固定格式
│  │  │        └─scanQrCode
│  │  └─resources
│  │      ├─META-INF
│  │      ├─static
│  │      ├─templates
│  │      ├─application.yml   配置
│  │      ├─application-dev.yml   开发环境配置
│  │      └─logback-spring.yml   日志配置
│  └─test   单元测试
│      └─java
│          └─com.github.valerie.wx.miniapp
│             └─service
│                └─impl
└─target   class文件夹
```

## 日志配置

采用SLF4j和Logback进行日志管理, 配置文件为`logback-spring.xml`

每天产生对应的info文件和error文件

## Lombok

本项目使用lombok来实现实体类的`setter`和`getter`方法

需要在实体类添加注解`@Data`

## swagger2配置

本项目采用swagger2来进行API文档管理

- `model`——添加注解`@ApiModel`， 对应属性中添加注解`@ApiModelProperty`
- `controller`——添加注解`@Api("")`， 对应方法中添加注解`@ApiOperation("")`。

  如果有对应参数的设置， 使用`@ApiImplicitParams`和`@ApiImplicitParam`来进行标记
  ```
  @ApiImplicitParam(name="id", value="ID", defaultValue="1", required=true)

  @ApiImplicitParams({
      @ApiImplicitParam(name="page", value="页码", defaultValue="1", required=true),
      @ApiImplicitParam(name="limit", value="条数", defaultValue="10", required=true)
  })
  ```

## MyBatis配置

本项目采用MyBatis框架和MySQL来实现持久化层数据的维护。

使用druid数据连接池

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    username: #MySQL数据库用户名
    password: #MySQL数据库密码
    url: jdbc:mysql://localhost:3306/clock?characterEncoding=UTF-8&serverTimezone=GMT%2B8
```

## Spring Security配置

采用Spring Security来实现权限控制。

- WebSecurityConfig 

  SpringSecurity基本配置
  
- CustomUrlDecisionManager 
  
  访问决策管理器
  
- CustomFilterInvocationSecurityMetadataSource 
  
  安全元数据源(根据用户传来的请求地址,分析出请求需要的角色)


#### postman登录获取cookie
```
POST localhost:2005/mini/login?phone=&password=
```
登录成功即可获取cookie，可直接访问其他需要权限的接口(接口需要在同一个collection中)。

PS:不能随便用浏览器访问`localhost:2005/mini/login`，这种时候采用的method是GET，不能实现登录
(主要是WebSecurityConfig设置了不重定向到Spring Security的登录页面`authenticationEntryPoint`)。


## 微信小程序第三方SDK配置

使用[第三方SDK开发工具包](https://gitee.com/binary/weixin-java-tools)访问微信API接口

```yaml
wx:
  miniapp:
    configs:
      - appid: #appid
        secret: #appsecret
        msgDataFormat: JSON
```

`WxMaProperties`和`WxMaConfiguration`是必须的， 
项目加载之后调用`final WxMaService wxService = WxMaConfiguration.getMaService(appid);`，
使用`wxService`相应的方法来实现与微信服务端的交互。



