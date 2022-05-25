package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Orders;
import servlet.OrderServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:09 PM
 * @project JavaEE POS Backend
 */

public class OrderDAOImpl implements OrderDAO {
    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        return null;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        Connection conn = OrderServlet.ds.getConnection();
        ResultSet rst = conn.prepareStatement("SELECT oid FROM orders ORDER BY oid DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                objectBuilder.add("oId", "O00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("oId", "O00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("oId", "O00-" + tempId);
            }
        } else {
            objectBuilder.add("oId", "O00-000");
        }
        conn.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean add(Orders orders) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Orders orders) {
        return false;
    }

    @Override
    public boolean saveOd(Orders orders) throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
        pstm.setObject(1, orders.getOid());
        pstm.setObject(2, orders.getDate());
        pstm.setObject(3, orders.getCustomerID());
        pstm.setObject(4, orders.getDiscount());
        pstm.setObject(5, orders.getTotal());
        pstm.setObject(6, orders.getSubTotal());
        return pstm.executeUpdate() > 0;
    }
}
