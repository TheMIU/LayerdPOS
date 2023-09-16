package lk.ijse.jsp.bo.custom.impl;

import lk.ijse.jsp.bo.custom.ItemBO;
import lk.ijse.jsp.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean itemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        return null;
    }
}
