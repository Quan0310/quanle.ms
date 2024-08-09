package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Person;
import com.example.demo.service.PersonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
    private PersonService personService;
	
	@GetMapping("/test")
	public String allAccess() {
		return "Public Content Admin.";
	}
    @PostMapping("/create")
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }
    @PutMapping("update/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return personService.updatePerson(id, personDetails);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personService.deletePersonById(id);
        return "User deleted successfully!";
    }
}
