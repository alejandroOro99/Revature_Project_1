FROM alpine

COPY index.html usr/local/tomcat/webapps/index.html
COPY build/libs/project1-0.1.0.jar usr/local/tomcat/webapps/project1-0.1.0.jar


