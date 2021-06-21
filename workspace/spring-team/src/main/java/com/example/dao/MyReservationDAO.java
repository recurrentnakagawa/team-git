package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.Prefectures;
import com.example.demo.Inn;
import com.example.demo.MyReservationBean;
import com.example.demo.NowReservationBean;

public class MyReservationDAO {
	private Connection con;

	public MyReservationDAO() throws DAOException {
		getConnection();
	}

	public List<MyReservationBean> findByFutureReservation(Integer clientCode, String reservationInvalid, Date date)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "select * "
						+ "from reservation join room on reservation.room_code = room.room_code "
						+ "join inn on room.inn_code = inn.inn_code "
						+ "where reservation.client_code = ? "
						+ "and reservation.reservation_invalid = ? "
						+ "and reservation.reservation_checkin_date > ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setInt(1,clientCode);
			st.setString(2,reservationInvalid);
			st.setDate(3,(java.sql.Date) date);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			List<MyReservationBean> list = new ArrayList<MyReservationBean>();
			while (rs.next()) {
				int reservationCode = rs.getInt("reservation_code");
				String innName = rs.getString("inn_name");
				String roomName = rs.getString("room_name");
				MyReservationBean bean = new MyReservationBean(reservationCode, innName, roomName);
				list.add(bean);
			}
			return list;
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
	
	public List<MyReservationBean> findByPastReservation(Integer clientCode, String reservationInvalid, Date date)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "select * from reservation join room on reservation.room_code = room.room_code "
						+ "join inn on room.inn_code = inn.inn_code "
						+ "where reservation.client_code = ?"
						+ "and reservation.reservation_invalid = ?"
						+ "and reservation.reservation_checkin_date <= ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setInt(1,clientCode);
			st.setString(2,reservationInvalid);
			st.setDate(3, (java.sql.Date) date);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			List<MyReservationBean> list = new ArrayList<MyReservationBean>();
			while (rs.next()) {
				int reservationCode = rs.getInt("reservation_code");
				String innName = rs.getString("inn_name");
				String roomName = rs.getString("room_name");
				int innCode = rs.getInt("inn_code");
				String reviewFlag = rs.getString("review_flag");
				MyReservationBean bean = new MyReservationBean(reservationCode, innName, roomName,innCode,reviewFlag);
				list.add(bean);
			}
			return list;
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
	
	public NowReservationBean findNowReservation(Integer reservationCode)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "select * from reservation "
					   + "join room on reservation.room_code = room.room_code "
					   + "join inn on room.inn_code = inn.inn_code "
					   + "join client on reservation.client_code = client.client_code "
					   + "where reservation.reservation_code = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setInt(1,reservationCode);
			// SQLの実行
			rs = st.executeQuery();
			rs.next();
			// 結果の取得および表示
			String innName = rs.getString("inn_name");
			String roomName = rs.getString("room_name");
			Date reservationChecinDate = rs.getDate("reservation_checkin_date");
			Date reservationChecoutDate =rs.getDate("reservation_checkout_date");
			int reservationUserTotal = rs.getInt("reservation_user_total");
			int reservationRoomTotal = rs.getInt("reservation_room_total");
			int reservationPrice = rs.getInt("reservation_price");
			String clientName = rs.getString("client_name");
			String clientTel = rs.getString("client_tel");
			NowReservationBean bean = new NowReservationBean(innName,roomName,reservationChecinDate,reservationChecoutDate,reservationUserTotal,reservationRoomTotal,reservationPrice,clientName,clientTel);
			return bean;
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