package lk.ijse.jsp.dao.custom.impl;

import lk.ijse.jsp.dao.custom.QueryDAO;
import lk.ijse.jsp.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
