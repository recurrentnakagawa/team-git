package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InnRepository extends JpaRepository<Inn, Integer> {
	Inn findByInnCode(int innCode);
	List<Inn> findByPrefecturesCode(String ruralcode);
	Inn findByInnName(String innName);
	Inn findByInnNameAndInnAddress(String innName, String innAddress);
}