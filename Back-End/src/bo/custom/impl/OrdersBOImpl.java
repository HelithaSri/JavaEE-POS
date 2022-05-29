package bo.custom.impl;

import bo.custom.OrdersBO;
import dao.DAOFactory;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;
import dto.OrdersDTO;
import entity.Orders;
import servlet.OrderServlet;

import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:59 PM
 * @project JavaEE POS Backend
 */

public class OrdersBOImpl implements OrdersBO {
    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAOImpl orderDetailsDAO = (OrderDetailsDAOImpl) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean addOrder(OrdersDTO ordersDTO) {
        Connection connection = null;
        try {
            connection = OrderServlet.ds.getConnection();
            connection.setAutoCommit(false);

            if (orderDAO.saveOd(new Orders(ordersDTO.getOid(), ordersDTO.getDate(), ordersDTO.getCustomerID(), ordersDTO.getDiscount(), ordersDTO.getTotal(), ordersDTO.getSubTotal()))) {
                if (orderDetailsDAO.saveOrderDetails(ordersDTO.getOid(), ordersDTO.getOrderDetailsArrayList())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        connection.close();
        return false;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        return orderDAO.generateID();
    }


}
