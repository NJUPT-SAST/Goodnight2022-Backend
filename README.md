# Goodnight2022-Backend

SAST 新年活动《晚安2022》的后端代码

## 配置指南

1. 安装`MySQL`、`Redis`和`Docker`
2. 将sql文件夹内的sql文件导入至数据库中
3. 打包本项目，并上传生成的jar文件
4. 在jar同级目录创建`config/application-prod.yml`文件，随后写入以下配置

```yaml
spring:
  datasource:
    username: admin  # 数据库账号
    password: admin  # 数据库密码
    url: jdbc:mysql://mariadb:3306/goodnight2022  # 数据库地址
    driver-class-name: com.mysql.cj.jdbc.Driver
  mail:
    host: smtp.ym.163.com  # SMTP邮箱地址
    username: mail@xxx.com  # SMTP邮箱账号
    password: xxx  # SMTP邮箱密码
    default-encoding: utf-8
    properties:
      mail.smtp.auth: true
      mail.smtp.timeout: 10000
      mail.smtp.writetimeout: 10000
      mail.smtp.connectiontimeout: 10000
      mail.smtp.starttls.enable: true
      mail.smtp.starttls.required: true
      mail.smtp.socketFactory.port: 994
      mail.smtp.socketFactory.class: javax.net.ssl.SSLSocketFactory
      mail.smtp.socketFactory.fallback: false
  data:
    redis:
      host: redis  # Redis地址
      port: 6379  # Redis端口

server:
  port: 1090  # 本地服务监听端口
  address: 0.0.0.0  # 本地服务监听地址，Docker内运行请一定使用0.0.0.0
  website: http://localhost  # 前端的访问地址，用于点击邮件链接时打开前端网页
```

5. 运行docker命令

```shell
docker run -d \
--link mariadb \
--link redis \
-p 127.0.0.1:1090:1090 \
-v <jar文件所在目录>:/goodnight \
-w /goodnight \
-e TZ=Asia/Shanghai \
--name GoodNight2022 \
eclipse-temurin:17-jre-alpine java -jar GoodNight2022.jar --spring.profiles.active=prod
```