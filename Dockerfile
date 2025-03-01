FROM tomcat:10.1-jdk21
COPY target/TODOWeb-1.0-SNAPSHOT.war C:/apache-tomcat-10.1.36/webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]
