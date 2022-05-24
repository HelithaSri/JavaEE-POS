package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:59 PM
 * @project JavaEE POS Backend
 */

public class CustomerBOImpl implements CustomerBO {
    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary());
        return customerDAO.add(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary()));
    }
}
