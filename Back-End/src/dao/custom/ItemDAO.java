package dao.custom;

import dao.CrudDAO;
import entity.Item;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:05 PM
 * @project JavaEE POS Backend
 */

public interface ItemDAO extends CrudDAO<Item, String> {
    JsonArrayBuilder loadItemId() throws SQLException;

    JsonArrayBuilder loadSelectItemDetails(String id) throws SQLException;
}
