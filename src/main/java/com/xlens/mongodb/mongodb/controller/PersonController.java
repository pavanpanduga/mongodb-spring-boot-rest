package com.xlens.mongodb.mongodb.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xlens.mongodb.mongodb.service.MongoService;

@RestController
@RequestMapping("api")
public class PersonController {
	
	@Autowired
	private MongoService service;

	@GetMapping(value = "/persons/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findPerson(@PathVariable(name="id") String personId){
		Document  person = service.findById(personId);
		if( person == null ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(person);
	}

    @PostMapping(value = "/persons", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
   	public ResponseEntity<?> createPerson(@RequestBody Document payload) throws Exception{
    	service.createPerson(payload);
		return new ResponseEntity<>(HttpStatus.CREATED);
   	} 

	@DeleteMapping(value = "/persons/{id}")
	public ResponseEntity<?> removePerson(@PathVariable(name="id") String personId){
		long  removalCount = service.removeById(personId);
		if( removalCount == 0 ) {
			throw new RuntimeException(personId+" not found for deletion!");
		}
		return ResponseEntity.ok().body(removalCount);
	}
	@PutMapping(value = "/persons/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updatePerson(@PathVariable(name="id") String personId, @RequestBody Document payload){
		long  updateCount = service.upsert(personId, payload);
		return ResponseEntity.ok().body(updateCount);
	}
}
