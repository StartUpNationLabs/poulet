# backend

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Before running

Create a .env file, at the root of the backend, similar to .env.example or delete the properties related to keycloak. (but don't push the deletion)

## Warning: If you develop from intelliJ

IntelliJ don't recognize files generated with proto (gRPC) so you need to do the following step to fix the issues :

- go to file -> project structure -> modules
- for each module of the backend select the folder grpc in : :modulePath:/target/generated-sources/grpc
- Mark this folder as Sources directory instead of excluded
- Then, when the directory is compiled and proto file are generated, intelliJ will take them into account

## Running the application in dev mode


You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Test the grpc request from the dev mode ui

When you launch the dev mode go to :
http://localhost:8080/q/dev-ui/io.quarkus.quarkus-grpc/services

And you'll see the list of gRPC endpoint you have with a try-it-tool included.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/backend-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and
  Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on
  it.
- MongoDB with Panache ([guide](https://quarkus.io/guides/mongodb-panache)): Simplify your persistence code for MongoDB
  via the active record or the repository pattern

## Provided Code

### gRPC

Create your first gRPC service

[Related guide section...](https://quarkus.io/guides/grpc-getting-started)

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
