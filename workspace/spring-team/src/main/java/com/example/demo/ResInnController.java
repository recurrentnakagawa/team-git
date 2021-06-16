package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResInnController {
	private static final String DATEFORMAT = "yyyy-MM-dd";
	
	@Autowired
	HttpSession session;
	
	@Autowired
	InnRepository innRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ClientRepository clientRepository;

	/**
	 * 予約画面に遷移する
	 */
	@RequestMapping(value="/reservation", method=RequestMethod.POST)
	public ModelAndView reservation(
			@RequestParam("innName") String innName,
			@RequestParam("roomName") String roomName,
			ModelAndView mv) {
		if((int)session.getAttribute("login") == 0) {
			//初期Formの作成
			LoginForm bean = new LoginForm();
			mv.addObject("bean", bean);
			mv.setViewName("login");
			return mv;
		}
		//初期Formの作成
		ResInfoInputForm rbean = new ResInfoInputForm();
		Inn innBean = innRepository.findByInnName(innName);
		Room roomBean = roomRepository.findByRoomName(roomName);
		//Client clientBean = clientRepository.findByClientCode(session.getClientCode());
		rbean.setInnName(innBean.getInnName());
		rbean.setRoomName(roomBean.getRoomName());
		//本日の日付の取得
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat(DATEFORMAT);
		String checkinDate = sdFormat.format(date);
		rbean.setCheckinDate(checkinDate);
		//明日の日付の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		String checkoutDate = sdFormat.format(date);
		rbean.setCheckoutDate(checkoutDate);
		rbean.setRoomPrice(roomBean.getRoomPrice());
		
		mv.setViewName("resInfoInput");
		return mv;
	}	
}
