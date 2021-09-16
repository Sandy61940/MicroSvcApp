package com.webstore.core.engine.web.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.core.engine.web.datasource.DataBaseUtil;
import com.webstore.core.engine.web.props.EnginePropertyReader;
/**
 * Servlet implementation class WebstoreDbLock
 */
@WebServlet("/webstoreLock")
public class WebstoreDbLock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebstoreDbLock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("webstore table lock request arrived");
			try {
				EnginePropertyReader engineProps = new EnginePropertyReader();
				Properties enginePropsObj = engineProps.getEnginePropsObj();
				String driverClass = enginePropsObj.getProperty("DB_DRIVER_CLASS");
				String jdbcUrl = enginePropsObj.getProperty("DB_JDBC_URL");
				String userName = enginePropsObj.getProperty("DB_USER_NAME");
				String password = enginePropsObj.getProperty("DB_PASSWORD");
				String query = enginePropsObj.getProperty("DB_LOCK_QUERY");
				System.out.println("DB details: "+driverClass+" "+jdbcUrl+" "+userName+" "+password+" "+query);
				DataBaseUtil baseUtil = new DataBaseUtil();
				Connection connection = baseUtil.getNormalConnection(driverClass, jdbcUrl, userName, password);
				Statement stmt = baseUtil.getStatment(connection);
				stmt.execute(query);
				System.out.println("webstore database table ["+query+"] is  locked");
			} catch (SQLException e) {
				System.out.println("sql exction " + e);
			}
		
		
		response.getWriter().append("webstore database locked request received in WebstoreDbLock servlet").append(request.getContextPath());
	}

}
