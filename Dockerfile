FROM tomcat:10.1-jdk21
COPY target/TODOWeb.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
