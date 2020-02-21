package com.marcusvmleite.crud.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "PHONE_NUMBER")
public class PhoneNumber {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person")
    private Person person;

    @Column
    private String number;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Version
    private Integer version;

    public enum Type {
        UNKNOWN,
        HOME,
        MOBILE,
        BUSINESS
    }

}
