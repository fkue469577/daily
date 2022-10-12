FROM livingobjects/jre8
ENV MYPATH /root/springboot
WORKDIR $MYPATH
ADD ./target/daily-0.0.1-SNAPSHOT.jar app.jar
VOLUME $MYPATH
run echo "Asia/Shanghai" > /etc/timezone
CMD java -jar app.jar
