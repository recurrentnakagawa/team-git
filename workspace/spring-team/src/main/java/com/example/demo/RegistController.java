package com.example.demo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistController {

	@Autowired
	HttpSession session;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	PointRepository pointRepository;

	@RequestMapping("/regist")
	public ModelAndView regist(ModelAndView mv) {
		// 初期Formの作成
		addUserForm bean = new addUserForm();
		mv.addObject("addUserBean", bean);
		List<Question> questionList = questionRepository.findAll();
		mv.addObject("questionList", questionList);
		mv.setViewName("addUser");
		return mv;
	}

	@RequestMapping(value = "/registUser", method = RequestMethod.POST)
	public ModelAndView registUser(@ModelAttribute("addUserBean") addUserForm bean, ModelAndView mv) {
		mv.addObject("addUserBean", bean);
		List<Question> questionList = questionRepository.findAll();
		mv.addObject("questionList", questionList);
		// 未入力チェックフラグ
		int flg = 0;
		// 電話番号未入力チェックフラグ
		int telFlg = 0;
		// 未入力チェック
		if (bean.getEmail().length() == 0) {
			mv.addObject("message_email", "メールアドレスが入力されていません");
			flg = 1;
		}
		if (bean.getRe_email().length() == 0) {
			mv.addObject("message_re_email", "メールアドレス再入力が入力されていません");
			flg = 1;
		}
		if (bean.getPassword().length() == 0) {
			mv.addObject("message_password", "パスワードが入力されていません");
			flg = 1;
		}
		if (bean.getRe_password().length() == 0) {
			mv.addObject("message_re_password", "メールアドレス再入力が入力されていません");
			flg = 1;
		}
		if (bean.getName().length() == 0) {
			mv.addObject("message_name", "名前が入力されていません");
			flg = 1;
		}
		if (bean.getKana().length() == 0) {
			mv.addObject("message_kana", "名前（フリガナ）が入力されていません");
			flg = 1;
		}
		if (bean.getPrefectures().length() == 0) {
			mv.addObject("message_prefectures", "都道府県が入力されていません");
			flg = 1;
		}
		if (bean.getCities().length() == 0) {
			mv.addObject("message_cities", "市町村が入力されていません");
			flg = 1;
		}
		if (bean.getHouse_number().length() == 0) {
			mv.addObject("message_house_number", "番地・部屋番号が入力されていません");
			flg = 1;
		}
		if (bean.getTel1().length() == 0) {
			telFlg = 1;
		}
		if (bean.getTel2().length() == 0) {
			telFlg = 1;
		}
		if (bean.getTel3().length() == 0) {
			telFlg = 1;
		}
		if (bean.getAnswer().length() == 0) {
			mv.addObject("message_answer", "質問の答えが入力されていません");
			flg = 1;
		}
		if (telFlg == 1) {
			mv.addObject("message_tel", "電話番号が入力されていません");
			flg = 1;
		}
		// 未入力が１つでもあると新規会員登録画面へ遷移
		if (flg == 1) {
			mv.setViewName("addUser");
			return mv;
		}
		// 同じメールアドレスが登録されていないかチェック
		Client user = clientRepository.findByClientEmail(bean.getEmail());
		if (user != null) {
			mv.addObject("message_email", "すでに存在しているメールアドレスです");
			mv.setViewName("addUser");
			return mv;
		}
		// メールアドレスフォーマットチェック
		if (!bean.getEmail().contains("@")) {
			mv.addObject("message_email", "メールアドレスのフォーマットが正しくありません");
			mv.setViewName("addUser");
			return mv;
		}
		// メールアドレスとメールアドレス再入力が一致しているかチェックする
		if (!bean.getEmail().equals(bean.getRe_email())) {
			mv.addObject("message_email", "メールアドレスとメールアドレス再入力が一致しません");
			mv.setViewName("addUser");
			return mv;
		}
		// パスワードフォーマットチェック
		if (passcheck(bean.getPassword()) == false) {
			mv.addObject("message_password", "半角の大文字小文字英数字を含む8文字以上20文字以下のパスワードにしてください");
			mv.setViewName("addUser");
			return mv;
		}
		// パスワードとパスワード再入力が一致しているかチェックする
		if (!bean.getPassword().equals(bean.getRe_password())) {
			mv.addObject("message_password", "パスワードとパスワード再入力が一致しません");
			mv.setViewName("addUser");
			return mv;
		}
		// カタカナフォーマットチェック
		if (kanacheck(bean.getKana()) == false) {
			mv.addObject("message_kana", "全角カタカナで入力してください");
			mv.setViewName("addUser");
			return mv;
		}
		// 電話番号1フォーマットチェック
		if (telcheck(bean.getTel1()) == false) {
			mv.addObject("message_tel", "数値で入力してください");
			mv.setViewName("addUser");
			return mv;
		}
		// 電話番号2フォーマットチェック
		if (telcheck(bean.getTel2()) == false) {
			mv.addObject("message_tel", "数値で入力してください");
			mv.setViewName("addUser");
			return mv;
		}
		// 電話番号3フォーマットチェック
		if (telcheck(bean.getTel3()) == false) {
			mv.addObject("message_tel", "数値で入力してください");
			mv.setViewName("addUser");
			return mv;
		}
		// 電話番号を結合する
		StringBuilder buildTel = new StringBuilder();
		buildTel.append(bean.getTel1());
		buildTel.append("-");
		buildTel.append(bean.getTel2());
		buildTel.append("-");
		buildTel.append(bean.getTel3());
		String tel = buildTel.toString();
		// 住所を結合する
		StringBuilder buildAddress = new StringBuilder();
		buildAddress.append(bean.getPrefectures());
		buildAddress.append(bean.getCities());
		buildAddress.append(bean.getHouse_number());
		String address = buildAddress.toString();
		//メールアドレスの文字数チェック
		if(bean.getEmail().length()>=50) {
			mv.addObject("message_email", "メールアドレスの文字数が多すぎます(50文字まで)");
			mv.setViewName("addUser");
			return mv;
		}
		//名前の文字数チェック
		if(bean.getName().length()>=30) {
			mv.addObject("message_name", "名前の文字数が多すぎます(30文字まで)");
			mv.setViewName("addUser");
			return mv;
		}
		//カタカナの文字数チェック
		if(bean.getKana().length()>=30) {
			mv.addObject("message_kana", "名前(カタカナ)の文字数が多すぎます(30文字まで)");
			mv.setViewName("addUser");
			return mv;
		}
		//住所の文字数チェック
		if(address.length()>=50) {
			mv.addObject("message_prefectures", "住所の文字数が多すぎます（合計50文字まで）");
			mv.setViewName("addUser");
			return mv;
		}
		//電話番号の文字数チェック
		if(tel.length()>=15) {
			mv.addObject("message_tel", "電話番号の文字数が多すぎます（合計１３文字まで）");
			mv.setViewName("addUser");
			return mv;
		}
		//秘密の質問の答えの文字数チェック
		if(bean.getAnswer().length()>=50) {
			mv.addObject("message_answer", "秘密の質問の答えの文字数が多すぎます(50文字まで)");
			mv.setViewName("addUser");
			return mv;
		}
		mv.setViewName("conAddUserInfo");
		mv.addObject("tel", tel);
		return mv;
	}

	@RequestMapping(value = "/regists", method = RequestMethod.POST)
	public ModelAndView regists(@ModelAttribute("addUserBean") addUserForm bean, ModelAndView mv) {
		// 同じメールアドレスが登録されていないかチェック
		Client user = clientRepository.findByClientEmail(bean.getEmail());
		if (user != null) {
			mv.addObject("message_email", "すでに存在しているメールアドレスです");
			mv.setViewName("addUser");
			return mv;
		}
		// 住所を結合する
		StringBuilder buildAddress = new StringBuilder();
		buildAddress.append(bean.getPrefectures());
		buildAddress.append(bean.getCities());
		buildAddress.append(bean.getHouse_number());
		String address = buildAddress.toString();
		
		// 電話番号を結合する
		StringBuilder buildTel = new StringBuilder();
		buildTel.append(bean.getTel1());
		buildTel.append("-");
		buildTel.append(bean.getTel2());
		buildTel.append("-");
		buildTel.append(bean.getTel3());
		String tel = buildTel.toString();
		//ユーザ数をカウントしてポイントテーブルを作成
		List<Client> count = clientRepository.findAll();
		int pointCode=(count.size())+1;
		Point addPoint = new Point(0);
		pointRepository.saveAndFlush(addPoint);
		//新規会員をデータベース追加する
		Client addUser = new Client(bean.getName(), bean.getKana(), bean.getEmail(), tel, address, bean.getPassword(),
				bean.getSex(), pointCode, bean.getQuestionCode(), bean.getAnswer(), 2);
		clientRepository.saveAndFlush(addUser);
		mv.addObject("message", "新規アカウントを追加しました");
		LoginForm loginBean = new LoginForm();
		mv.addObject("bean", loginBean);
		mv.setViewName("userLoginFrame");
		return mv;
	}
	
	// パスワードフォーマットチェック
	public static boolean passcheck(String pass) {
		boolean result = true;
		//正規表現で数値と半角英字のみ許可。文字数は8～20文字
		String check = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z\\-]{8,20}$";
		Pattern pattern = Pattern.compile(check);
		Matcher m1 = pattern.matcher(pass); // パターンと検査対象文字列の照合
		result = m1.matches(); // 照合結果をtrueかfalseで取得
		return result;
	}

	// カナチェック
	public static boolean kanacheck(String kana) {
		boolean result = false;
		Pattern pattern = Pattern.compile("^[\u30a0-\u30ff]+$");
		result = pattern.matcher(kana).matches();
		return result;
	}

	// 数値チェック
	public boolean telcheck(String suu) {
		boolean isNumeric = suu.matches("[+-]?\\d*(\\.\\d+)?");
		return isNumeric;
	}
}
