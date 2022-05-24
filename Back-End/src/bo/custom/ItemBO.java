package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:55 PM
 * @project JavaEE POS Backend
 */

public interface ItemBO extends SuperBO {
    JsonArrayBuilder getAllItems() throws SQLException;
    JsonObjectBuilder generateItemID() throws SQLException;
    JsonArrayBuilder searchItem(String id) throws SQLException;
    boolean addItem(ItemDTO itemDTO) throws SQLException;
    boolean deleteItem(String id) throws SQLException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException;
}
