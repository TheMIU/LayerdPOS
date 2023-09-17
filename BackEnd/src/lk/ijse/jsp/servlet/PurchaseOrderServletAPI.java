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

        boolean isPurchased = purchaseOrderBO.purchaseOrder(dto);

        if (isPurchased){
            resp.getWriter().print(ResponseUtil.genJson("Transaction Success", " Order Successfully Added..!"));
            resp.setStatus(200);
        }else {
            resp.getWriter().print(ResponseUtil.genJson("Transaction Error", "Data insertion failed"));
            resp.setStatus(500);
        }
    }
}
