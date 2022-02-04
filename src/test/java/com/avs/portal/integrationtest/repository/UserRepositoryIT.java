package com.avs.portal.integrationtest.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.avs.portal.entity.User;
import com.avs.portal.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryIT {

	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void createUserIT() throws IOException {
		User anil = new User()
				.setPhone(9988776644L)
				.setEmail("anil@google.com");
		
		anil = testEntityManager.persist(anil);
		testEntityManager.flush();
		
		System.err.println("## Persisted: " + anil.getId());
		User anilEntity = userRepository.findById(anil.getId()).orElse(null);
		if(anilEntity == null)
			System.err.println("Anil not Persisted.");

		System.err.println("## Retrived: " + anilEntity.getId());
		
		System.in.read();
		
		assertTrue(anilEntity.getId().equals(anil.getId()));
	}
	
}
