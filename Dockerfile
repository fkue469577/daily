FROM livingobjects/jre8
ENV MYPATH /root/springboot
WORKDIR $MYPATH
ADD ./target/daily-0.0.1.jar app.jar
VOLUME $MYPATH
RUN echo "Asia/Shanghai" > /etc/timezone
CMD java -jar app.jar
