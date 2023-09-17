package lk.ijse.jsp.servlet;

import lk.ijse.jsp.bo.BOFactory;
import lk.ijse.jsp.bo.custom.PurchaseOrderBO;
import lk.ijse.jsp.dto.CartDTO;
import lk.ijse.jsp.dto.ItemDTO;
import lk.ijse.jsp.dto.OrdersDTO;
import lk.ijse.jsp.dto.PlaceOrderDTO;
import lk.ijse.jsp.servlet.util.ResponseUtil;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/pages/purchase-order"})
public class PurchaseOrderServletAPI extends HttpServlet {
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String orderID = jsonObject.getString("orderID");
        String date = jsonObject.getString("date");
        JsonObject customer = jsonObject.getJsonObject("customer");
        JsonArray cart = jsonObject.getJsonArray("cart");
        String total = jsonObject.getString("total");
        String discount = jsonObject.getString("discount");
        if (discount.equals("NaN")) {
            discount = "0";
        }

        String customerID = customer.getString("id");

        PlaceOrderDTO dto = new PlaceOrderDTO();
        dto.setOrderID(orderID);
        dto.setDate(date);

        ArrayList<CartDTO> cartArray = new ArrayList<>();
        for (JsonValue jsonValue : cart) {
            JsonObject cartObject = (JsonObject) jsonValue;

            // Extract item and qty values from the JSON object
            JsonObject itemObject = cartObject.getJsonObject("item");
            int qty = cartObject.getInt("qty");

            ItemDTO itemDTO = new ItemDTO();
            System.out.println(itemObject);
            itemDTO.setCode(itemObject.getString("code"));
            itemDTO.setQtyOnHand(itemObject.getInt("qty"));

            CartDTO cartDTO = new CartDTO();
            cartDTO.setItem(itemDTO);
            cartDTO.setQty(qty);

            cartArray.add(cartDTO);
        }

        dto.setCartArray(cartArray);
        dto.setTotal(Double.parseDouble(total));
        dto.setDiscount(Integer.parseInt(discount));
        dto.setCusID(customerID);

        System.out.println(dto);
        boolean isPurchased = purchaseOrderBO.purchaseOrder(dto);

        if (isPurchased){
            resp.getWriter().print(ResponseUtil.genJson("Transaction Success", " Order Successfully Added..!"));
            resp.setStatus(200);
        }else {
            resp.getWriter().print(ResponseUtil.genJson("Transaction Error", "Data insertion failed"));
            resp.setStatus(500);
        }

       /* try {
            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();

            String orderID = jsonObject.getString("orderID");
            String date = jsonObject.getString("date");
            JsonObject customer = jsonObject.getJsonObject("customer");
            JsonArray cart = jsonObject.getJsonArray("cart");
            String total = jsonObject.getString("total");
            String discount = jsonObject.getString("discount");
            if (discount.equals("NaN")) {
                discount = "0";
            }

            String customerID = customer.getString("id");

            try (Connection connection = ((BasicDataSource) getServletContext().getAttribute("dbcp")).getConnection();) {

                connection.setAutoCommit(false);

                boolean success = true;

                // Insert order
                PreparedStatement pstm1 = connection.prepareStatement("INSERT INTO orders (orderID, date, customerID, discount, total) VALUES (?, ?, ?, ?, ?)");
                pstm1.setString(1, orderID);
                pstm1.setString(2, date);
                pstm1.setString(3, customerID);
                pstm1.setString(4, discount);
                pstm1.setString(5, total);

                if (pstm1.executeUpdate() <= 0) {
                    success = false;
                }

                // Insert items from the cart
                PreparedStatement pstm2 = connection.prepareStatement("INSERT INTO order_items (orderID, itemID, qty) VALUES (?, ?, ?)");
                PreparedStatement pstm3 = connection.prepareStatement("UPDATE item SET qty = qty - ? WHERE code = ?");

                for (JsonValue cartItemValue : cart) {
                    JsonObject cartItem = (JsonObject) cartItemValue;
                    JsonObject item = cartItem.getJsonObject("item");
                    String cartItemCode = item.getString("code");
                    int cartQty = cartItem.getInt("qty");
                    //System.out.println(cart);
                    //System.out.println(cartQty);

                    pstm2.setString(1, orderID);
                    pstm2.setString(2, cartItemCode);
                    pstm2.setInt(3, cartQty);

                    if (pstm2.executeUpdate() <= 0) {
                        success = false;
                    }

                    pstm3.setInt(1, cartQty);
                    pstm3.setString(2, cartItemCode);

                    if (pstm3.executeUpdate() <= 0) {
                        success = false;
                    }
                }

                if (success) {
                    connection.commit();
                    resp.getWriter().print(ResponseUtil.genJson("Success", orderID + " Order Successfully Added..!"));
                    resp.setStatus(200);
                } else {
                    connection.rollback();
                    resp.getWriter().print(ResponseUtil.genJson("Error", "Data insertion failed"));
                    resp.setStatus(500);
                }

                connection.setAutoCommit(true);
        }*/
    }
}
