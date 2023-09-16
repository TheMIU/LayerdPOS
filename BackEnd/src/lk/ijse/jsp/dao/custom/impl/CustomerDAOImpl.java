package lk.ijse.jsp.dao.custom.impl;

import lk.ijse.jsp.dao.custom.CustomerDAO;
import lk.ijse.jsp.entity.Customer;
import lk.ijse.jsp.listener.MyListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            ArrayList<Customer> customers = new ArrayList<>();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);

                Customer customer = new Customer(id, name, address);
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    @Override
    public boolean save(Customer dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?)");
            pstm.setObject(1, dto.getId());
            pstm.setObject(2, dto.getName());
            pstm.setObject(3, dto.getAddress());

            if (pstm.executeUpdate() > 0) {
                System.out.println("Saved");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public boolean update(Customer dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("update customer set cusName=?,cusAddress=? where cusID=?");

            pstm.setObject(3, dto.getId());
            pstm.setObject(1, dto.getName());
            pstm.setObject(2, dto.getAddress());

            if (pstm.executeUpdate() > 0) {
                System.out.println("Updated");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public boolean delete(String cusID) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from customer where cusID=?");
            pstm.setObject(1, cusID);

            if (pstm.executeUpdate() > 0) {
                System.out.println("Deleted");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Customer> getAllCustomersByAddress(String address) throws ClassNotFoundException, SQLException {
        return null;
    }
}
