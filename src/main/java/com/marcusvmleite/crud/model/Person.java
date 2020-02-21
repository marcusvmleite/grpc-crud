package com.marcusvmleite.crud.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PERSON")
@NamedEntityGraph(
        name = "Person.eager",
        attributeNodes = {
                @NamedAttributeNode("documents"),
                @NamedAttributeNode("numbers")
        }
)
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @Column
    private Integer height;

    @Column
    private BigDecimal weight;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Version
    private Integer version;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private PersonDocuments documents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person", orphanRemoval = true)
    private List<PhoneNumber> numbers = new ArrayList<>();

}
