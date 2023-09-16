package lk.ijse.jsp.dao.custom;

import lk.ijse.jsp.dao.CrudDAO;
import lk.ijse.jsp.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    public ArrayList<Item> getItemFromPrice(double price)throws ClassNotFoundException, SQLException;
}
