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
 * @created 5/16/2022 - 7:03 PM
 * @project JavaEE POS Backend
 */
@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String itemCode = req.getParameter("itemCodeF");
        String itemName = req.getParameter("itemNameF");
        int itemQty = Integer.parseInt(req.getParameter("itemQtyF"));
        int itemPrice = Integer.parseInt(req.getParameter("itemPriceF"));

        PrintWriter writer = resp.getWriter();
        Connection connection = null;
        try {
            connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUE(?,?,?,?)");
            pstm.setObject(1, itemCode);
            pstm.setObject(2, itemName);
            pstm.setObject(3, itemQty);
            pstm.setObject(4, itemPrice);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();
            String option = req.getParameter("option");
            switch (option){
                case "GETALL":
                    ResultSet rst = connection.prepareStatement("SELECT * FROM item").executeQuery();
                    while (rst.next()) {
                        String itemCode = rst.getString(1);
                        String itemName = rst.getString(2);
                        int itemQtyOnHand = rst.getInt(3);
                        int itemUnitPrice = rst.getInt(4);

                        resp.setStatus(HttpServletResponse.SC_OK);//201

                        objectBuilder.add("code", itemCode);
                        objectBuilder.add("name", itemName);
                        objectBuilder.add("qtyOnHand", itemQtyOnHand);
                        objectBuilder.add("unitPrice", itemUnitPrice);

                        arrayBuilder.add(objectBuilder.build());

                        System.out.println(itemCode + " " + itemName + " " + itemQtyOnHand + " " + itemUnitPrice);
                    }
//                    JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
                    dataMsgBuilder.add("data", arrayBuilder.build());
                    dataMsgBuilder.add("massage", "Done");
                    dataMsgBuilder.add("status", "200");

//                    PrintWriter writer = resp.getWriter();
                    writer.print(dataMsgBuilder.build());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
