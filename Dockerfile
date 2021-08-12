#
# Build Maven artifact
#
FROM maven:3.8.1-amazoncorretto-11 as mavenbuilder

# Cache our dependencies in this layer
RUN mkdir -p /tmp/pom/
COPY pom.xml /tmp/pom
WORKDIR /tmp/pom
RUN mvn dependency:go-offline

# Copy the source to the container
COPY . /tmp/src

# Set the working directory for all our other commands
WORKDIR /tmp/src

# Run the build with the package goal
RUN mvn -B -e -U clean package

#
# Final image to ship containing jre and app.jar
#

FROM openjdk:11.0-jre

ENV WEBAPPS_HOME=/var/www
ENV WRITE_TO_FILE false

# Copy the built WAR file from our build container to this runtime container
COPY --from=mavenbuilder /tmp/src/target/urlshortenerapp.jar $WEBAPPS_HOME/webapps/urlshortenerapp.jar

WORKDIR $WEBAPPS_HOME

EXPOSE 8080

ENTRYPOINT java -jar ./webapps/urlshortenerapp.jar
