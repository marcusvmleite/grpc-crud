package com.marcusvmleite.crud.service;

import com.google.protobuf.Timestamp;
import com.marcusvmleite.crud.grpc.PersonGrpc;
import com.marcusvmleite.crud.model.Person;
import com.marcusvmleite.crud.model.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mappings({
            @Mapping(source = "birthDate", target = "birthDate", qualifiedByName = "protobufTimestampToLocalDate"),
            @Mapping(source = "phoneNumberList", target = "numbers", qualifiedByName = "protobufNumberToPhoneNumber")
    })
    Person personGrpcToPerson(PersonGrpc personGrpc);

    @Named("protobufTimestampToLocalDate")
    static LocalDate mapBirthDate(Timestamp timestamp) {
        return Instant
                .ofEpochSecond(timestamp.getSeconds() , timestamp.getNanos())
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();
    }

    @Named("protobufNumberToPhoneNumber")
    static List<PhoneNumber> mapPhoneNumbers(List<PersonGrpc.PhoneNumber> numbers) {
        return numbers.stream().map(number -> {
            PhoneNumber pn = new PhoneNumber();
            pn.setNumber(number.getNumber());
            pn.setType(getType(number.getType()));
            return pn;
        }).collect(Collectors.toList());
    }

    static PhoneNumber.Type getType(PersonGrpc.PhoneNumber.PhoneType type) {
        PhoneNumber.Type result;
        switch (type) {
            case HOME: result = PhoneNumber.Type.HOME; break;
            case MOBILE: result = PhoneNumber.Type.MOBILE; break;
            case BUSINESS: result = PhoneNumber.Type.BUSINESS; break;
            default: result = PhoneNumber.Type.UNKNOWN;
        }
        return result;
    }

}
