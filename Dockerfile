FROM openjdk:11
VOLUME /tmp
EXPOSE 8107
ADD ./target/ms-management-fixed-term-account-0.0.1-SNAPSHOT.jar ms-management-fixed-term-account.jar
ENTRYPOINT ["java","-jar","ms-management-fixed-term-account.jar"]