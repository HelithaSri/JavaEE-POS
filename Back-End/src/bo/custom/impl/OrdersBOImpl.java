package bo.custom.impl;

import bo.custom.OrdersBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDAOImpl;
import dto.OrdersDTO;

import javax.json.JsonObjectBuilder;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:59 PM
 * @project JavaEE POS Backend
 */

public class OrdersBOImpl implements OrdersBO {
    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public boolean addOrder(OrdersDTO ordersDTO) {
        return false;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        return orderDAO.generateID();
    }


}
