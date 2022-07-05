FROM livingobjects/jre8
ENV MYPATH /root/springboot
WORKDIR $MYPATH
ADD ./target/finance-0.0.1-SNAPSHOT.jar app.jar
VOLUME $MYPATH
EXPOSE 8668
CMD java -jar app.jar --spring.profiles.active=dev
