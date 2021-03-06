package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.DAOException;
import com.example.dao.MyReservationDAO;
import com.example.dao.RecInnDAO;
import com.example.dao.SearchInnDAO;

@Controller
public class FrameController {
	private static final String DATEFORMAT = "yyyy-MM-dd";
	private static final int PREFECTURESCODE = 13;
	private static final int SELHIGHPRICE = 10000;
	
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
		bean.setSelHighPrice(SELHIGHPRICE);
		//人数・部屋数リストの取得
		List<Integer> selectList=selectList();
		mv.addObject("selectList", selectList);
		mv.addObject("bean", bean);
		mv.setViewName("search");
		return mv;
	}
	
	//検索結果を表示する
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView doSearch(
			@ModelAttribute("bean") SearchForm bean,
			@RequestParam("checkinDate") String checkinDate,
			@RequestParam("checkoutDate") String checkoutDate,
			@RequestParam("selPrefectures") int selPrefectures,
			@RequestParam("selLowPrice") int selLowPrice,
			@RequestParam("selHighPrice") int selHighPrice,
			@RequestParam("selPeople") int selPeople,
			@RequestParam("selRooms") int selRooms,
			ModelAndView mv) throws ParseException {
		//本日の日付の取得
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		//前日の日付の取得
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
        Date checkindate = sdFormat.parse(checkinDate);
        Date checkoutdate = sdFormat.parse(checkoutDate);
		//入力された日付をセットする
		bean.setCheckinDate(checkinDate);
		bean.setCheckoutDate(checkoutDate);
		//都道府県リストの取得
		List<Prefectures> prefecturesList = prefecturesRepository.findAll();
		mv.addObject("prefecturesList",prefecturesList);
		//入力された都道府県をセットする
		bean.setPrefecturesCode(selPrefectures);
		//価格リストの取得
		List<Integer> priceList=selPrice();
		mv.addObject("priceList", priceList);
		//入力された価格をセットする
		bean.setSelLowPrice(selLowPrice);
		bean.setSelHighPrice(selHighPrice);
		//人数・部屋数リストの取得
		List<Integer> selectList=selectList();
		mv.addObject("selectList", selectList);
		//入力された人数・部屋数をセットする
		bean.setSelPeople(selPeople);
		bean.setSelRooms(selRooms);
		mv.addObject("bean", bean);
		//エラーチェック
		if(checkindate.before(date) || checkoutdate.before(date)) {
			mv.addObject("err_msg", "現在の日付よりも前の日付は入力できません");
			mv.setViewName("search");
			return mv;
		}
		if(checkindate.after(checkoutdate)) {
			mv.addObject("err_msg", "チェックイン日よりもチェックアウト日が前になっています");
			mv.setViewName("search");
			return mv;
		}
		if(selLowPrice >= selHighPrice) {
			mv.addObject("err_msg", "最低価格が最高価格と同じもしくは超えています");
			mv.setViewName("search");
			return mv;
		}
		mv.addObject("reloadFlg", "1");
		mv.setViewName("search");
		return mv;
	}
	
	@RequestMapping("/searchFlg")
	public ModelAndView searchFlg(
			@RequestParam("checkinDate") String checkinDate,
			@RequestParam("checkoutDate") String checkoutDate,
			@RequestParam("selPrefectures") String selPrefectures,
			@RequestParam("selLowPrice") String selLowPrice,
			@RequestParam("selHighPrice") String selHighPrice,
			@RequestParam("selPeople") String selPeople,
			@RequestParam("selRooms") String selRooms,
			ModelAndView mv) throws DAOException {
		int sellowprice = Integer.parseInt(selLowPrice);
		int selhighprice = Integer.parseInt(selHighPrice);
		int selpeople = Integer.parseInt(selPeople);
		int selrooms = Integer.parseInt(selRooms);
		SearchInnDAO dao = new SearchInnDAO();
		List<SearchBean> srcInnList = dao.srcInn(selPrefectures, sellowprice, selhighprice, selpeople, selrooms);
		//Mapに格納
		Map<Integer,List<SearchBean>> srcInnMap = new HashMap<Integer,List<SearchBean>>();
		for(SearchBean srcInn : srcInnList) {
			List<SearchBean> beanList = srcInnMap.get(srcInn.getInnCode());
			if(beanList==null || beanList.size()==0) {
				beanList=new ArrayList<SearchBean>();
			}
			beanList.add(srcInn);
			srcInnMap.put(srcInn.getInnCode(), beanList);
		}
		mv.addObject("srcInnMap", srcInnMap);
		mv.addObject("selPeople", selPeople);
		mv.addObject("selRooms", selRooms);
		mv.addObject("checkinDate", checkinDate);
		mv.addObject("checkoutDate", checkoutDate);
		mv.addObject("srcListFlg", 0);
		if(srcInnList.size()==0) {
			mv.addObject("noInn_msg", "検索条件に当てはまる宿・ホテルが見つかりません");
			mv.addObject("srcListFlg", 1);
		}
		mv.setViewName("innSearch");
		return mv;
	}
	
	@RequestMapping("/top")
	public ModelAndView main(ModelAndView mv) throws DAOException {
		RecInnDAO dao = new RecInnDAO();
		List<Inn> recInnList = dao.RecInn();
		mv.addObject("recInnList", recInnList);
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
	
	@RequestMapping("/mypageRes")
	public ModelAndView mypageRes(ModelAndView mv) throws DAOException {
		Client client = (Client)session.getAttribute("loginUser");
		
		java.util.Date dates = new java.util.Date();
		java.sql.Date date = new java.sql.Date(dates.getTime());
		
		MyReservationDAO dao = new MyReservationDAO();
		
		List<MyReservationBean> futureReservationList =  dao.findByFutureReservation(client.getClientCode(),"0",date);
		mv.addObject("futureReservationList", futureReservationList);
		
		List<MyReservationBean> pastReservationList =  dao.findByPastReservation(client.getClientCode(),"0",date);
		mv.addObject("pastReservationList", pastReservationList);
		if (futureReservationList.size() == 0) {
			mv.addObject("message_future", "現在予約中の宿はありません");
			mv.addObject("futureFlg", 0);
		}else {
			mv.addObject("message_future", "現在の予約");
		}
		if (pastReservationList.size() == 0) {
			mv.addObject("message_past", "過去に予約した宿がありません");
			mv.addObject("pastFlg", 1);
		}else {
			mv.addObject("pastFlg", 0);
			mv.addObject("message_past", "過去の予約");
		}
		mv.setViewName("mypageRes");
		return mv;
	}
	
	@RequestMapping("/mypageEdit")
	public ModelAndView mypageEdit(ModelAndView mv) {
		mv.setViewName("mypageEdit");
		addUserForm bean = new addUserForm();
		mv.addObject("addUserBean", bean);
		return mv;
	}
	
}
