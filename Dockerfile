FROM anapsix/alpine-java
COPY build/libs/project1-0.1.0.jar home/project1-0.1.0.jar
CMD ["java","-jar","/home/project1-0.1.0.jar"]
