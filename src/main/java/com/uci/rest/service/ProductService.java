package com.uci.rest.service;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import com.uci.rest.db.DatabaseConnector;
import com.uci.rest.db.DatabaseUtils;
import com.uci.rest.model.Product;
import com.uci.rest.model.Order;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductService {

    private final static String ALL_PRODUCTS_QUERY = "SELECT * FROM product";
    private final static String ALL_ORDERS_QUERY = "SELECT * FROM orders";

    public static Product getProductById(int id) {
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_PRODUCTS_QUERY + " WHERE pid = " + id);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setPID(resultSet.getInt("pid"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory(resultSet.getString("category"));
                    product.setBrand(resultSet.getString("brand"));
                    product.setPrice(resultSet.getFloat("price"));

                    return product;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {

                    // We will always close the connection once we are done interacting with the Database.
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }
    
    public static Order getOrderById(int id){
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_ORDERS_QUERY + " WHERE OrderNumber = " + id);
        Order order = new Order();
        if (resultSet != null) {
            try {
                resultSet.next();
                order.setFirstName(resultSet.getString("firstName"));
                order.setLastName(resultSet.getString("lastName"));
                order.setEmail(resultSet.getString("email"));
                order.setTelephone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setCity(resultSet.getString("city"));
                order.setState(resultSet.getString("state"));
                order.setBillingZip(resultSet.getString("zip"));
                order.setCountry(resultSet.getString("country"));
                order.setCardNumber(resultSet.getString("cardNumber"));
                order.setExpirationMonth(resultSet.getInt("cardExpirationMonth"));
                order.setExpirationYear(resultSet.getInt("cardExpirationYear"));
                order.setCCV(resultSet.getInt("ccv"));
                order.setShippingOption(resultSet.getInt("shippingCost"));
                order.setShippingAddress(resultSet.getString("shippingAddress"));
                order.setShippingCity(resultSet.getString("shippingCity"));
                order.setShippingState(resultSet.getString("shippingState"));
                order.setShippingZip(resultSet.getInt("shippingZip"));
                order.setShippingCountry(resultSet.getString("shippingCountry"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setGrandTotal(resultSet.getFloat("totalPrice"));
                return order;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // We will always close the connection once we are done interacting with the Database.
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    
    public static List<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<Product>();

        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_PRODUCTS_QUERY);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Product product = new Product();

                    product.setPID(resultSet.getInt("pid"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory(resultSet.getString("category"));
                    product.setBrand(resultSet.getString("brand"));
                    product.setPrice(resultSet.getFloat("price"));

                    products.add(product);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return products;
    }

    public static void AddOrder(Order order){
        Connection conn = DatabaseConnector.getConnection();
        try{
            PreparedStatement pstmt = null;
            //pstmt = conn.prepareStatement("INSERT INTO dummy (id) VALUES (1)");
            pstmt = conn.prepareStatement("INSERT INTO Orders (OrderDate, firstName, lastName, email, phone, "
                    + "address, city, state, zip, country, cardNumber, cardExpirationMonth, cardExpirationYear, "
                    + "ccv, shippingCost, shippingAddress, shippingCity, shippingState, shippingZip, shippingCountry, "
                    + "quantity, totalPrice) VALUES ("
                    + "?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            
            pstmt.setDate(1, date);
            pstmt.setString(2, order.getFirstName());
            pstmt.setString(3, order.getLastName());
            pstmt.setString(4, order.getEmail());
            pstmt.setString(5, order.getTelephone());
            pstmt.setString(6, order.getAddress());
            pstmt.setString(7, order.getCity());
            pstmt.setString(8, order.getState());
            //pstmt.setInt(9, order.getBillingZip());
            pstmt.setString(9, order.getBillingZip());
            pstmt.setString(10, order.getCountry());
            pstmt.setString(11, order.getCardNumber());
            pstmt.setInt(12, order.getExpirationMonth());
            pstmt.setInt(13, order.getExpirationYear());
            pstmt.setInt(14, order.getCCV());
            pstmt.setInt(15, order.getShippingOption());
            pstmt.setString(16, order.getShippingAddress());
            pstmt.setString(17, order.getShippingCity());
            pstmt.setString(18, order.getShippingState());
            pstmt.setInt(19, order.getShippingZip());
            pstmt.setString(20, order.getShippingCountry());
            pstmt.setInt(21, order.getQuantity());
            pstmt.setDouble(22, Double.parseDouble(String.format("%.2f", order.getGrandTotal())));
            int result = pstmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            try {
                // We will always close the connection once we are done interacting with the Database.
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    public static boolean updateProduct(Product product) {
//
//        String sql = "UPDATE TODOS SET TODO_SUMMARY=?, TODO_DESC=? WHERE TODO_ID=?;";
//
//        Connection connection = DatabaseConnector.getConnection();
//
//        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, product.getSummary(), product.getDescription(),
//                String.valueOf(product.getId()));
//
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return updateStatus;
//
//    }
//
    public static void deleteOrder() throws SQLException {
        Connection conn = DatabaseConnector.getConnection();

        try {       
            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement("DELETE FROM Orders ORDER BY OrderNumber DESC limit 1", 
            PreparedStatement.RETURN_GENERATED_KEYS);
            System.out.println(pstmt);
            int result = pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean updateOrder(int orderNumber, Order order) throws SQLException{
        Connection conn = DatabaseConnector.getConnection();
        try {     
            System.out.println("here");
            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement("UPDATE Orders SET firstName = 'Chris' WHERE OrderNumber =" + orderNumber, 
            PreparedStatement.RETURN_GENERATED_KEYS);
            System.out.println(pstmt);
            int result = pstmt.executeUpdate();
            conn.close();
            if (result != 0){
                return true;
            } else {
                return false;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
