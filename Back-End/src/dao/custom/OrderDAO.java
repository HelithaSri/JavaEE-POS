package dao.custom;

import dao.CrudDAO;
import entity.Orders;

import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:05 PM
 * @project JavaEE POS Backend
 */

public interface OrderDAO extends CrudDAO<Orders, String> {
    boolean saveOd(Orders orders) throws SQLException;
}
