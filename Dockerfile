FROM tomcat:8.0.20-jre8

COPY build/libs/project1-0.1.0.jar usr/local/tomcat/webapps/project1-0.1.0.jar
