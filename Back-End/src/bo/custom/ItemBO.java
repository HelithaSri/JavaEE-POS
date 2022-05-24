package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;

import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:55 PM
 * @project JavaEE POS Backend
 */

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO itemDTO) throws SQLException;
    boolean deleteItem(String id) throws SQLException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException;
}
