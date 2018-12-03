package com.xlens.mongodb.mongodb.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Service
public class MongoService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Document findById(String value){
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(value));
		return mongoTemplate.findOne(query, Document.class, "people");
	}
	public List<Document> findByLastname(String value){
		Query query = new Query();
		query.addCriteria(Criteria.where("last_name").is(value));
		return mongoTemplate.find(query, Document.class,"people");
	}	
	
	public List<Document> findAll(){
		Query query = new Query();
		return mongoTemplate.find(query, Document.class,"people");
	}
	public void createPerson(Document payload) {
		mongoTemplate.insert(payload, "people");
	}
	public long removeById(String personId) {
		DeleteResult result = mongoTemplate.remove(personId, "people");
		return result.getDeletedCount();
	}
	public long upsert(String personId, Document payload) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(personId));
		Update update = Update.fromDocument(payload);
		UpdateResult result = mongoTemplate.upsert(query, update, "people");
		return result.getModifiedCount();
	}
}
