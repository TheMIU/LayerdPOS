package lk.ijse.jsp.servlet;

import lk.ijse.jsp.bo.BOFactory;
import lk.ijse.jsp.bo.custom.CustomerBO;
import lk.ijse.jsp.dto.CustomerDTO;
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

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServletAPI extends HttpServlet {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<CustomerDTO> customerArray = customerBO.getAllCustomers();

        JsonArrayBuilder allCustomers = Json.createArrayBuilder();

        for (CustomerDTO c : customerArray) {
            JsonObjectBuilder customerObject = Json.createObjectBuilder();
            customerObject.add("id", c.getId());
            customerObject.add("name", c.getName());
            customerObject.add("address", c.getAddress());
            allCustomers.add(customerObject.build());
        }

        resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");

        boolean isSaved = customerBO.saveCustomer(new CustomerDTO(cusID, cusName, cusAddress));

        if (isSaved) {
            resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Successfully Added."));
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

        String cusID = jsonObject.getString("cusID");
        String cusName = jsonObject.getString("cusName");
        String cusAddress = jsonObject.getString("cusAddress");

        boolean isUpdated = customerBO.updateCustomer(new CustomerDTO(cusID, cusName, cusAddress));

        if (isUpdated) {
            resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Customer Updated..!"));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Failed", cusID + " Customer is not exist..!"));
            resp.setStatus(400);
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cusID = req.getParameter("cusID");

        boolean isDeleted = customerBO.deleteCustomer(cusID);

        if (isDeleted) {
            resp.getWriter().print(ResponseUtil.genJson("Success", cusID + " Customer Deleted..!"));
            resp.setStatus(200);
        } else {
            resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer with ID " + cusID + " not found."));
            resp.setStatus(400);
        }
    }
}



