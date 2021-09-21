FROM 192.168.25.203:8080/library/serverjre:8
COPY /app/target/*.jar /usr/local/lib/app.jar
                        
ENV spring.cloud.config.uri='http://config-server-cfs.cfs.svc.cluster.local:8080'
ENV spring.cloud.config.profile='test'
ENV spring.cloud.config.username=user
ENV spring.cloud.config.password=pass

ENTRYPOINT ["bin/sh","-c","java -Xmx256m -Duser.timezone=Asia/Tehran -Djava.security.egd=file:/dev/./urandom -jar /usr/local/lib/app.jar"]
