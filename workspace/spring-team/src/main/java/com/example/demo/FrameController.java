package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private static final int PREFECTURESCODE = 13;
	@Autowired
	HttpSession session;
	
	@Autowired
	PrefecturesRepository prefecturesRepository;
	
	@Autowired
	InnRepository innRepository;
	
	@Autowired
	RuralRepository ruralRepository;
	
	@RequestMapping("/")
	public String normal() {
		// セッション情報はクリアする
		session.invalidate();
		session.setAttribute("login", 0);
		return "innFrame";
	}
	
	@RequestMapping("/frame")
	public String frame() {
		return "innFrame";
	}
	
	@RequestMapping("/menu")
	public ModelAndView menu(ModelAndView mv) {
		List<Rural> ruralList=ruralRepository.findAll();
		mv.addObject("ruralList", ruralList);
		mv.setViewName("menu");
		return mv;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(ModelAndView mv) {
		//初期Formの作成
		SearchForm bean = new SearchForm();
		//本日の日付の取得
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat(DATEFORMAT);
		String checkinDate = sdFormat.format(date);
		bean.setCheckinDate(checkinDate);
		//明日の日付の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		String checkoutDate = sdFormat.format(date);
		bean.setCheckoutDate(checkoutDate);
		//都道府県リストの取得
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		mv.addObject("prefecturesList",prefecturesList);
		bean.setPrefecturesCode(PREFECTURESCODE);
		//価格リストの取得
		List<Integer> priceList=selPrice();
		mv.addObject("priceList", priceList);
		//人数・部屋数リストの取得
		List<Integer> selectList=selectList();
		mv.addObject("selectList", selectList);
		mv.addObject("bean", bean);
		mv.setViewName("search");
		return mv;
	}
	
	@RequestMapping("/top")
	public ModelAndView main(ModelAndView mv) {
		List<Inn> innList=innRepository.findAll();
		mv.addObject("innList", innList);
		mv.addObject("rural_msg", "あなたへ");
		mv.setViewName("top");
		return mv;
	}
	
	//価格リスト生成メソッド
	public List<Integer> selPrice() {
		List<Integer> priceList = new ArrayList<Integer>();
		int i=1;
		int price=0;
		for(i=1;i<11;i++) {
			price+=1000;
			priceList.add(price);
		}
		for(i=1;i<11;i++) {
			price+=2000;
			priceList.add(price);
		}
		for(i=1;i<3;i++) {
			price+=10000;
			priceList.add(price);
		}
		return priceList;
	}
	//人数・部屋数リスト生成メソッド
	public List<Integer> selectList() {
		List<Integer> selectList = new ArrayList<Integer>();
		for(int i=1;i<11;i++) {
			selectList.add(i);
		}
		return selectList;
	}
	
	@RequestMapping("/myFrame")
	public String myFrame() {
		return "myFrame";
	}
	
	@RequestMapping("/myMenu")
	public String myMenu() {
		return "myMenu";
	}
	
	@RequestMapping("/myRes")
	public String myRes() {
		return "mypageRes";
	}
	
	@RequestMapping("/mypageRes")
	public ModelAndView mypageRes(ModelAndView mv) {
		mv.setViewName("mypageRes");
		return mv;
	}
	
	@RequestMapping("/mypageView")
	public ModelAndView mypageView(ModelAndView mv) {
		mv.setViewName("mypageView");
		return mv;
	}
	
	@RequestMapping("/mypageEdit")
	public ModelAndView mypageEdit(ModelAndView mv) {
		mv.setViewName("mypageEdit");
		return mv;
	}
	
}
