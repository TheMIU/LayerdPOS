package lk.ijse.jsp.bo.custom.impl;

import lk.ijse.jsp.bo.custom.CustomerBO;
import lk.ijse.jsp.dao.DAOFactory;
import lk.ijse.jsp.dao.custom.CustomerDAO;
import lk.ijse.jsp.dto.CustomerDTO;
import lk.ijse.jsp.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer c : all) {
            CustomerDTO customerDTO = new CustomerDTO(c.getId(), c.getName(), c.getAddress());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress());
        return customerDAO.save(customer);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(dto.getId(), dto.getName(), dto.getAddress());
        return customerDAO.update(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
