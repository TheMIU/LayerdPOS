package lk.ijse.jsp.servlet;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionAPI extends HttpServlet {
    public static Connection connection;

    public Connection getConnection(){
        try {
            connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
