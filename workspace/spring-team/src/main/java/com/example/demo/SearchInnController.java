package com.example.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.RecInnDAO;
import com.example.dao.ReviewDAO;
import com.example.dao.SearchInnDAO;

@Controller
public class SearchInnController {
	private static final String DATEFORMAT = "yyyy-MM-dd";
	
	@Autowired
	HttpSession session;
	
	@Autowired
	InnRepository innRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ViewHistoryRepository viewHistoryRepository;
	
	//地方選択後の宿一覧表示
	@RequestMapping(value="/showRuralInn/{ruralCode}/{ruralName}")
	public ModelAndView showRuralInn(
			@PathVariable("ruralCode") String ruralCode,
			@PathVariable("ruralName") String ruralName,
			ModelAndView mv) throws DAOException {
		RecInnDAO dao = new RecInnDAO();
		
		List<Inn> recRuralInnList = dao.RecRuralInn(ruralCode);
		mv.addObject("rural_msg", ruralName);
		mv.addObject("recInnList",recRuralInnList);
		mv.setViewName("top");
		return mv;
	}
	
	//宿詳細表示
		@RequestMapping(value="/innDetail/{innCode}/{innName}")
		public ModelAndView showInnDetail(
				@PathVariable("innCode") int innCode,
				ModelAndView mv) throws DAOException {
			Inn innBean=innRepository.findByInnCode(innCode);
			List<Review> reviewList=reviewRepository.findByInnCode(innCode);
			
			ReviewDAO dao = new ReviewDAO();
			double reviewAvg = dao.reviewAvg(innCode);
			int reviewCount = dao.reviewCount(innCode);
			
			if(reviewAvg == 0) {
				mv.addObject("reviewZero","レビューがありません");
			}
			if(reviewAvg != 0) {
				BigDecimal bd = new BigDecimal(reviewAvg);
				mv.addObject("reviewAvg",bd.setScale(1, RoundingMode.HALF_UP) + " / 5.0　(" + reviewCount + "件)");
			}
			String scheckinTimestr = innBean.getInnCheckinTime();
			String scheckinTime = scheckinTimestr.substring(0,2);
			String bcheckinTimestr = innBean.getInnCheckinTime();
			String bcheckinTime = bcheckinTimestr.substring(bcheckinTimestr.length() - 2);
			String checkinTime = scheckinTime + ":" + bcheckinTime;
			String scheckoutTimestr = innBean.getInnCheckoutTime();
			String scheckoutTime = scheckoutTimestr.substring(0,2);
			String bcheckoutTimestr = innBean.getInnCheckoutTime();
			String bcheckoutTime = bcheckoutTimestr.substring(bcheckoutTimestr.length() - 2);
			String checkoutTime = scheckoutTime + ":" + bcheckoutTime;
			mv.addObject("checkinTime", checkinTime);
			mv.addObject("checkoutTime", checkoutTime);
			mv.addObject("innBean", innBean);
			mv.addObject("reviewList", reviewList);
			//閲覧履歴を残す
			//ログインされているかを判断する
			int login = (int)session.getAttribute("login");
			if(login==1) {
				//ログインユーザー情報
				Client client = (Client)session.getAttribute("loginUser");
				//現在の日時と時刻を取得
				Timestamp toDayTime = new Timestamp(System.currentTimeMillis());
				//Beanにセット
				ViewHistory view = new ViewHistory(client.getClientCode(),innCode,toDayTime);
				//データベースに追加する
				viewHistoryRepository.saveAndFlush(view);
			}
			mv.addObject("srcFlg", 0);
			mv.setViewName("innDetail");
			return mv;
		}
		
	//宿詳細表示(検索)
		@RequestMapping(value="/innDetail/{innCode}/{innName}/{selPeople}/{selRooms}/{checkinDate}/{checkoutDate}")
		public ModelAndView showSrcInnDetail(
				@PathVariable("innCode") int innCode,
				@PathVariable("selPeople") String selPeople,
				@PathVariable("selRooms") String selRooms,
				@PathVariable("checkinDate") String checkinDate,
				@PathVariable("checkoutDate") String checkoutDate,
				ModelAndView mv) throws DAOException {
			Inn innBean=innRepository.findByInnCode(innCode);
			List<Review> reviewList=reviewRepository.findByInnCode(innCode);
			
			ReviewDAO dao = new ReviewDAO();
			double reviewAvg = dao.reviewAvg(innCode);
			int reviewCount = dao.reviewCount(innCode);
			if(reviewAvg == 0) {
				mv.addObject("reviewZero","レビューがありません");
			}
			if(reviewAvg != 0) {
				BigDecimal bd = new BigDecimal(reviewAvg);
				mv.addObject("reviewAvg",bd.setScale(1, RoundingMode.HALF_UP) + " / 5.0　(" + reviewCount + "件)");
			}
			String scheckinTimestr = innBean.getInnCheckinTime();
			String scheckinTime = scheckinTimestr.substring(0,2);
			String bcheckinTimestr = innBean.getInnCheckinTime();
			String bcheckinTime = bcheckinTimestr.substring(bcheckinTimestr.length() - 2);
			String checkinTime = scheckinTime + ":" + bcheckinTime;
			String scheckoutTimestr = innBean.getInnCheckoutTime();
			String scheckoutTime = scheckoutTimestr.substring(0,2);
			String bcheckoutTimestr = innBean.getInnCheckoutTime();
			String bcheckoutTime = bcheckoutTimestr.substring(bcheckoutTimestr.length() - 2);
			String checkoutTime = scheckoutTime + ":" + bcheckoutTime;
			mv.addObject("checkinTime", checkinTime);
			mv.addObject("checkoutTime", checkoutTime);
			mv.addObject("innBean", innBean);
			mv.addObject("reviewList", reviewList);
			//閲覧履歴を残す
			//ログインされているかを判断する
			int login = (int)session.getAttribute("login");
			if(login==1) {
				//ログインユーザー情報
				Client client = (Client)session.getAttribute("loginUser");
				//現在の日時と時刻を取得
				Timestamp toDayTime = new Timestamp(System.currentTimeMillis());
				//Beanにセット
				ViewHistory view = new ViewHistory(client.getClientCode(),innCode,toDayTime);
				//データベースに追加する
				viewHistoryRepository.saveAndFlush(view);
			}
			mv.addObject("srcFlg", 1);
			mv.addObject("selPeople", selPeople);
			mv.addObject("selRooms", selRooms);
			mv.addObject("checkinDate", checkinDate);
			mv.addObject("checkoutDate", checkoutDate);
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
		mv.addObject("srcFlg", 0);
		mv.setViewName("showRoom");
		return mv;
	}
	
	//部屋一覧表示(検索)
	@RequestMapping(value="/roomList/{innCode}/{innName}/{selPeople}/{selRooms}/{checkinDate}/{checkoutDate}")
	public ModelAndView showSrcRoomList(
			@PathVariable("innCode") int innCode,
			@PathVariable("innName") String innName,
			@PathVariable("selPeople") String selPeople,
			@PathVariable("selRooms") String selRooms,
			@PathVariable("checkinDate") String checkinDate,
			@PathVariable("checkoutDate") String checkoutDate,
			ModelAndView mv) {
		List<Room> roomList=roomRepository.findByInnCode(innCode);
		mv.addObject("innCode", innCode);
		mv.addObject("innName", innName);
		mv.addObject("selPeople", selPeople);
		mv.addObject("selRooms", selRooms);
		mv.addObject("checkinDate", checkinDate);
		mv.addObject("checkoutDate", checkoutDate);
		mv.addObject("roomList", roomList);
		mv.addObject("srcFlg", 1);
		mv.setViewName("showRoom");
		return mv;
	}
	
	//部屋詳細表示
	@RequestMapping(value="/roomDetail/{innCode}/{innName}/{roomCode}/{roomName}")
	public ModelAndView showRoomDetail(
			@PathVariable("innCode") int innCode,
			@PathVariable("innName") String innName,
			@PathVariable("roomCode") int roomCode,
			@PathVariable("roomName") String roomName,
			ModelAndView mv) {
		Room roomBean=roomRepository.findByRoomCode(roomCode);
		mv.addObject("innCode", innCode);
		mv.addObject("innName", innName);
		mv.addObject("roomBean", roomBean);
		mv.addObject("srcFlg", 0);
		mv.setViewName("roomDetail");
		return mv;
	}
	
	//部屋詳細表示(検索)
	@RequestMapping(value="/roomDetail/{innCode}/{innName}/{roomCode}/{roomName}/{selPeople}/{selRooms}/{checkinDate}/{checkoutDate}")
	public ModelAndView showSrcRoomDetail(
			@PathVariable("innCode") int innCode,
			@PathVariable("innName") String innName,
			@PathVariable("roomCode") int roomCode,
			@PathVariable("roomName") String roomName,
			@PathVariable("selPeople") String selPeople,
			@PathVariable("selRooms") String selRooms,
			@PathVariable("checkinDate") String checkinDate,
			@PathVariable("checkoutDate") String checkoutDate,
			ModelAndView mv) throws DAOException, ParseException {
		Room roomBean=roomRepository.findByRoomCode(roomCode);
		int selrooms = Integer.parseInt(selRooms);
		SearchInnDAO dao = new SearchInnDAO();
		SimpleDateFormat sdFormat = new SimpleDateFormat(DATEFORMAT); 
		Date checkindate = sdFormat.parse(checkinDate);
		Date checkoutdate = sdFormat.parse(checkoutDate);
		java.sql.Date checkInDate = new java.sql.Date(checkindate.getTime());
		java.sql.Date checkOutDate = new java.sql.Date(checkoutdate.getTime());
		ResCheck resBean = dao.resInnRoom(checkInDate, checkOutDate, roomCode);
		//予約のエラーチェック
		if(!(resBean==null)) {
			if(selrooms > roomBean.getRoomTotal() - resBean.getResSum()) {
				mv.addObject("err_msg", "入力された部屋数が現在の残り部屋数を超えています");
				mv.addObject("srcListFlg", 0);
				mv.setViewName("innSearch");
				return mv;
			}
		}
		mv.addObject("innCode", innCode);
		mv.addObject("innName", innName);
		mv.addObject("roomBean", roomBean);
		mv.addObject("selPeople", selPeople);
		mv.addObject("selRooms", selRooms);
		mv.addObject("checkinDate", checkinDate);
		mv.addObject("checkoutDate", checkoutDate);
		mv.addObject("srcFlg", 1);
		mv.setViewName("roomDetail");
		return mv;
	}
}