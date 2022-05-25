package bo.custom;

import bo.SuperBO;
import dto.OrdersDTO;

import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:55 PM
 * @project JavaEE POS Backend
 */

public interface OrdersBO extends SuperBO {
    boolean addOrder(OrdersDTO ordersDTO);

    JsonObjectBuilder generateID() throws SQLException;
}
