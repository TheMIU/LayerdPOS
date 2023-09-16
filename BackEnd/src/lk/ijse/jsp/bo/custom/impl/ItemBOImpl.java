package lk.ijse.jsp.bo.custom.impl;

import lk.ijse.jsp.bo.custom.ItemBO;
import lk.ijse.jsp.dao.DAOFactory;
import lk.ijse.jsp.dao.custom.ItemDAO;
import lk.ijse.jsp.dto.ItemDTO;
import lk.ijse.jsp.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item i : all) {
            ItemDTO itemDTO = new ItemDTO(i.getCode(), i.getItemName(), i.getQty(), i.getUnitPrice());
            itemDTOS.add(itemDTO);
        }

        return itemDTOS;
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
