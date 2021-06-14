package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client findByClientEmailAndClientPassword(String email, String password);

	Client findByClientEmail(String email);

	Client findByClientCode(int codes);
	
}
