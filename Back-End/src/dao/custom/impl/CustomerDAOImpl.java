package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import servlet.CustomerServlt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/24/2022 - 4:08 PM
 * @project JavaEE POS Backend
 */

public class CustomerDAOImpl implements CustomerDAO {

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
