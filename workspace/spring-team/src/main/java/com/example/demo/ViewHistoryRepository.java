package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Integer> {

	ViewHistory findByClientCode(Integer clientCode);
	
}
