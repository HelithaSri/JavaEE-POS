package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import servlet.ItemServlet;
import servlet.OrderServlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:08 PM
 * @project JavaEE POS Backend
 */

public class ItemDAOImpl implements ItemDAO {
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    @Override
    public JsonArrayBuilder getAll() throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        ResultSet rst = conn.prepareStatement("SELECT * FROM item").executeQuery();
        while (rst.next()) {
            String itemCode = rst.getString(1);
            String itemName = rst.getString(2);
            int itemQtyOnHand = rst.getInt(3);
            int itemUnitPrice = rst.getInt(4);

            objectBuilder.add("code", itemCode);
            objectBuilder.add("name", itemName);
            objectBuilder.add("qtyOnHand", itemQtyOnHand);
            objectBuilder.add("unitPrice", itemUnitPrice);

            arrayBuilder.add(objectBuilder.build());

            System.out.println(itemCode + " " + itemName + " " + itemQtyOnHand + " " + itemUnitPrice);
        }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        ResultSet rstI = conn.prepareStatement("SELECT code FROM item ORDER BY code DESC LIMIT 1").executeQuery();
        if (rstI.next()) {
            int tempId = Integer.parseInt(rstI.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                objectBuilder.add("code", "I00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("code", "I00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("code", "I00-" + tempId);
            }
        } else {
            objectBuilder.add("code", "I00-000");
        }
        conn.close();
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM item WHERE CONCAT(code,description) LIKE ?");
        pstm.setObject(1, "%"+id+"%");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String itemCodeS = resultSet.getString(1);
            String itemNameS = resultSet.getString(2);
            int itemQtyOnHandS = resultSet.getInt(3);
            int itemUnitPriceS = resultSet.getInt(4);

            objectBuilder.add("code", itemCodeS);
            objectBuilder.add("name", itemNameS);
            objectBuilder.add("qtyOnHand", itemQtyOnHandS);
            objectBuilder.add("unitPrice", itemUnitPriceS);

            arrayBuilder.add(objectBuilder.build());
        }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public boolean add(Item item) throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("INSERT INTO item VALUE(?,?,?,?)");
        pstm.setObject(1, item.getCode());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(3, item.getQtyOnHand());
        pstm.setObject(4, item.getUnitPrice());
        boolean b = pstm.executeUpdate() > 0;
        conn.close();
        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("DELETE FROM item WHERE code=?");
        pstm.setObject(1, id);
        boolean b = pstm.executeUpdate() > 0;
        conn.close();
        return b;
    }

    @Override
    public boolean update(Item item) throws SQLException {
        Connection conn = ItemServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("UPDATE item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?");
        pstm.setObject(1, item.getDescription());
        pstm.setObject(2, item.getQtyOnHand());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getCode());
        boolean b = pstm.executeUpdate() > 0;
        conn.close();
        return b;
    }

    @Override
    public JsonArrayBuilder loadItemId() throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        ResultSet rst = conn.prepareStatement("SELECT code FROM item").executeQuery();
        while (rst.next()) {
            String code = rst.getString(1);
            objectBuilder.add("code", code);
            arrayBuilder.add(objectBuilder.build());
        }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonArrayBuilder loadSelectItemDetails(String id) throws SQLException {
        Connection conn = OrderServlet.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM item WHERE code=?");
        pstm.setObject(1, id);
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            String description = rst.getString(2);
            String qtyOnHand = rst.getString(3);
            String unitPrice = rst.getString(4);

            objectBuilder.add("description", description);
            objectBuilder.add("qtyOnHand", qtyOnHand);
            objectBuilder.add("unitPrice", unitPrice);
            arrayBuilder.add(objectBuilder.build());
        }
        conn.close();
        return arrayBuilder;
    }
}
