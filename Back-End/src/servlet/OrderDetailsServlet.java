package servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/21/2022 - 1:02 AM
 * @project JavaEE POS Backend
 */

@WebServlet(urlPatterns = "/oDetails")
public class OrderDetailsServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        String orderID = req.getParameter("orderID");
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT o.customerID, o.date, o.subTotal, od.*, i.description FROM orders o, orderdetails od, item i WHERE (o.oid =od.oid AND od.itemCode=CODE) AND CONCAT(o.oid) LIKE ?");
            pstm.setObject(1,"%"+orderID+"%");
            ResultSet rst = pstm.executeQuery();
            resp.setStatus(HttpServletResponse.SC_OK);//201

            while (rst.next()){
                String cusiD = rst.getString(1);
                String date = rst.getString(2);
                String subTotal = rst.getString(3);
                String oID = rst.getString(4);
                String itemCode = rst.getString(5);
                String qty = rst.getString(6);
                String unitPrice = rst.getString(7);
                String total = rst.getString(8);
                String itemName = rst.getString(9);

                objectBuilder.add("cusID", cusiD);
                objectBuilder.add("date", date);
                objectBuilder.add("subTotal", subTotal);
                objectBuilder.add("oID", oID);
                objectBuilder.add("itemCode", itemCode);
                objectBuilder.add("qty", qty);
                objectBuilder.add("unitPrice", unitPrice);
                objectBuilder.add("total", total);
                objectBuilder.add("itemName", itemName);
                arrayBuilder.add(objectBuilder.build());
            }
            dataMsgBuilder.add("data", arrayBuilder.build());
            dataMsgBuilder.add("message", "Done");
            dataMsgBuilder.add("status", 200);
            writer.print(dataMsgBuilder.build());

        } catch (SQLException e) {
            e.printStackTrace();
            dataMsgBuilder.add("data", arrayBuilder.build());
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("status", 400);
            writer.print(dataMsgBuilder.build());
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
