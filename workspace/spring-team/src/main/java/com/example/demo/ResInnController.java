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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.SearchInnDAO;

@Controller
public class ResInnController {
	private static final String DATEFORMAT = "yyyy-MM-dd";
	private static final String RESFlag = "0";

	@Autowired
	HttpSession session;

	@Autowired
	InnRepository innRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	ReservationRepository reservationRepository;

	/**
	 * 宿予約画面に遷移する
	 */
	@RequestMapping(value = "/reservation/{innCode}/{roomCode}", method = RequestMethod.POST)
	public ModelAndView reservation(
			@PathVariable("innCode") int innCode,
			@PathVariable("roomCode") int roomCode,
			ModelAndView mv) {
		if ((int) session.getAttribute("login") == 0) {
			// LoginFormの作成
			LoginForm bean = new LoginForm();
			mv.addObject("bean", bean);
			mv.setViewName("login");
			return mv;
		}
		Inn innBean = innRepository.findByInnCode(innCode);
		Room roomBean = roomRepository.findByRoomCode(roomCode);
		// 本日の日付の取得
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat(DATEFORMAT);
		String checkinDate = sdFormat.format(date);
		// 明日の日付の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		String checkoutDate = sdFormat.format(date);
		// 部屋の最大人数の取得
		List<Integer> selPeopleList = selPeople(roomBean.getRoomMax());
		// 部屋数リストの取得
		List<Integer> selRoomsList = selRooms(roomBean.getRoomTotal());
		mv.addObject("innCode", innCode);
		mv.addObject("roomCode", roomCode);
		mv.addObject("innBean", innBean);
		mv.addObject("roomBean", roomBean);
		mv.addObject("checkinDate", checkinDate);
		mv.addObject("checkoutDate", checkoutDate);
		mv.addObject("selPeople", selPeopleList);
		mv.addObject("selRooms", selRoomsList);
		mv.setViewName("resInfoInput");
		return mv;
	}

	/**
	 * 宿予約内容確認画面を遷移する
	 * @throws ParseException 
	 * @throws DAOException 
	 */	
	@RequestMapping(value="/conReservation/{innCode}/{roomCode}", method=RequestMethod.POST)
	public ModelAndView conReservation(
			@PathVariable("innCode") int innCode,
			@PathVariable("roomCode") int roomCode,
			@RequestParam(name="checkinDate") String checkinDate,
			@RequestParam(name="checkoutDate") String checkoutDate,
			@RequestParam(name="selPeople") String selPeople,
			@RequestParam(name="selRooms") String selRooms,
		ModelAndView mv) throws ParseException, DAOException {
		//本日の日付の取得
		Date date = new Date();
		Inn innBean = innRepository.findByInnCode(innCode);
		Room roomBean = roomRepository.findByRoomCode(roomCode);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		//前日の日付の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
        Date checkindate = sdFormat.parse(checkinDate);
        Date checkoutdate = sdFormat.parse(checkoutDate);
		// 部屋の最大人数の取得
		List<Integer> selPeopleList = selPeople(roomBean.getRoomMax());
		// 部屋数リストの取得
		List<Integer> selRoomsList = selRooms(roomBean.getRoomTotal());
		mv.addObject("innCode", innCode);
		mv.addObject("roomCode", roomCode);
		mv.addObject("innBean", innBean);
		mv.addObject("roomBean", roomBean);
		mv.addObject("checkinDate", checkinDate);
		mv.addObject("checkoutDate", checkoutDate);
		mv.addObject("selPeople", selPeopleList);
		mv.addObject("selRooms", selRoomsList);
		//日付のエラーチェック
        if(checkindate.before(date) || checkoutdate.before(date)) {
			mv.addObject("err_msg", "現在の日付よりも前の日付は入力できません");
			mv.setViewName("resInfoInput");
			return mv;
		}
		if(checkoutdate.before(checkindate)) {
			mv.addObject("err_msg", "チェックイン日よりもチェックアウト日が前になっています");
			mv.setViewName("resInfoInput");
			return mv;
		}
		//予約部屋数のエラーチェック
		int selrooms = Integer.parseInt(selRooms);
		SearchInnDAO dao = new SearchInnDAO();
		java.sql.Date checkInDate = new java.sql.Date(checkindate.getTime());
		java.sql.Date checkOutDate = new java.sql.Date(checkoutdate.getTime());
		ResCheck resBean = dao.resInnRoom(checkInDate, checkOutDate, roomCode);
		//予約のエラーチェック
		if(!(resBean==null)) {
			if(selrooms > roomBean.getRoomTotal() - resBean.getResSum()) {
				mv.addObject("err_msg", "入力された部屋数が現在の残り部屋数を超えています");
				mv.setViewName("resInfoInput");
				return mv;
			}
		}
		mv.setViewName("conResdetail");
		return mv;
	}
	
	/**
	 * 宿予約内容確認画面を遷移する(検索)
	 */	
	@RequestMapping(value="/conReservation/{innCode}/{roomCode}/{selPeople}/{selRooms}/{checkinDate}/{checkoutDate}", method=RequestMethod.POST)
	public ModelAndView conSrcReservation(
			@PathVariable("innCode") int innCode,
			@PathVariable("roomCode") int roomCode,
			@PathVariable("selPeople") String selPeople,
			@PathVariable("selRooms") String selRooms,
			@PathVariable("checkinDate") String checkinDate,
			@PathVariable("checkoutDate") String checkoutDate,
			ModelAndView mv) {
		Inn innBean = innRepository.findByInnCode(innCode);
		Room roomBean = roomRepository.findByRoomCode(roomCode);
		mv.addObject("innCode", innCode);
		mv.addObject("roomCode", roomCode);
		mv.addObject("innBean", innBean);
		mv.addObject("roomBean", roomBean);
		mv.addObject("checkinDate", checkinDate);
		mv.addObject("checkoutDate", checkoutDate);
		mv.addObject("selPeople", selPeople);
		mv.addObject("selRooms", selRooms);
		mv.setViewName("conResdetail");
		return mv;
	}
	
	/**
	 * 宿予約を実行する
	 * @throws ParseException 
	 */	
	@RequestMapping(value="/regReservation/{innCode}/{roomCode}", method=RequestMethod.POST)
	public ModelAndView regReservation(
			@PathVariable("innCode") int innCode,
			@PathVariable("roomCode") int roomCode,
			@RequestParam("checkinDate") String checkinDate,
			@RequestParam("checkoutDate") String checkoutDate,
			@RequestParam("selPeople") int selPeople,
			@RequestParam("selRooms") int selRooms,
			@RequestParam("resPrice") int resPrice,
			ModelAndView mv) throws ParseException {
		Client client = (Client)session.getAttribute("loginUser");
		int clientCode = client.getClientCode();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkindate = sdFormat.parse(checkinDate);
        Date checkoutdate = sdFormat.parse(checkoutDate);
        Reservation reservation = new Reservation(clientCode,roomCode,checkindate,checkoutdate,selRooms,resPrice,selPeople,RESFlag,"0");
		reservationRepository.saveAndFlush(reservation);
		mv.setViewName("myFrame");
		return mv;
	} 

	// 部屋に合わせた人数リスト生成メソッド
	public List<Integer> selPeople(int roomMax) {
		List<Integer> selPeople = new ArrayList<Integer>();
		for (int i = 1; i <= roomMax; i++) {
			selPeople.add(i);
		}
		return selPeople;
	}

	// 部屋に合わせた部屋数リスト生成メソッド
	public List<Integer> selRooms(int roomTotal) {
		List<Integer> selRooms = new ArrayList<Integer>();
		for (int i = 1; i <= roomTotal; i++) {
			selRooms.add(i);
		}
		return selRooms;
	}
}
