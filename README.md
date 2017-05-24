# HttpURLConnection

- HttpURLConnection是一种多用途、轻量极的HTTP客户端，使用它来进行HTTP操作可以适用于大多数的应用程序。
- 从Android4.4开始HttpURLConnection的底层实现采用的是okHttp。

# OkHttp

- 一个处理网络请求的开源项目,是安卓端最火热的轻量级框架,由移动支付Square公司贡献(该公司还贡献了Picasso)
- OkHttp是**网络执行层**
- 做底层网络请求


### 使用条件
- OkHttp支持Android 2.3及其以上版本。
- 对于Java, JDK1.7以上。

# Retrofit
- Retrofit是**网络调度层**
- 做具体业务请求、线程切换、数据转换

### 特点
- **CallAdapter-请求适配器**：

    可以实现多种请求响应形式：同步方式、异步回调方式、RxJava方式
- **Converter-数据转换器**：

    可以自己定义responseBodyConverter和requestBodyConverter,实现加密功能和各种奇葩格式的解析