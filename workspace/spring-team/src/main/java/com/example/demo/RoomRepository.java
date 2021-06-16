package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
	List<Room> findByInnCode(int innCode);
	Room findByroomCode(int roomCode);
	Room findByRoomName(String roomName);
}