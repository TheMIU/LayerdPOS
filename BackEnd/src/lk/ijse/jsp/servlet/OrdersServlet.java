package lk.ijse.jsp.servlet;

import lk.ijse.jsp.servlet.util.ResponseUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/orders"})
public class OrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection();) {

            PreparedStatement pstm = connection.prepareStatement("SELECT\n" +
                    "    o.orderID,\n" +
                    "    o.date,\n" +
                    "    o.customerID,\n" +
                    "    GROUP_CONCAT(oi.itemID ORDER BY oi.itemID) AS itemsIDs,\n" +
                    "    o.discount,\n" +
                    "    o.total\n" +
                    "FROM orders o\n" +
                    "         JOIN order_items oi ON o.orderID = oi.orderID\n" +
                    "GROUP BY o.orderID;");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allOrders = Json.createArrayBuilder();
            while (rst.next()) {
                String orderID = rst.getString(1);
                String date = rst.getString(2);
                String customerID = rst.getString(3);
                String itemsIDs = rst.getString(4);
                String discount = rst.getString(5);
                String total = rst.getString(6);

                JsonObjectBuilder orderObject = Json.createObjectBuilder();
                orderObject.add("orderID", orderID);
                orderObject.add("date", date);
                orderObject.add("customerID", customerID);
                orderObject.add("itemsIDs", itemsIDs);
                orderObject.add("discount", discount);
                orderObject.add("total", total);
                allOrders.add(orderObject.build());
            }
            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allOrders.build()));

        } catch (SQLException e) {
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
            resp.setStatus(400);
        }
    }
}
