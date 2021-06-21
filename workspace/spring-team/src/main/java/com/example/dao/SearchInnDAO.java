package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.example.demo.ResCheck;
import com.example.demo.SearchBean;

public class SearchInnDAO {
	private Connection con;

	public SearchInnDAO() throws DAOException {
		getConnection();
	}
	
	public List<SearchBean> srcInn(String prefecturesCode, int roomLowPrice, int roomHighPrice,  int roomMax, int roomTotal)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "SELECT i.inn_code, i.inn_name, r.room_code, r.room_name, i.prefectures_code, r.room_price, r.room_max, r.room_total FROM room r JOIN inn i ON r.inn_code = i.inn_code WHERE ? <= r.room_price AND r.room_price <= ? AND i.prefectures_code = ? AND ? <= r.room_max AND ? <= r.room_total AND i.inn_invalid = '0';";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setInt(1, roomLowPrice);
			st.setInt(2, roomHighPrice);
			st.setString(3, prefecturesCode);
			st.setInt(4, roomMax);
			st.setInt(5, roomTotal);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			List<SearchBean> srcInnList = new ArrayList<SearchBean>();
			while (rs.next()) {
				int innCode = rs.getInt("inn_code");
				String innName = rs.getString("inn_name");
				int roomCode = rs.getInt("room_code");
				String roomName = rs.getString("room_name");
				SearchBean srcBean = new SearchBean(innCode, innName, roomCode,roomName);
				srcInnList.add(srcBean);
			}
			return srcInnList;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
		finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	public ResCheck resInnRoom(Date checkinDate, Date checkoutDate, int roomCode)
			throws DAOException, ParseException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		ResCheck resBean = new ResCheck();
		try {
			// SQL文の作成
			String sql = "SELECT room_code, SUM(reservation_room_total) FROM reservation WHERE ? <= reservation_checkin_date AND ? >= reservation_checkout_date AND room_code = ? AND reservation_invalid = '0' GROUP BY room_code;";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setDate(1, checkinDate);
			st.setDate(2, checkoutDate);
			st.setInt(3, roomCode);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			while(rs.next()) {
			int resSum = rs.getInt("sum");
			resBean.setResSum(resSum);
			}
			return resBean;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
		finally {
			try {
				// リソースの開放
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	private void getConnection() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			// URL、ユーザ名、パスワードの設定
			String url = "jdbc:postgresql:group";
			String user = "student";
			String pass = "himitu";
			// データベースへの接続
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("接続に失敗しました。");
		}
	}

	private void close() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}
	
}