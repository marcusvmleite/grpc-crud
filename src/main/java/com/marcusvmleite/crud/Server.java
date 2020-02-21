package com.marcusvmleite.crud;

import com.marcusvmleite.crud.service.PersonService;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class Server implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    private void start() throws IOException, InterruptedException {

        io.grpc.Server server = ServerBuilder.forPort(50051)
                .addService(personService)
                .addService(ProtoReflectionService.newInstance()) //Service Reflection
                .build();

        server.start();

        log.info("gRPC up and running!");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Received shutdown request.");
            server.shutdown();
            log.info("Successfully stopped server.");
        }));

        server.awaitTermination();
    }

}
