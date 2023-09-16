package lk.ijse.jsp.dao.custom;

import lk.ijse.jsp.dao.SuperDAO;
import lk.ijse.jsp.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> searchOrderByOrderID(String id)throws SQLException,ClassNotFoundException;
}
