package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:54 PM
 * @project JavaEE POS Backend
 */

public interface CustomerBO extends SuperBO {
    JsonArrayBuilder getAllCustomer() throws SQLException;
    JsonObjectBuilder generateCustomerID() throws SQLException;
    JsonArrayBuilder searchCustomer(String id) throws SQLException;
    boolean addCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
}
