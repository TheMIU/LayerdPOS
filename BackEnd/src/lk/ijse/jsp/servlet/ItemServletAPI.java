package lk.ijse.jsp.servlet;

import lk.ijse.jsp.bo.BOFactory;
import lk.ijse.jsp.bo.custom.ItemBO;
import lk.ijse.jsp.dto.ItemDTO;
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

@WebServlet(urlPatterns = "/pages/item")
public class ItemServletAPI extends HttpServlet {
    ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<ItemDTO> allItemDTOs = itemBO.getAllItems();

        JsonArrayBuilder allItems = Json.createArrayBuilder();

        for (ItemDTO i : allItemDTOs) {
            JsonObjectBuilder itemObject = Json.createObjectBuilder();
            itemObject.add("code", i.getCode());
            itemObject.add("itemName", i.getDescription());
            itemObject.add("qty", i.getQtyOnHand());
            itemObject.add("unitPrice", i.getUnitPrice());
            allItems.add(itemObject.build());
        }

        resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allItems.build()));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        String itemName = req.getParameter("description");
        String qty = req.getParameter("qty");
        String unitPrice = req.getParameter("unitPrice");

        boolean isSaved = itemBO.saveItem(new ItemDTO(code, itemName, Integer.parseInt(qty), Double.parseDouble(unitPrice)));

        if (isSaved) {
            resp.getWriter().print(ResponseUtil.genJson("Success", code + " Successfully Added."));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Error", "Wrong data !"));
            resp.setStatus(400);
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String code = jsonObject.getString("code");
        String itemName = jsonObject.getString("itemName");
        String qty = jsonObject.getString("qty");
        String unitPrice = jsonObject.getString("unitPrice");

        boolean isUpdated = itemBO.updateItem(new ItemDTO(code, itemName, Integer.parseInt(qty), Double.parseDouble(unitPrice)));

        if (isUpdated) {
            resp.getWriter().print(ResponseUtil.genJson("Success", code + " Item Updated..!"));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Failed", code + " Item is not exist..!"));
            resp.setStatus(400);
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");

        boolean isDeleted = itemBO.deleteItem(code);

        if (isDeleted) {
            resp.getWriter().print(ResponseUtil.genJson("Success", code + " Item Deleted..!"));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Failed", "Item with code " + code + " not found."));
            resp.setStatus(400);
        }
    }
}
