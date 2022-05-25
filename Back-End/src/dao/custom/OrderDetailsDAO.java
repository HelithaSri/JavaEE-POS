package dao.custom;

import dao.CrudDAO;
import dto.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:05 PM
 * @project JavaEE POS Backend
 */

public interface OrderDetailsDAO extends CrudDAO<entity.OrderDetails, String> {
    boolean saveOrderDetails(String id, ArrayList<OrderDetails> dtos) throws SQLException;
}
