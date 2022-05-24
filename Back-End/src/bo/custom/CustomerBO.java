package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:54 PM
 * @project JavaEE POS Backend
 */

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
}
