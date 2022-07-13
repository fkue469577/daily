FROM livingobjects/jre8
ENV MYPATH /root/springboot
WORKDIR $MYPATH
ADD ./target/daily-0.0.1-SNAPSHOT.jar app.jar
VOLUME $MYPATH
EXPOSE 8778
CMD java -jar app.jar --spring.profiles.active=dev
