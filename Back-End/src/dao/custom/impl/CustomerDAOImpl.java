package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import servlet.CustomerServlt;

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

public class CustomerDAOImpl implements CustomerDAO {
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    @Override
    public JsonArrayBuilder getAll() throws SQLException {

//        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        Connection conn = CustomerServlt.ds.getConnection();
        ResultSet rst = conn.prepareStatement("SELECT * FROM customer").executeQuery();
        while (rst.next()) {
            String cusID = rst.getString(1);
            String cusName = rst.getString(2);
            String cusAddress = rst.getString(3);
            int cusSalary = rst.getInt(4);

//            resp.setStatus(HttpServletResponse.SC_OK);//201

            objectBuilder.add("id", cusID);
            objectBuilder.add("name", cusName);
            objectBuilder.add("address", cusAddress);
            objectBuilder.add("salary", cusSalary);

            arrayBuilder.add(objectBuilder.build());

            System.out.println(cusID + " " + cusAddress + " " + cusName + " " + cusSalary);
        }
        conn.close();
        return arrayBuilder;
    }

    @Override
    public JsonObjectBuilder generateID() throws SQLException {
        Connection conn = CustomerServlt.ds.getConnection();
        ResultSet rstI = conn.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1").executeQuery();
        if (rstI.next()) {
            int tempId = Integer.parseInt(rstI.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                objectBuilder.add("id", "C00-00" + tempId);
            } else if (tempId < 100) {
                objectBuilder.add("id", "C00-0" + tempId);
            } else if (tempId < 1000) {
                objectBuilder.add("id", "C00-" + tempId);
            }
        } else {
            objectBuilder.add("id", "C00-000");
        }
        return objectBuilder;
    }

    @Override
    public JsonArrayBuilder search(String id) throws SQLException {
        Connection conn = CustomerServlt.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM customer WHERE CONCAT(id,name) LIKE ?");
        pstm.setObject(1, "%"+id+"%");
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String cusIDS = resultSet.getString(1);
            String cusNameS = resultSet.getString(2);
            String cusAddressS = resultSet.getString(3);
            int cusSalaryS = resultSet.getInt(4);

            objectBuilder.add("id", cusIDS);
            objectBuilder.add("name", cusNameS);
            objectBuilder.add("address", cusAddressS);
            objectBuilder.add("salary", cusSalaryS);

            arrayBuilder.add(objectBuilder.build());

            System.out.println(cusIDS + " " + cusNameS + " " + cusAddressS + " " + cusSalaryS);
        }
        return arrayBuilder;
    }

    @Override
    public boolean add(Customer customer) throws SQLException {
        Connection conn = CustomerServlt.ds.getConnection();
        PreparedStatement pstm = conn.prepareStatement("INSERT INTO customer values(?,?,?,?)");
        pstm.setObject(1, customer.getId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getSalary());
        boolean b = pstm.executeUpdate() > 0;
        conn.close();
        return b;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection con = CustomerServlt.ds.getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setObject(1, id);
        boolean b = pstm.executeUpdate() > 0;
        con.close();
        return b;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        Connection con = CustomerServlt.ds.getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getSalary());
        pstm.setObject(4, customer.getId());
        boolean b = pstm.executeUpdate() > 0;
        con.close();
        return b;
    }
}
