package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import javax.json.JsonArrayBuilder;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:05 PM
 * @project JavaEE POS Backend
 */

public interface CustomerDAO extends CrudDAO<Customer,String> {
    JsonArrayBuilder loadCusId() throws SQLException;
    JsonArrayBuilder loadSelectCusDetails(String id) throws SQLException;
}
