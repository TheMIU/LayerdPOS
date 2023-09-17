package lk.ijse.jsp.listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class MyListener implements ServletContextListener {
    public static BasicDataSource pool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // create pool
        pool = new BasicDataSource();
        pool.setDriverClassName("com.mysql.cj.jdbc.Driver");
        pool.setUrl("jdbc:mysql://localhost:3306/testdb");
        pool.setUsername("root");
        pool.setPassword("1234");
        pool.setInitialSize(5);
        pool.setMaxTotal(5);

        // bind pool to servlet context
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("dbcp", pool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}