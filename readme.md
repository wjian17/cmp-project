##项目工程说明
|模块|功能|说明|技术点|
| --- |--- | --- | --- |
|core|核心jar|||
|security|安全类jar|||
|parent|父pom|||
|remote-parent|父remote pom|供其他模块内部fegin调用||
|register|注册中心|服务注册中心|eureka\nacos|
|config|配置中心|服务配置中心|git/svn/local等|
|rest|统一对外接口服务|整合多个类似的fegin,通过参数统一对外|swagger|
|auth|授权认证服务|统一授权认证|oauth2/security|
|auth-client|资源服务|资源服务器|resources/security|
|quartz|任务管理服务|任务管理器|quartz/scheduler|
|workflow|工作流服务|flowable工作流|flowable/drools|


###swagger
```$xslt
api访问可视化页面
http://localhost:8080/rest/swagger-ui.html
http://localhost:8080/rest/doc.html
```
|注解|用途|说明|
| --- |--- | --- |
|@Api()|用于类|表示标识这个类是swagger的资源|
|@ApiOperation()|用于方法|表示一个http请求的操作|
|@ApiParam()|用于方法，参数，字段说明|表示对参数的添加元数据（说明或是否必填等）|
|@ApiModel()|用于类,用于参数|表示对类进行说明，用实体类接收|
|@ApiModelProperty()|用于方法，字段|表示对model属性的说明或者数据操作更改|
|@ApiIgnore()|用于类，方法，方法参数|表示这个方法或者类被忽略|
|@ApiImplicitParam()|用于方法|表示单独的请求参数|
|@ApiImplicitParams()|用于方法，包含多个@ApiImplicitParam||