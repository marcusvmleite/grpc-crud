package com.marcusvmleite.crud.repository;

import com.marcusvmleite.crud.model.Person;
import com.marcusvmleite.crud.model.PersonDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT NEW com.marcusvmleite.crud.model.PersonDto(p.name) FROM Person p WHERE p.name like :name")
    List<PersonDto> findNameById(@Param("name") String name);

    @EntityGraph(value = "Person.eager")
    Optional<Person> findById(@Param("id") Long id);

}
