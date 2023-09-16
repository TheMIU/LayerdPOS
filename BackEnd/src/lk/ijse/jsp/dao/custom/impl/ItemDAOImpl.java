package lk.ijse.jsp.dao.custom.impl;

import lk.ijse.jsp.dao.custom.ItemDAO;
import lk.ijse.jsp.entity.Item;
import lk.ijse.jsp.listener.MyListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            ArrayList<Item> itemArrayList = new ArrayList<>();
            while (rst.next()) {
                String code = rst.getString(1);
                String itemName = rst.getString(2);
                int qty = rst.getInt(3);
                double unitPrice = rst.getDouble(4);

                Item item = new Item(code, itemName, qty, unitPrice);
                itemArrayList.add(item);
            }

            return itemArrayList;

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Item> getItemFromPrice(double price) throws ClassNotFoundException, SQLException {
        return null;
    }
}
