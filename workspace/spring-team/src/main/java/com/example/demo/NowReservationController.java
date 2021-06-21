package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.MyReservationDAO;
import com.example.dao.RecInnDAO;

@Controller
public class NowReservationController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	PrefecturesRepository prefecturesRepository;
	
	@Autowired
	InnRepository innRepository;
	
	@Autowired
	RuralRepository ruralRepository;
	
	@Autowired
	ReservationRepository reservationRepository;

	
	@RequestMapping("/reservationDetail/{reservationCode}")
	public ModelAndView reservationDetail(
			@PathVariable(name = "reservationCode") int reservationCode,
			ModelAndView mv
	) throws DAOException {
		
		MyReservationDAO dao = new MyReservationDAO();
		NowReservationBean bean = dao.findNowReservation(reservationCode);
		mv.addObject("innName", bean.getInnName());
		mv.addObject("roomName", bean.getRoomName());
		mv.addObject("checkinDate", bean.getCheckinDate());
		mv.addObject("checkoutDate", bean.getCheckoutDate());
		mv.addObject("reservationUserTotal", bean.getReservationUserTotal());
		mv.addObject("reservationRoomTotal", bean.getReservationRoomTotal());
		mv.addObject("reservationPrice", bean.getReservationPrice());
		mv.addObject("clientName", bean.getClientName());
		mv.addObject("clientTel", bean.getClientTel());
		mv.setViewName("reservationDetail");
		return mv;
	}
	
	@RequestMapping("/reservationCancel/{reservationCode}")
	public ModelAndView reservationCancel(
			@PathVariable(name = "reservationCode") int reservationCode,
			ModelAndView mv
	) throws DAOException {	
		Reservation bean = reservationRepository.findByReservationCode(reservationCode);
		bean.setReservationInvalid("1");
		
		reservationRepository.saveAndFlush(bean);
		
		Client client = (Client)session.getAttribute("loginUser");
		
		java.util.Date dates = new java.util.Date();
		java.sql.Date date = new java.sql.Date(dates.getTime());
		
		MyReservationDAO dao = new MyReservationDAO();
		
		List<MyReservationBean> futureReservationList =  dao.findByFutureReservation(client.getClientCode(),"0",date);
		mv.addObject("futureReservationList", futureReservationList);
		
		List<MyReservationBean> pastReservationList =  dao.findByPastReservation(client.getClientCode(),"0",date);
		mv.addObject("pastReservationList", pastReservationList);

		
		mv.setViewName("mypageRes");
		return mv;
	}
	
}
