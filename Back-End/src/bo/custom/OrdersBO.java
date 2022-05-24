package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.OrdersDTO;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:55 PM
 * @project JavaEE POS Backend
 */

public interface OrdersBO extends SuperBO {
    boolean addOrder(OrdersDTO ordersDTO);
}
