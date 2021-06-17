package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Prefectures;
import com.example.demo.Inn;

public class RecInnDAO {
	private Connection con;

	public RecInnDAO() throws DAOException {
		getConnection();
	}

	public List<Inn> RecInn()
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "SELECT * FROM inn i JOIN review r ON i.inn_code = r.inn_code WHERE i.inn_invalid = '0' AND (select AVG(review_star) from review r) >= 4;";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			List<Inn> recInnList = new ArrayList<Inn>();
			while (rs.next()) {
				int innCode = rs.getInt("inn_code");
				String innName = rs.getString("inn_name");
				String prefecturesCode = rs.getString("prefectures_code");
				String innAddress = rs.getString("inn_address");
				String innAccess = rs.getString("inn_access");
				String innCheckinTime = rs.getString("inn_checkin_time");
				String innCheckoutTime = rs.getString("inn_checkout_time");
				String innAmenity = rs.getString("inn_amenity");
				String innInvalid = rs.getString("inn_invalid");
				Inn innBean = new Inn(innCode, innName, prefecturesCode, innAddress, innAccess, innCheckinTime, innCheckoutTime, innAmenity, innInvalid);
				recInnList.add(innBean);
			}
			return recInnList;
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