package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	/*
	 * public List<SearchBean> SearchInn(String checkinDate, String checkoutDate,
	 * String roomLowPrice, String roomHighPrice, int roomMax) throws DAOException {
	 * if (con == null) getConnection();
	 * 
	 * PreparedStatement st1 = null; PreparedStatement st2 = null; ResultSet rs1 =
	 * null; ResultSet rs2 = null; try { // SQL文の作成 String sql1 =
	 * "SELECT i.inn_code, i.inn_name, i.prefectures_code, re.room_code, r.room_name, r.room_price, r.room_total, r.room_max, SUM(reservation_room_total) FROM reservation re JOIN room r ON re.room_code = r.room_code JOIN inn i ON r.inn_code = i.inn_code WHERE ? <= re.reservation_checkin_date AND ? >= re.reservation_checkout_date AND i.prefecture_code = ? AND ? <= r.room_price AND ? >= r.room_price AND ? >= r.room_max AND re.reservation_invalid = '0' GROUP BY i.inn_code, r.room_code, re.room_code;"
	 * ; String sql2 = "SELECT room_code, room_total FROM room;"; //
	 * PreparedStatementオブジェクトの取得 st1 = con.prepareStatement(sql1); st2 =
	 * con.prepareStatement(sql2); // カテゴリの設定 st1.setString(1, checkinDate);
	 * st1.setString(2, checkoutDate); st1.setString(3, roomLowPrice);
	 * st1.setString(4, roomHighPrice); st1.setInt(5, roomMax); // SQLの実行 rs1 =
	 * st1.executeQuery(); rs2 = st2.executeQuery(); // 結果の取得および表示 List<SearchBean>
	 * srcInnList = new ArrayList<SearchBean>(); while (rs1.next()) { int innCode =
	 * rs1.getInt("inn_code"); String innName = rs1.getString("inn_name"); String
	 * prefecturesCode = rs1.getString("prefectures_code"); int roomCode =
	 * rs1.getInt("room_code"); String roomName = rs1.getString("room_name"); String
	 * roomPrice = rs1.getString("room_price"); String roomTotal =
	 * rs1.getString("rooom_total"); int roommax = rs1.getInt("room_max"); int
	 * resSum = rs1.getInt("sum"); SearchBean srcBean = new SearchBean(innCode,
	 * innName, prefecturesCode, roomCode, roomName, roomPrice, roomTotal, roommax,
	 * resSum); srcInnList.add(srcBean); } return srcInnList; } catch (Exception e)
	 * { e.printStackTrace(); throw new DAOException("レコードの取得に失敗しました。"); } finally {
	 * try { // リソースの開放 if (rs1 != null) rs1.close(); if (st1 != null) st1.close();
	 * if (rs2 != null) rs2.close(); if (st2 != null) st2.close(); close(); } catch
	 * (Exception e) { throw new DAOException("リソースの開放に失敗しました。"); } } }
	 */

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