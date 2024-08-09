package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepository;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    
    public Page<Person> getAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person updatePerson(Long id, Person personDetails) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        
        person.setEmail(personDetails.getEmail());
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setYearOfBirth(personDetails.getYearOfBirth());
        person.setPhoneNumber(personDetails.getPhoneNumber());
        
        return personRepository.save(person);
    }

    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }
}