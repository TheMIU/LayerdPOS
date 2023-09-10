package lk.ijse.jsp.servlet;

import lk.ijse.jsp.servlet.util.ResponseUtil;

import javax.json.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServletAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                allCustomers.add(customerObject.build());
            }

            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));

        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(500);

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?)");

            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", cusID+" Successfully Added."));
                resp.setStatus(200);
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Wrong data !"));
                resp.setStatus(400);
            }

        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(500);

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String cusID = jsonObject.getString("cusID");
        String cusName = jsonObject.getString("cusName");
        String cusAddress = jsonObject.getString("cusAddress");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");
            PreparedStatement pstm3 = connection.prepareStatement("update customer set cusName=?,cusAddress=? where cusID=?");

            pstm3.setObject(3, cusID);
            pstm3.setObject(1, cusName);
            pstm3.setObject(2, cusAddress);

            if (pstm3.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Customer Updated..!"));
                resp.setStatus(200);
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", cusID + " Customer is not exist..!"));
                resp.setStatus(400);
            }

        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(500);

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cusID = req.getParameter("cusID");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("delete from customer where cusID=?");
            pstm.setObject(1, cusID);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Customer Deleted..!"));
                resp.setStatus(200);
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer with ID " + cusID + " not found."));
                resp.setStatus(400);
            }

        } catch (ClassNotFoundException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(500);

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(400);
        }
    }
}



