FROM openjdk:8
ADD build/libs/project1-0.1.0.jar project1-0.1.0.jar
CMD ["java","-jar","/home/project1-0.1.0.jar"]
