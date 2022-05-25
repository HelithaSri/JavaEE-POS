package servlet;

import bo.BOFactory;
import bo.custom.impl.ItemBOImpl;
import dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/16/2022 - 7:03 PM
 * @project JavaEE POS Backend
 */
@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource ds;
    ItemBOImpl itemBO = (ItemBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ITEM);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String itemCode = req.getParameter("itemCodeF");
        String itemName = req.getParameter("itemNameF");
        int itemQty = Integer.parseInt(req.getParameter("itemQtyF"));
        int itemPrice = Integer.parseInt(req.getParameter("itemPriceF"));
        ItemDTO itemDTO = new ItemDTO(itemCode, itemName, itemQty, itemPrice);
        PrintWriter writer = resp.getWriter();
        JsonObjectBuilder response = Json.createObjectBuilder();

        try {
            if (itemBO.addItem(itemDTO)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_OK);
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
            String option = req.getParameter("option");
            switch (option) {
                case "GENERATED_ID":
                    dataMsgBuilder.add("data", itemBO.generateItemID());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", "200");
                    writer.print(dataMsgBuilder.build());
                    break;

                case "GETALL":
                    resp.setStatus(HttpServletResponse.SC_OK);//201
                    dataMsgBuilder.add("data", itemBO.getAllItems());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", "200");
                    writer.print(dataMsgBuilder.build());
                    break;

                case "SEARCH":
                    String id = req.getParameter("code");
                    resp.setStatus(HttpServletResponse.SC_OK);//201
                    dataMsgBuilder.add("data", itemBO.searchItem(id));
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", "200");
                    writer.print(dataMsgBuilder.build());
                    break;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String itemCode = req.getParameter("itemCode");

        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        try {
            if (itemBO.deleteItem(itemCode)) {
                resp.setStatus(HttpServletResponse.SC_OK); //200
                dataMsgBuilder.add("data", "");
                dataMsgBuilder.add("message", "Item Deleted");
                dataMsgBuilder.add("status", "200");
                writer.print(dataMsgBuilder.build());
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_OK); //200
            dataMsgBuilder.add("status", 400);
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("data", e.getLocalizedMessage());
            writer.print(dataMsgBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        //we have to get updated data from JSON format
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String code = jsonObject.getString("code");
        String name = jsonObject.getString("name");
        int qtyOnHand = jsonObject.getInt("qtyOnHand");
        int unitPrice = jsonObject.getInt("unitPrice");
        ItemDTO itemDTO = new ItemDTO(code, name, qtyOnHand, unitPrice);
        JsonObjectBuilder response = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();
//        System.out.println(code + " " + name + " " + qtyOnHand + " " + unitPrice);

        try {
            if (itemBO.updateItem(itemDTO)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Updated");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_OK); //200
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
        }
    }
}
