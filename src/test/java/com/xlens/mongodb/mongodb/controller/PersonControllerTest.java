package com.xlens.mongodb.mongodb.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xlens.mongodb.mongodb.MongodbApplication;
import com.xlens.mongodb.mongodb.service.MongoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongodbApplication.class)
@SpringBootTest
public class PersonControllerTest {

	private MockMvc mockMvc;
	
	@MockBean
	private MongoService service;
	
	@Autowired
	private WebApplicationContext appContext;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
	}
	
	@Test
	public void testFindById() throws Exception {
		String personId = "123232";
		Document testDoc = new Document();
		testDoc.append("_id", personId).append("name", "Neo").append("movie", "matrix");
		
		when(service.findById(personId)).thenReturn(testDoc);
		when(service.findById("999999")).thenThrow(RuntimeException.class);
		
		mockMvc.perform(get("/api/persons/"+personId))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Neo")));
		
		mockMvc.perform(get("/api/persons/999999"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
}
