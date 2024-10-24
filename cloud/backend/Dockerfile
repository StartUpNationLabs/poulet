## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-23 AS build
ARG SERVICE

COPY --chown=quarkus:quarkus . /code
## install maven with curl
# https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
USER    root
RUN curl -fsSL -o /tmp/apache-maven.tar.gz https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
RUN tar -xzf /tmp/apache-maven.tar.gz -C /opt/
RUN ln -s /opt/apache-maven-3.9.9 /opt/maven
ENV PATH="/opt/maven/bin:${PATH}"
USER quarkus
WORKDIR /code
RUN mvn -B -f /code/$SERVICE/pom.xml -DskipTests clean package -Dnative

## Stage 2 : create the docker final image
FROM quay.io/quarkus/quarkus-micro-image:2.0
ARG SERVICE

WORKDIR /work/
COPY --from=build /code/$SERVICE/target/*-runner /work/application

# set up permissions for user `1001`
RUN chmod 775 /work /work/application \
  && chown -R 1001 /work \
  && chmod -R "g+rwX" /work \
  && chown -R 1001:root /work

ENV PORT=8080
EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]