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
public class RegistUserEditController {

	@Autowired
	HttpSession session;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	PointRepository pointRepository;

	@RequestMapping(value = "/registUserEdit", method = RequestMethod.POST)
	public ModelAndView registUser(@ModelAttribute("addUserBean") addUserForm bean, ModelAndView mv) {
		mv.addObject("addUserBean", bean);
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
		if (bean.getName().length() == 0) {
			mv.addObject("message_name", "名前が入力されていません");
			flg = 1;
		}
		if (bean.getKana().length() == 0) {
			mv.addObject("message_kana", "名前（フリガナ）が入力されていません");
			flg = 1;
		}
		if (bean.getAddress().length() == 0) {
			mv.addObject("message_address", "住所が入力されていません");
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
		if (telFlg == 1) {
			mv.addObject("message_tel", "電話番号が入力されていません");
			flg = 1;
		}
		// 未入力が１つでもあるとアカウント編集画面へ遷移
		if (flg == 1) {
			mv.setViewName("mypageEdit");
			return mv;
		}
		// メールアドレスフォーマットチェック
		if (!bean.getEmail().contains("@")) {
			mv.addObject("message_email", "メールアドレスのフォーマットが正しくありません");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// メールアドレスとメールアドレス再入力が一致しているかチェックする
		if (!bean.getEmail().equals(bean.getRe_email())) {
			mv.addObject("message_email", "メールアドレスとメールアドレス再入力が一致しません");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// カタカナフォーマットチェック
		if (kanacheck(bean.getKana()) == false) {
			mv.addObject("message_kana", "全角カタカナで入力してください");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 電話番号1フォーマットチェック
		if (telcheck(bean.getTel1()) == false) {
			mv.addObject("message_tel", "数値で入力してください");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 電話番号2フォーマットチェック
		if (telcheck(bean.getTel2()) == false) {
			mv.addObject("message_tel", "数値で入力してください");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 電話番号3フォーマットチェック
		if (telcheck(bean.getTel3()) == false) {
			mv.addObject("message_tel", "数値で入力してください");
			mv.setViewName("mypageEdit");
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
		// メールアドレスの文字数チェック
		if (bean.getEmail().length() >= 50) {
			mv.addObject("message_email", "メールアドレスの文字数が多すぎます(50文字まで)");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 名前の文字数チェック
		if (bean.getName().length() >= 30) {
			mv.addObject("message_name", "名前の文字数が多すぎます(30文字まで)");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// カタカナの文字数チェック
		if (bean.getKana().length() >= 30) {
			mv.addObject("message_kana", "名前(カタカナ)の文字数が多すぎます(30文字まで)");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 住所の文字数チェック
		if (bean.getAddress().length() >= 50) {
			mv.addObject("message_address", "住所の文字数が多すぎます（50文字まで）");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 電話番号の文字数チェック
		if (tel.length() >= 15) {
			mv.addObject("message_tel", "電話番号の文字数が多すぎます（合計１３文字まで）");
			mv.setViewName("mypageEdit");
			return mv;
		}
		// 同じメールアドレスが登録されていないかチェック
		//ログインユーザー情報
		Client client = (Client)session.getAttribute("loginUser");
		//入力されたメールアドレスを一致するユーザー情報を取得
		Client user = clientRepository.findByClientEmail(bean.getEmail());
		//ログインを一致するユーザー情報を取得
		Client login = clientRepository.findByClientCode(client.getClientCode());
		//メールアドレスが登録されていないか
		if (user != null) {
			//自分のメールアドレスならOK
			if (!client.getClientEmail().equals(bean.getEmail())) {
				mv.addObject("message_email", "すでに存在しているメールアドレスです");
				mv.setViewName("mypageEdit");
				return mv;
			}
		}
		login.setClientEmail(bean.getEmail());
		login.setClientName(bean.getName());
		login.setClientKana(bean.getKana());
		login.setClientSex(bean.getSex());
		login.setClientAddress(bean.getAddress());
		login.setClientTel(tel);
		clientRepository.saveAndFlush(login);
		session.setAttribute("loginUser", login);
		session.setAttribute("tel1", bean.getTel1());
		session.setAttribute("tel2", bean.getTel2());
		session.setAttribute("tel3", bean.getTel3());
		mv.addObject("message", "アカウント情報を更新しました");
		mv.setViewName("mypageEdit");
		return mv;
	}

	// パスワードフォーマットチェック
	public static boolean passcheck(String pass) {
		boolean result = true;
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
