package servlet;

import bo.BOFactory;
import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
import dto.CustomerDTO;
import entity.Customer;

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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/15/2022 - 12:04 PM
 * @project JavaEE POS Backend
 */

@WebServlet(urlPatterns = "/customer")
public class CustomerServlt extends HttpServlet {

    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BoTypes.CUSTOMER);

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");
        CustomerDTO customerDTO = new CustomerDTO(cusID, cusName, cusAddress, Integer.parseInt(cusSalary));
        PrintWriter writer = resp.getWriter();

//        Connection connection = null;
        try {
            /*connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer values(?,?,?,?)");
            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);
            pstm.setObject(4, cusSalary);*/

            if (customerBO.addCustomer(customerDTO)) {
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
            resp.setStatus(HttpServletResponse.SC_OK); //200
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

        Connection connection = null;
        try {
            connection = ds.getConnection();
            String option = req.getParameter("option");
            switch (option) {
                case "GENERATED_ID":
                    dataMsgBuilder.add("data", customerBO.generateCustomerID());
//                    dataMsgBuilder.add("data", objectBuilder.build());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;

                case "GETALL":
                    /*ResultSet rst = connection.prepareStatement("SELECT * FROM customer").executeQuery();
                    while (rst.next()) {
                        String cusID = rst.getString(1);
                        String cusName = rst.getString(2);
                        String cusAddress = rst.getString(3);
                        int cusSalary = rst.getInt(4);

                        resp.setStatus(HttpServletResponse.SC_OK);//201

                        objectBuilder.add("id", cusID);
                        objectBuilder.add("name", cusName);
                        objectBuilder.add("address", cusAddress);
                        objectBuilder.add("salary", cusSalary);

                        arrayBuilder.add(objectBuilder.build());

                        System.out.println(cusID + " " + cusAddress + " " + cusName + " " + cusSalary);
                    }*/
                    resp.setStatus(HttpServletResponse.SC_OK);//201
                    dataMsgBuilder.add("data", customerBO.getAllCustomer());
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);
                    writer.print(dataMsgBuilder.build());
                    break;
                case "SEARCH":
                    String id = req.getParameter("id");
                    resp.setStatus(HttpServletResponse.SC_OK);//201
                    /*PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE id LIKE ?");
                    pstm.setObject(1, "%"+id+"%");
                    ResultSet resultSet = pstm.executeQuery();

                    while (resultSet.next()) {
                        String cusIDS = resultSet.getString(1);
                        String cusNameS = resultSet.getString(2);
                        String cusAddressS = resultSet.getString(3);
                        int cusSalaryS = resultSet.getInt(4);

                        resp.setStatus(HttpServletResponse.SC_OK);//201

                        objectBuilder.add("id", cusIDS);
                        objectBuilder.add("name", cusNameS);
                        objectBuilder.add("address", cusAddressS);
                        objectBuilder.add("salary", cusSalaryS);

                        arrayBuilder.add(objectBuilder.build());

                        System.out.println(cusIDS + " " + cusNameS + " " + cusAddressS + " " + cusSalaryS);
                    }*/
                    dataMsgBuilder.add("data", customerBO.searchCustomer(id));
                    dataMsgBuilder.add("message", "Done");
                    dataMsgBuilder.add("status", 200);

                    writer.print(dataMsgBuilder.build());
                    break;
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String customerID = req.getParameter("customerID");
        System.out.println("cus : " + " " + customerID);
        JsonObjectBuilder dataMsgBuilder = Json.createObjectBuilder();
        PrintWriter writer = resp.getWriter();

//        Connection connection = null;
        try {
            /*connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            pstm.setObject(1, customerID);*/

            if (customerBO.deleteCustomer(customerID)) {
                resp.setStatus(HttpServletResponse.SC_OK); //200
                dataMsgBuilder.add("data", "");
                dataMsgBuilder.add("message", "Customer Deleted");
                dataMsgBuilder.add("status", 200);
                writer.print(dataMsgBuilder.build());
            }
        } catch (SQLException e) {
            dataMsgBuilder.add("status", 400);
            dataMsgBuilder.add("message", "Error");
            dataMsgBuilder.add("data", e.getLocalizedMessage());
            writer.print(dataMsgBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        //we have to get updated data from JSON format
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String cusIDUpdate = jsonObject.getString("id");
        String cusNameUpdate = jsonObject.getString("name");
        String cusAddressUpdate = jsonObject.getString("address");
        String cusSalaryUpdate = jsonObject.getString("salary");
        CustomerDTO customerDTO = new CustomerDTO(cusIDUpdate, cusNameUpdate, cusAddressUpdate, Integer.parseInt(cusSalaryUpdate));
        PrintWriter writer = resp.getWriter();
        System.out.println(cusIDUpdate + " " + cusAddressUpdate + " " + cusSalaryUpdate + " " + cusNameUpdate);

//        Connection connection = null;
        try {

            /*connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
            pstm.setObject(1, cusNameUpdate);
            pstm.setObject(2, cusAddressUpdate);
            pstm.setObject(3, cusSalaryUpdate);
            pstm.setObject(4, cusIDUpdate);*/

            if (customerBO.updateCustomer(customerDTO)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Updated");
                response.add("data", "");
                writer.print(response.build());
            }

        } catch (SQLException e) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", e.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
        } /*finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }
}
