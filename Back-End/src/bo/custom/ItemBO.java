package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:55 PM
 * @project JavaEE POS Backend
 */

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO itemDTO);
    boolean deleteItem(String id);
    boolean updateItem(ItemDTO itemDTO);
}
