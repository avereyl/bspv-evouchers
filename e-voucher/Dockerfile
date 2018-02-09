# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
# Add some fonts (used by JasperReports)
RUN apk add --no-cache ttf-dejavu

# copy application WAR (with libraries inside + keystore and config file)
COPY target/e-voucher-*.jar /eVoucher.jar
COPY src/main/resources/security/keystore.p12 /keystore.p12
COPY src/main/resources/application-docker.properties /application.properties

# specify default command + add config file
CMD ["/usr/bin/java", "-jar", "-Dspring.config.location=/", "/eVoucher.jar"]

EXPOSE 8443
