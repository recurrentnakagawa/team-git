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

public class InnDAO {
	private Connection con;

	public InnDAO() throws DAOException {
		getConnection();
	}

	public List<Inn> findByInn(String inn_name, String pref_id)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "select * from inn join prefectures on inn.prefectures_code = prefectures.prefectures_code where inn_name like ? and inn.prefectures_code = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setString(1, "%" + inn_name + "%");
			st.setString(2, pref_id);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			List<Inn> list = new ArrayList<Inn>();
			while (rs.next()) {
				int innCode = rs.getInt("inn_code");
				String innName = rs.getString("inn_name");
				String innAddress = rs.getString("inn_address");
				Inn innBean = new Inn(innCode, innName, innAddress);
				list.add(innBean);
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
	
	public String findPrefectureName(String prefCode)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "select prefectures_name from prefectures where prefectures_code = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setString(1,prefCode);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			String pref = null;
			while (rs.next()) {
				pref = rs.getString("prefectures_name");
			}
			return pref;
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