FROM tomcat:8.0.20-jre8

COPY /var/lib/jenkins/workspace/java-app-pipeline/build/libs/* usr/local/tomcat/webapps/project1-0.1.0.jar
