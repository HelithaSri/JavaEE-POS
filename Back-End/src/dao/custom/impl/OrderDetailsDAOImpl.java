package dao.custom.impl;

import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import dto.OrderDetails;
import servlet.OrderServlet;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:09 PM
 * @project JavaEE POS Backend
 */

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        return null;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        return null;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean add(entity.OrderDetails orderDetails) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(entity.OrderDetails orderDetails) {
        return false;
    }

    @Override
    public boolean saveOrderDetails(String id, ArrayList<OrderDetails> dtos) throws SQLException {
        for (OrderDetails items:dtos) {
            System.out.println("save od s");
            Connection connection = OrderServlet.ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderdetails VALUES(?,?,?,?,?)");
            pstm.setObject(1, items.getOid());
            pstm.setObject(2, items.getItemCode());
            pstm.setObject(3, items.getQty());
            pstm.setObject(4, items.getUnitPrice());
            pstm.setObject(5, items.getTotal());
            if (pstm.executeUpdate()>0){
                if (itemDAO.updateQty(items.getItemCode(),items.getQty())){
                    System.out.println("i update done");
                }else {
                    System.out.println("i not update");
                    return false;
                }
            }else {
                System.out.println("od not add");
                return false;
            }
        }
        return true;
    }
}
