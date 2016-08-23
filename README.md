本接口程序被设计用来支撑移动客户端部分功能，所有API均为 http web
service，数据交换格式为JSON。

接口若支持POST方式访问，则一般也支持GET方式访问（特殊情况除外，如：文件上传），最佳访问方式请参照各API定义中的建议。

对于接口访问的请求示例，若支持POST，则给出POST
报文样例（仅供参考，若要调试，请使用调试工具自行按要求设置入参），否则给出GET样例，若同时支持，则只给出POST样例，GET方式的请求，请参见HTTP规范自行组织。

对于入参组织结构，若入参为非结构化数据（比如：登录账号、密码，无需作为对象结构传入），则均采用
application/x-www-form-urlencoded 内容类型，否则，采用 application/json。

 

接口规范
--------

 

### 连接参数

**host**:

**port**: 8000

请在构造API地址时，将 **host** 和 **port** 部分用上述参数替换

 

### 安全性

 

**数据完整性：**

所有 API 均通过传输秘钥来对请求信息签名，以此保证信息不被篡改:

**transfer\_key**:

 

**入参签名规则：**

签名算法为 SHA1。具体签名规则如下：

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
sign = sha1( (k1=v1&k2=v2...) + transfer_key )
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

其中，k1=v1&k2=v2... 为参数键值列表，列表中的每一项格式为
“键=值”，列表项需要根据键名升序排列，然后用 “&” 进行连接。最后将数据传输秘钥
transfer\_key 拼接至尾端（不含 + 号）。整体作 sha1 运算得到最终的签名。

 

由于所有 API 入参均需签名，因此接口详细定义节不在赘述该参数含义。

 

**登录与授权：**

采用和 OAuth 类似的授权机制，用户的账号密码仅在登录接口调用时被用到，其他 API
调用只需传入由登录接口返回的鉴权码（access\_token），目前鉴权码有效期为7天。

 

### 通用响应格式

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
{
  "code" : 1,
  "message" : null,
  "data" : {
    ...
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

#### 响应字段说明

| **字段** | **含义**            | **必传** | **备注**                                                          |
|----------|---------------------|----------|-------------------------------------------------------------------|
| code     | 本次 API 调用的结果 | Y        | 指示API调用过程是否完整（无网络错误或异常），详见接口常量定义一节 |
| message  | 异常或错误调试信息  | N        |                                                                   |
| data     | 响应数据的根节点    | N        | 根据具体业务逻辑，非数据接口可能不包含该节点                      |

 

### 常量定义

 

#### API 响应代码

| **代码** | **含义**             |
|----------|----------------------|
| \-1      | 请求参数签名错误     |
| 1        | 成功                 |
| 2        | 请求参数错误         |
| 3        | 系统内部错误         |
| 4        | 未知错误（联系技术） |
| 5        | 未登陆               |

 

#### API 业务错误代码

| 代码 | 含义 |
|------|------|
|      |      |

 

附录
----

 

### 程序发行包结构

根目录

— app.war //程序可执行文件

— run.sh //linux 下的程序控制脚本（ 执行 run.sh start 来启动；执行 run.sh stop
来停止）

— run.bat //windows 下的启动脚本（前台运行，CTLR+C 停止）

— setenv.bat //windows 下的环境参数脚本

— setenv.sh //linux 下的环境参数脚本

 

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
setenv.bat 参数说明:

JRE_HOME: java的安装目录
TCP_LISTEN: TCP 进程监听的 IP (0.0.0.0 表示请求的目标 IP 为任何值时，都处理请求)  
TCP_PORT: TCP 进程坚挺的本地端口号  
EXTRA_ARGS: JVM 参数（详见 oracle 文档）
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
setenv.sh 参数说明：

TCP_LISTEN: TCP 进程监听的 IP (0.0.0.0 表示请求的目标 IP 为任何值时，都处理请求)
TCP_PORT: TCP 进程坚挺的本地端口号
JAVA_EXEC: java可执行程序的绝对路径，如: /opt/jdk1.7/bin/java；若配置了环境变量，则值为 java
EXTRA_ARGS: JVM 参数（详见 oracle 文档）
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 

### 部署环境要求

jdk/jre \>= 1.7.0 （需要设置 jdk/jre 环境变量）

启动后，将会在 run.sh 或 run.bat 同级目录创建 data
目录，此目录包含程序必要配置和数据以及缓存，请不要擅自修改其中的内容。
