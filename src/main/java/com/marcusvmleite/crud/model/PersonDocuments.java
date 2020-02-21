package com.marcusvmleite.crud.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "PERSON_DOCUMENTS")
public class PersonDocuments {

    @Id
    private Long id;

    @Column
    private String passport;

    @Column(name = "DRIVER_LICENCE")
    private String driverLicence;

    @Column(name = "NATIONAL_ID")
    private String nationalId;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Person person;

    @Version
    private Integer version;

}
