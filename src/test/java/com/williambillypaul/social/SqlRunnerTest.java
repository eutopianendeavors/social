package com.williambillypaul.social;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class SqlRunnerTest extends TestCase {

	static Logger logger = Logger.getLogger(SqlRunnerTest.class);

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/social";

	// Database credentials
	static final String USER = "social";
	static final String PASS = "social";

	public void testRunScript() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer currentVersion = getCurrentVersion(connection);
		List<SqlScript> upgradeScripts = getSortedUpgradeScripts();
		try {

			for (SqlScript sqlScript : upgradeScripts) {
				if (sqlScript.getRunOrder() > currentVersion) {
					PrintWriter writer = new PrintWriter(System.out);
					SqlRunner runner = new SqlRunner(connection, writer,
							writer, false, true);
					Reader reader = null;
					reader = new FileReader(sqlScript.getPath());
					runner.runScript(reader);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Integer getCurrentVersion(Connection connection) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "SELECT MAX(version) AS version FROM data_base_version";
		ResultSet resultSet = null;
		Integer version = null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Database in initial state.");
			return new Integer(0);
		}
		try {
			if (resultSet.next()) {
				version = new Integer(resultSet.getInt("version"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	private List<SqlScript> getSortedUpgradeScripts() {
		List<SqlScript> upgradeScripts = new ArrayList<SqlScript>();
		File dir = new File(this.getClass().getResource("/sql/").getPath());
		for (File file : dir.listFiles()) {
			Integer runOrder = new Integer(file.getName().split("\\.")[0]);
			String path = file.getPath();
			SqlScript sqlScript = new SqlScript(runOrder, path);
			upgradeScripts.add(sqlScript);
		}
		Collections.sort(upgradeScripts, new Comparator<SqlScript>() {
			@Override
			public int compare(SqlScript sqlScript1, SqlScript sqlScript2) {
				return sqlScript1.getRunOrder().compareTo(
						sqlScript2.getRunOrder());
			}
		});
		return upgradeScripts;
	}

	private class SqlScript {

		private Integer runOrder;
		private String path;

		public SqlScript() {
			super();
		}

		public SqlScript(Integer runOrder, String path) {
			super();
			this.runOrder = runOrder;
			this.path = path;
		}

		public Integer getRunOrder() {
			return runOrder;
		}

		public void setRunOrder(Integer runOrder) {
			this.runOrder = runOrder;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

	}
}
