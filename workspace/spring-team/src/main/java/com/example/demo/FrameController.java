package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FrameController {
	private static final String DATEFORMAT = "yyyy-MM-dd";
	
	@Autowired
	HttpSession session;
	
	@Autowired
	PrefecturesRepository prefecturesRepository;
	
	@RequestMapping("/")
	public String login() {
		//これ消して
		// セッション情報はクリアする
		session.invalidate();
		return "innFrame";
	}
	
	@RequestMapping("/menu")
	public ModelAndView menu(ModelAndView mv) {
		mv.setViewName("menu");
		return mv;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(ModelAndView mv) {
		//本日の日付の取得
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat(DATEFORMAT);
		String checkinDate = sdFormat.format(date);
		mv.addObject("checkinDate", checkinDate);
		//明日の日付の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		String checkoutDate = sdFormat.format(date);
		mv.addObject("checkoutDate", checkoutDate);
		//都道府県リストの取得
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		mv.addObject("prefecturesList",prefecturesList);
		mv.setViewName("search");
		return mv;
	}
	
	@RequestMapping("/top")
	public ModelAndView main(ModelAndView mv) {
		mv.setViewName("top");
		return mv;
	}
}
