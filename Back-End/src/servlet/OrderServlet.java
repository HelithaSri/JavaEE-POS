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
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    Connection connection = null;

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
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject);

        JsonObject order = jsonObject.getJsonObject("order");
        System.out.println(order);
        JsonArray orderDetail = jsonObject.getJsonArray("orderDetail");

        /*String orderId = order.getString("orderId");
        String customer = order.getString("customer");
        String orderDate = order.getString("orderDate");
        int discount = order.getInt("discount");
        String total = order.getString("total");
        String subTotal = order.getString("subTotal");
*/

        /*for (JsonValue orderDetails:orderDetail) {
            JsonObject orderDetailJsonObj = orderDetails.asJsonObject();
            String itemCode = orderDetailJsonObj.getString("itemCode");
            System.out.println(itemCode);
        }*/

        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        for (JsonValue value:orderDetail) {
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

        System.out.println("before if");
        JsonObjectBuilder response = Json.createObjectBuilder();

            if (ordersBO.addOrder(ordersDTO)) {
                System.out.println("true");
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Order Successful");
                response.add("data", "");
            } else {
                System.out.println("false");
                resp.setStatus(HttpServletResponse.SC_OK);//201
                response.add("status", 400);
                response.add("message", "Order Not Successful");
                response.add("data", "");
            }

        writer.print(response.build());
    }

/*    public boolean saveOrder(JsonObject order, JsonArray orderDetail) {

        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");
            pstm.setObject(1, order.getString("orderId"));
            pstm.setObject(2, order.getString("orderDate"));
            pstm.setObject(3, order.getString("customer"));
            pstm.setObject(4, order.getInt("discount"));
            pstm.setObject(5, order.getString("total"));
            pstm.setObject(6, order.getString("subTotal"));

            if (pstm.executeUpdate() > 0) {
                if (saveOrderDetails(order.getString("orderId"), orderDetail)) {
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
                if (connection != null) {
                    connection.close();
                }
//                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean saveOrderDetails(String oid, JsonArray orderDetail) throws SQLException {
        for (JsonValue orderDetails : orderDetail) {
            JsonObject orderDetailJsonObj = orderDetails.asJsonObject();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderdetails VALUES(?,?,?,?,?)");
            pstm.setObject(1, oid);
            pstm.setObject(2, orderDetailJsonObj.getString("itemCode"));
            pstm.setObject(3, orderDetailJsonObj.getString("itemQty"));
            pstm.setObject(4, orderDetailJsonObj.getString("itemPrice"));
            pstm.setObject(5, orderDetailJsonObj.getString("itemTotal"));

            *//*String itemCode = orderDetailJsonObj.getString("itemCode");
            String itemName = orderDetailJsonObj.getString("itemName");
            String itemPrice = orderDetailJsonObj.getString("itemPrice");
            String itemQty = orderDetailJsonObj.getString("itemQty");
            String itemTotal = orderDetailJsonObj.getString("itemTotal");*//*

            if (pstm.executeUpdate() > 0) {
                if (updateItem(orderDetailJsonObj.getString("itemCode"), orderDetailJsonObj.getString("itemQty"))) {

                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
        return true;
    }

    public boolean updateItem(String ItemCode, String itemQty) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement("UPDATE item SET qtyOnHand=(qtyOnHand-?) WHERE code=?");
        pstm.setObject(1, itemQty);
        pstm.setObject(2, ItemCode);
        return pstm.executeUpdate() > 0;
    }*/
}
