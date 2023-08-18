# Docker image for springboot file run
# VERSION 0.0.1
# Author: taoyu
# 基础镜像使用java
FROM openjdk:8-jre
# 作者
MAINTAINER taoyu <1130689463@qq.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
ADD account-server-0.0.1.jar account-server.jar
EXPOSE 8082
# 运行jar包
RUN bash -c 'touch /account-server.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/account-server.jar"]