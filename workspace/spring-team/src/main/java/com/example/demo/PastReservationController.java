package com.example.demo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@Controller
public class PastReservationController {

	@Autowired
	HttpSession session;

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	ReservationRepository reservationRepository;

	@RequestMapping("/pastReservation/{innCode}/{reservationCode}")
	public ModelAndView pastReservation(
			@PathVariable("innCode") int innCode,
			@PathVariable("reservationCode") int reservationCode,ModelAndView mv) {
		session.setAttribute("innCode", innCode);
		session.setAttribute("reservationCode", reservationCode);
		mv.setViewName("review");
		return mv;
	}
	
	@RequestMapping("/reviewInn")
	public ModelAndView reviewInn(
			@RequestParam("reviewStar") String reviewStarStr,
			@RequestParam("content") String content,ModelAndView mv) throws DAOException {
		int innCode = (int)session.getAttribute("innCode");
		int reservationCode = (int)session.getAttribute("reservationCode");
		Timestamp toDayTime = new Timestamp(System.currentTimeMillis());
		int reviewStar = Integer.parseInt(reviewStarStr);
		Review review = new Review(innCode,reviewStar,content,toDayTime);
		reviewRepository.saveAndFlush(review);
		Reservation reservation = reservationRepository.findByReservationCode(reservationCode);
		reservation.setReviewFlag("1");
		reservationRepository.saveAndFlush(reservation);
		//一覧を表示
		Client client = (Client)session.getAttribute("loginUser");	
		java.util.Date dates = new java.util.Date();
		java.sql.Date date = new java.sql.Date(dates.getTime());
		MyReservationDAO dao = new MyReservationDAO();
		List<MyReservationBean> pastReservationList =  dao.findByPastReservation(client.getClientCode(),"0",date);
		List<MyReservationBean> futureReservationList =  dao.findByFutureReservation(client.getClientCode(),"0",date);
		mv.addObject("futureReservationList", futureReservationList);
		mv.addObject("pastReservationList", pastReservationList);
		if (futureReservationList.size() == 0) {
			mv.addObject("message_future", "現在予約中の宿はありません");
		}else {
			mv.addObject("message_future", "現在の予約");
		}
		if (pastReservationList.size() == 0) {
			mv.addObject("message_past", "過去に予約した宿がありません");
		}else {
			mv.addObject("message_past", "過去の予約");
		}
		//リストの受け渡し
		mv.addObject("pastReservationList", pastReservationList);
		mv.setViewName("mypageRes");
		mv.addObject("message", "レビューありがとうございます！レビューが追加されました。");
		return mv;
	}
	
	@RequestMapping("/reviewDelet/{reservationCode}")
	public ModelAndView reviewDelete(@PathVariable("reservationCode") int reservationCode,ModelAndView mv) throws DAOException {
		//予約を削除
		reservationRepository.deleteById(reservationCode);
		//一覧を表示（過去）
		Client client = (Client)session.getAttribute("loginUser");	
		java.util.Date dates = new java.util.Date();
		java.sql.Date date = new java.sql.Date(dates.getTime());
		MyReservationDAO dao = new MyReservationDAO();
		List<MyReservationBean> pastReservationList =  dao.findByPastReservation(client.getClientCode(),"0",date);
		if (pastReservationList.size() == 0) {
			mv.addObject("message_past", "過去に予約した宿がありません");
		}else {
			mv.addObject("message_past", "過去の予約");
		}
		//一覧を表示（現在）
		List<MyReservationBean> futureReservationList =  dao.findByFutureReservation(client.getClientCode(),"0",date);
		mv.addObject("futureReservationList", futureReservationList);
		mv.addObject("pastReservationList", pastReservationList);
		if (futureReservationList.size() == 0) {
			mv.addObject("message_future", "現在予約中の宿はありません");
		}else {
			mv.addObject("message_future", "現在の予約");
		}
		mv.setViewName("mypageRes");
		return mv;
	}
}

