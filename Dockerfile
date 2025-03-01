FROM tomcat:10.1-jdk21
COPY target/TODOWeb-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
