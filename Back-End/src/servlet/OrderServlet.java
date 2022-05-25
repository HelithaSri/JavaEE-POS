package servlet;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrdersBOImpl;
import dto.OrderDetails;
import dto.OrdersDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Helitha Sri
 * @created 5/19/2022 - 3:06 PM
 * @project JavaEE POS Backend
 */
@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource ds;

    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.CUSTOMER);
    ItemBOImpl itemBO = (ItemBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ITEM);
    OrdersBOImpl ordersBO = (OrdersBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ORDER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        resp.setStatus(HttpServletResponse.SC_OK);//200
        try {
            String option = req.getParameter("option");
            switch (option) {
                case "LOAD_CUS_ID":
                    dataMsgBuilder.add("data", customerBO.loadAllCusIDs());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;

                case "SELECTED_CUS":
                    String selectID = req.getParameter("cusID");
                    dataMsgBuilder.add("data", customerBO.loadSelectedCusData(selectID));
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;

                case "GENERATED_OID":
                    dataMsgBuilder.add("data", ordersBO.generateID());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;

                case "LOAD_ITEM_ID":
                    dataMsgBuilder.add("data", itemBO.loadAllItemIDs());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;

                case "SELECTED_ITEM":
                    String selectItemID = req.getParameter("itemID");
                    dataMsgBuilder.add("data", itemBO.loadSelectedItemData(selectItemID));
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_OK);//200
            dataMsgBuilder.add("data", e.getLocalizedMessage());
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("status", 400);
            writer.print(dataMsgBuilder.build());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        JsonObjectBuilder response = Json.createObjectBuilder();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        JsonObject order = jsonObject.getJsonObject("order");
        JsonArray orderDetail = jsonObject.getJsonArray("orderDetail");

        ArrayList<OrderDetails> orderDetails = new ArrayList<>();

        for (JsonValue value : orderDetail) {
            JsonObject jObj = value.asJsonObject();
            orderDetails.add(new OrderDetails(
                    order.getString("orderId"),
                    jObj.getString("itemCode"),
                    Integer.parseInt(jObj.getString("itemQty")),
                    Double.parseDouble(jObj.getString("itemPrice")),
                    Integer.parseInt(jObj.getString("itemTotal"))
            ));
        }

        OrdersDTO ordersDTO = new OrdersDTO(
                order.getString("orderId"),
                order.getString("orderDate"),
                order.getString("customer"),
                order.getInt("discount"),
                Double.parseDouble(order.getString("total")),
                Double.parseDouble(order.getString("subTotal")),
                orderDetails
        );

        if (ordersBO.addOrder(ordersDTO)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);//201
            response.add("status", 200);
            response.add("message", "Order Successful");
            response.add("data", "");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);//201
            response.add("status", 400);
            response.add("message", "Order Not Successful");
            response.add("data", "");
        }
        writer.print(response.build());
    }
}
