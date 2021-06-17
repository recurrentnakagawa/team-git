package com.example.demo;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.RecInnDAO;

@Controller
public class SearchInnController {
	
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
				ModelAndView mv) {
			Inn innBean=innRepository.findByInnCode(innCode);
			List<Review> reviewList=reviewRepository.findByInnCode(innCode);
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
		mv.setViewName("roomDetail");
		return mv;
	}
}