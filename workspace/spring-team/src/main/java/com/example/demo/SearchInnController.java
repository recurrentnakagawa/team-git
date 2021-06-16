package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SearchInnController {
	
	@Autowired
	InnRepository innRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	//地方選択後の宿一覧表示
	@RequestMapping(value="/showRuralInn/{ruralCode}/{ruralName}")
	public ModelAndView showRuralInn(
			@PathVariable("ruralCode") String ruralCode,
			@PathVariable("ruralName") String ruralName,
			ModelAndView mv) {
		List<Inn> innList=innRepository.findByPrefecturesCode(ruralCode);
		mv.addObject("rural_msg", ruralName);
		mv.addObject("innList",innList);
		mv.setViewName("top");
		return mv;
	}
	
	//部屋詳細表示
	@RequestMapping(value="/innDetail/{innCode}/{innName}")
	public ModelAndView showInnDetail(
			@PathVariable("innCode") int innCode,
			ModelAndView mv) {
		Inn innBean=innRepository.findByInnCode(innCode);
		List<Review> reviewList=reviewRepository.findByInnCode(innCode);
		mv.addObject("innBean", innBean);
		mv.addObject("reviewList", reviewList);
		mv.setViewName("innDetail");
		return mv;
	}
	
	//部屋一覧表示
	@RequestMapping(value="/roomList/{innCode}/{innName}")
	public ModelAndView showRoomList(
			@PathVariable("innCode") int innCode,
			@PathVariable("innName") String innName,
			ModelAndView mv) {
		List<Room> roomList=roomRepository.findByInnCode(innCode);
		mv.addObject("innCode", innCode);
		mv.addObject("innName", innName);
		mv.addObject("roomList", roomList);
		mv.setViewName("showRoom");
		return mv;
	}
	
	//部屋詳細表示
	@RequestMapping(value="/roomDetail/{innName}/{roomCode}/{roomName}")
	public ModelAndView showRoomDetail(
			@PathVariable("innName") String innName,
			@PathVariable("roomCode") int roomCode,
			@PathVariable("roomName") String roomName,
			ModelAndView mv) {
		Room roomBean=roomRepository.findByroomCode(roomCode);
		mv.addObject("innName", innName);
		mv.addObject("roomBean", roomBean);
		mv.setViewName("roomDetail");
		return mv;
	}
}