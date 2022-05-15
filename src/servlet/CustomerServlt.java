package servlet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/15/2022 - 12:04 PM
 * @project JavaEE POS Backend
 */
@WebServlet(urlPatterns = "/customer")
public class CustomerServlt extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer values(?,?,?,?)");
            pstm.setObject(1,cusID);
            pstm.setObject(2,cusName);
            pstm.setObject(3,cusAddress);
            pstm.setObject(4,cusSalary);

            if (pstm.executeUpdate()>0){
                System.out.println("cus done");

            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
