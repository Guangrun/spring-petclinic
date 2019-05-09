/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.samples.petclinic.jpa.repository.OwnerRepository;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.mongo.repository.MongoOwnerRepository;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages="org.springframework.samples.petclinic.jpa.repository")
@EnableMongoRepositories(basePackages="org.springframework.samples.petclinic.mongo.repository")
public class PetClinicApplication implements CommandLineRunner {

	
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	MongoOwnerRepository mongoOwnerRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		ownerRepository.findAll().stream().forEach(o -> saveToMongo(o));
		
		System.out.println("Find owner with lastname started by Davis");
		mongoOwnerRepository.findByLastNameLike("Davis").forEach(o-> System.out.println(o));
		
	}
	
	private void saveToMongo(Owner o) {
	    Owner owner = new Owner();
	    owner.setAddress(o.getAddress());
	    owner.setCity(o.getCity());
	    owner.setFirstName(o.getFirstName());
	    owner.setId(o.getId());
	    owner.setLastName(o.getLastName());
	    owner.setPetsInternal(new HashSet<>());
	    owner.setTelephone(o.getTelephone());
		mongoOwnerRepository.save(owner);
		
	}

}
