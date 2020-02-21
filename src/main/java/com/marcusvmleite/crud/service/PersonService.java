package com.marcusvmleite.crud.service;

import com.marcusvmleite.crud.grpc.*;
import com.marcusvmleite.crud.model.Person;
import com.marcusvmleite.crud.repository.PersonRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PersonService extends PersonServiceGrpc.PersonServiceImplBase {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void createUpdatePerson(CreateUpdatePersonRequest request,
                                   StreamObserver<CreateUpdatePersonResponse> responseObserver) {

        PersonGrpc personGrpc = request.getPerson();

        Person person = PersonMapper.INSTANCE.personGrpcToPerson(personGrpc);
        person.getDocuments().setPerson(person);
        person.getNumbers().forEach(number -> number.setPerson(person));

        personRepository.save(person);

        CreateUpdatePersonResponse response = CreateUpdatePersonResponse.newBuilder()
                .setId(person.getId().intValue())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPerson(GetPersonRequest request,
                          StreamObserver<GetPersonResponse> responseObserver) {

    }

    @Override
    public void getPeople(GetPeopleRequest request,
                          StreamObserver<GetPeopleResponse> responseObserver) {

    }

    @Override
    public void deletePerson(DeletePersonRequest request,
                             StreamObserver<DeletePersonResponse> responseObserver) {

    }

}
