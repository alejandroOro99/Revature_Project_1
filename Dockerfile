FROM tomcat:8.0.20-jre8

COPY target/project1.jar usr/local/tomcat/webapps/project1.jar
