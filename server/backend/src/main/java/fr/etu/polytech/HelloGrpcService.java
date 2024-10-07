package fr.etu.polytech;

import io.quarkus.grpc.GrpcService;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;

@GrpcService
@Authenticated
public class HelloGrpcService implements HelloGrpc {

    @Override
    @RolesAllowed("user")
    public Uni<HelloReply> sayHello(HelloRequest request) {
        return Uni.createFrom().item("Hello " + request.getName() + "!")
                .map(msg -> HelloReply.newBuilder().setMessage(msg).build());
    }

}
