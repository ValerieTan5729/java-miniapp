# **基于SpringBoot开发的打卡小程序后端**

- [目录]()
    - [项目结构](#项目结构)
    - [日志配置](#日志配置)
    - [Lombok](#lombok)
    - [swagger2配置](#swagger2配置)
    - [MyBatis配置](#mybatis配置)
    - [Spring Security配置](#spring-security配置)
    - [微信小程序第三方SDK配置](#微信小程序第三方SDK配置)
    - [Docker项目部署](#docker项目部署)


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

访问地址为`localhost:2005/mini/swagger-ui.html`(请自行修改)

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

### 1.采用Spring Security来实现权限控制。

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

### 2.手机号码直接登录系统

考虑到用户在微信小程序使用该系统的时候，希望能够直接通过手机号登录该系统，因此在登录方面增加了
类似`手机号码+验证码`的形式进行登录。[参考博客传送门](https://blog.csdn.net/yuanlaijike/article/details/86164160)

全程主要模仿`UsernamePasswordAuthentication`机制实现，
主要的代码实现在`com.github.valerie.wx.miniapp.config.wxLogin`中。

- WxAuthenticationToken

  参考`UsernamePasswordAuthenticationToken`实现，主要通过手机号码获取一个安全的凭证，以便在后续的访问中
  可以访问其他需要登录认证的API。

- WxAuthenticationFilter

  参考`UsernamePasswordAuthenticationFilter`实现，主要是检验发送过来的url的方式是否为post和获取手机号码参数，
  而在系统实现中，不涉及这一部分。
  
  (如果需要测试这个，可以使用postman访问`localhost:2005/mini/wx/user/wxeb195511809cd1ef/login`，并且修改
  该API为`PostMapping`。)

- WxAuthenticationProvider

  这个类较为重要，为SpringSecurity的`AuthenticationManager`增加新的验证机制。

- WxAuthenticationSecurityConfig

  微信手机号码登录的配置。
  
  使用该配置的方法：在`WebSecurityConfig`的`configure(HttpSecurity http)`添加`http.apply(wxAuthenticationSecurityConfig);`。
  
PS:在测试过程中发现，如果在`Spring Security`的配置过滤的url，访问这些url的时候，无法获取当前登录用户的信息。


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

## Docker项目部署

#### 1. 服务器docker设置

1. Ubuntu安装Docker

    [参考文档](https://www.jianshu.com/p/80e3fd18a17e)
    
2. docker开启开启远程访问

    - 修改docker宿主机文件的ExecStart配置
    
        `vim /lib/systemd/system/docker.service`
        
        `ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:[port] -H unix:///var/run/docker.sock`
        
        [port]为外界访问服务器docker服务的端口号(自行定义)
    
    - 通知docker服务做出修改
    
        `systemctl deamon-reload`
    
    - 重启docker服务
    
        `sudo service docker restart`
    
    - 测试docker api
    
        `curl http://127.0.0.1:[port]/version` or `curl http://[ip]:[port]/version`

#### 2. docker部署文档(/src/main/docker/Dockerfile)

```dockerfile
# 指定java8为基础镜像(必须是第一条指令)
FROM java:8
# 挂载目录
VOLUME /tmp
# 把文件复制到镜像中
ADD java-miniapp-1.0.0-SNAPSHOT.jar app.jar
# 启动时的默认命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

#### 3.idea Docker总体配置

在idea的File->Settings->Build,Execution,Deployment->Docker中添加服务器的docker, 
api url为`tcp://[ip]:[port]`

#### 4. pom配置

主要是将打包生成jar包复制到docker的目录下。

```xml
<plugins>
    <plugin>
        <groupId>com.spotify</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>1.0.0</version>
        <configuration>
            <dockerDirectory>src/main/docker</dockerDirectory>
            <resources>
                <resource>
                    <targetPath>/</targetPath>
                    <directory>${project.build.directory}</directory>
                    <include>${project.build.finalName}.jar</include>
                </resource>
            </resources>
        </configuration>
    </plugin>
    <plugin>
        <artifactId>maven-antrun-plugin</artifactId>
        <!--绑定mvn package命令，当执行package这个maven命令打包项目的同时
            会把target目录下的jar包给copy到docker目录去-->
        <executions>
            <execution>
                <phase>package</phase>
                <configuration>
                    <target>
                        <copy todir="src/main/docker" file="target/${project.build.finalName}.jar"/>
                    </target>
                </configuration>
                <goals>
                    <goal>run</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

#### 5. DockerFile配置

Run/Debug Configuration->Add Docker/Dockerfile添加docker具体配置

- Server->添加步骤3中的server
- Dockerfile->添加步骤2中dockerfile的具体位置
- Image tag->生成镜像的名称(spring:clock)
- Run built image->勾选(这样可以一键启动docker镜像)
- Container name->容器名称(clock)
- Bind ports->[Host port]:[container port] (Host port为外界访问的端口, container port为项目端口)
- 此外可以添加`Run Maven Goal : clean package`(这样在启动的时候可以获取最新的jar包)
