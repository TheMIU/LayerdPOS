package lk.ijse.jsp.servlet;

import lk.ijse.jsp.servlet.util.ResponseUtil;

import javax.json.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/pages/item")
public class ItemServletAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allItems = Json.createArrayBuilder();
            while (rst.next()) {
                String code = rst.getString(1);
                String itemName = rst.getString(2);
                int qty = rst.getInt(3);
                double unitPrice = rst.getDouble(4);

                JsonObjectBuilder itemObject = Json.createObjectBuilder();
                itemObject.add("code", code);
                itemObject.add("itemName", itemName);
                itemObject.add("qty", qty);
                itemObject.add("unitPrice", unitPrice);
                allItems.add(itemObject.build());
            }

            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allItems.build()));


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
        String code = req.getParameter("code");
        String itemName = req.getParameter("description");
        String qty = req.getParameter("qty");
        String unitPrice = req.getParameter("unitPrice");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
            pstm.setObject(1, code);
            pstm.setObject(2, itemName);
            pstm.setObject(3, qty);
            pstm.setObject(4, unitPrice);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", code + " Successfully Added."));
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

        String code = jsonObject.getString("code");
        String itemName = jsonObject.getString("itemName");
        String qty = jsonObject.getString("qty");
        String unitPrice = jsonObject.getString("unitPrice");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");

            PreparedStatement pstm3 = connection.prepareStatement("update Item set itemName=?,qty=?,unitPrice=? where code=?");
            pstm3.setObject(1, itemName);
            pstm3.setObject(2, qty);
            pstm3.setObject(3, unitPrice);
            pstm3.setObject(4, code);

            if (pstm3.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", code + " Item Updated..!"));
                resp.setStatus(200);
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", code + " Item is not exist..!"));
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
        String code = req.getParameter("code");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("delete from Item where code=?");
            pstm.setObject(1, code);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", code + " Item Deleted..!"));
                resp.setStatus(200);
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", "Item with code " + code + " not found."));
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
