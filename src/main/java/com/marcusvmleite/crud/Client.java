package com.marcusvmleite.crud;

import com.google.protobuf.Timestamp;
import com.marcusvmleite.crud.grpc.CreateUpdatePersonRequest;
import com.marcusvmleite.crud.grpc.CreateUpdatePersonResponse;
import com.marcusvmleite.crud.grpc.PersonGrpc;
import com.marcusvmleite.crud.grpc.PersonServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.UUID;

@Slf4j
public class Client {

    public static void main(String[] args) {

        log.info("Starting gRPC client...");

        ManagedChannel managedChannel = getManagedChannel();

        PersonServiceGrpc.PersonServiceBlockingStub client = getSyncClient(managedChannel);
        CreateUpdatePersonResponse response = client.createUpdatePerson(createPersonRequest());
        System.out.println(response);

        managedChannel.shutdown();
    }

    private static CreateUpdatePersonRequest createPersonRequest() {
        return CreateUpdatePersonRequest.newBuilder()
                .setPerson(createMockPerson())
                .build();
    }

    private static PersonGrpc createMockPerson() {
        Instant instant = Instant.now();
        return PersonGrpc.newBuilder()
                .setBirthDate(Timestamp.newBuilder()
                        .setSeconds(instant.getEpochSecond())
                        .setNanos(instant.getNano())
                        .build())
                .addPhoneNumber(PersonGrpc.PhoneNumber.newBuilder()
                        .setTypeValue(PersonGrpc.PhoneNumber.PhoneType.MOBILE_VALUE)
                        .setNumber("12345678")
                        .build())
                .setDocuments(PersonGrpc.PersonDocuments.newBuilder()
                        .setDriverLicence(UUID.randomUUID().toString())
                        .setNationalId(UUID.randomUUID().toString())
                        .setPassport(UUID.randomUUID().toString())
                        .build())
                .setWeight(120)
                .setHeight(185)
                .setName("Aristides")
                .build();
    }

    private static ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext() //disabling TLS for dev. purposes only (DO NOT USE IN PROD!)
                .build();
    }

    private static PersonServiceGrpc.PersonServiceBlockingStub getSyncClient(ManagedChannel managedChannel) {
        return PersonServiceGrpc.newBlockingStub(managedChannel);
    }

}