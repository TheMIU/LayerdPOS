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
    public boolean save(Item dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
            pstm.setObject(1, dto.getCode());
            pstm.setObject(2, dto.getItemName());
            pstm.setObject(3, dto.getQty());
            pstm.setObject(4, dto.getUnitPrice());

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
    public boolean update(Item dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("update Item set itemName=?,qty=?,unitPrice=? where code=?");
            pstm.setObject(1, dto.getItemName());
            pstm.setObject(2, dto.getQty());
            pstm.setObject(3, dto.getUnitPrice());
            pstm.setObject(4, dto.getCode());

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

    public boolean update2(Item dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET qty = qty - ? WHERE code = ?");
            pstm.setObject(1, dto.getQty());
            pstm.setObject(2, dto.getCode());

            if (pstm.executeUpdate() > 0) {
                System.out.println("item Updated");
                return true;
            } else {
                System.out.println("item update failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }
    @Override
    public boolean delete(String code) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from Item where code=?");
            pstm.setObject(1, code);

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
    public Item search(String s) throws SQLException, ClassNotFoundException {
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
    public ArrayList<Item> getItemFromPrice(double price) throws ClassNotFoundException, SQLException {
        return null;
    }
}
