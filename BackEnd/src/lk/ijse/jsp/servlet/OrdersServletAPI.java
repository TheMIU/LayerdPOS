package lk.ijse.jsp.servlet;

import lk.ijse.jsp.bo.BOFactory;
import lk.ijse.jsp.bo.custom.OrdersBO;
import lk.ijse.jsp.bo.custom.PurchaseOrderBO;
import lk.ijse.jsp.dto.OrdersDTO;
import lk.ijse.jsp.servlet.util.ResponseUtil;
import lombok.SneakyThrows;
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
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/pages/orders"})
public class OrdersServletAPI extends HttpServlet {
    OrdersBO ordersBO = (OrdersBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDERS);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<OrdersDTO> allOrderDTOs = ordersBO.getAllOrders();

        JsonArrayBuilder allOrders = Json.createArrayBuilder();

        for (OrdersDTO o : allOrderDTOs) {
            JsonObjectBuilder orderObject = Json.createObjectBuilder();

            orderObject.add("orderID", o.getOrderID());
            orderObject.add("date", o.getDate());
            orderObject.add("customerID", o.getCusID());
            orderObject.add("itemsIDs", o.getItemIDs());
            orderObject.add("discount", o.getDiscount());
            orderObject.add("total", o.getTotal());

            allOrders.add(orderObject.build());
        }

        resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allOrders.build()));
    }
}
