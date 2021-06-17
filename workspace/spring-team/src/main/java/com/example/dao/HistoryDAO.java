package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.ViewBean;

public class HistoryDAO {
	private Connection con;

	public HistoryDAO() throws DAOException {
		getConnection();
	}

	public List<ViewBean> history(int client_codes)
			throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "select * from view_history join inn on view_history.inn_code = inn.inn_code where view_history.client_code = ?";
			st = con.prepareStatement(sql);
			// カテゴリの設定
			st.setInt(1,client_codes);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			List<ViewBean> list = new ArrayList<ViewBean>();
			while (rs.next()) {
				int view_history_code = rs.getInt("view_history_code");
				String client_code = rs.getString("inn_code");
				String inn_name = rs.getString("inn_name");
				Timestamp view_history_datetime = rs.getTimestamp("view_history_datetime");
				ViewBean viewList = new ViewBean(view_history_code,client_code, inn_name, view_history_datetime);
				list.add(viewList);
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