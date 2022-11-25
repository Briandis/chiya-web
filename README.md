# chiya-web

针对java-web开发中，场景的使用场景的业务工具库

基础依赖，chiyaUtil。
导入jar后可以直接使用，但是使用对应库，必须引用对应的依赖。

## 功能说明

> 需要自行在项目中引用对应依赖，本库只提供功能封装
> 1. excel处理
> 2. 阿里云OSS文件上传
> 3. redis操作
> 4. http请求处理
> 5. http请求工具
> 6. ChiyaSecurity权限处理
> 7. token工具
> 8. 微信支付工具库

### excel依赖

```xml
<!-- Excel -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.1.0</version>
</dependency>
```

```xml

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.1.0</version>
</dependency>
```

### oss依赖

```xml
<!-- 阿里云OSS -->
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.8.0</version>
</dependency>
 ```

### http请求工具

```xml

<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpcore</artifactId>
    <version>4.4.10</version>
</dependency>
```

```xml

<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.13</version>
</dependency>
```

### 微信支付(需要引入HTTP请求工具依赖)

```xml

<dependency>
    <groupId>com.github.wechatpay-apiv3</groupId>
    <artifactId>wechatpay-apache-httpclient</artifactId>
    <version>0.2.3</version>
</dependency>

```

### token依赖

```xml

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>

```

### json处理

```xml
<!-- 请自行选择无漏洞版本 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.76</version>
</dependency>

```