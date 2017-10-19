package com.uci.rest;

import com.uci.rest.model.Order;
import com.uci.rest.model.Product;
import com.uci.rest.service.ProductService;
import java.sql.SQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.glassfish.jersey.filter.LoggingFilter;

/**
 * This class contains the methods that will respond to the various endpoints that you define for your RESTful API Service.
 *
 */
//products will be the pathsegment that precedes any path segment specified by @Path on a method.
@Path("/products")
public class ProductResource {

    //This method represents an endpoint with the URL /todos and a GET request.
    // Since there is no @PathParam mentioned, the /todos as a relative path and a GET request will invoke this method.
    @GET
    @Produces( { MediaType.APPLICATION_JSON })
    public Response getAllProducts() {
        List<Product> productList = ProductService.getAllProducts();

        if(productList == null || productList.isEmpty()) {
        }
        return Response.ok(productList).build();
    }
    
    @Path("cart")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getShoppingCart(@PathParam("pid") int pid/* The {id} placeholder parameter is resolved */) {
        //invokes the DB method which will fetch a todo_list item object by id
        Product product = ProductService.getProductById(pid);

        //Respond with a 404 if there is no such todo_list item for the id provided
        if(product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a todo_list_item object to return as response
        return Response.ok(product).build();
    }
    
    @Path("{pid}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON }) //This provides only JSON responses
    public Response getProductById(@PathParam("pid") int pid/* The {id} placeholder parameter is resolved */) {
        //invokes the DB method which will fetch a todo_list item object by id
        Product product = ProductService.getProductById(pid);

        //Respond with a 404 if there is no such todo_list item for the id provided
        if(product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        //Respond with a 200 OK if you have a todo_list_item object to return as response
        return Response.ok(product).build();
    }
    
   

    //This method represents an endpoint with the URL /todos and a POST request.
    // Since there is no @PathParam mentioned, the /todos as a relative path and a POST request will invoke this method.
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON}) //This method accepts a request of the JSON type
//    public Response addProduct(Product todo) {
//
//        //The todo object here is automatically constructed from the json request. Jersey is so cool!
//        if(ProductService.AddProduct(todo)) {
//            return Response.ok().entity("TODO Added Successfully").build();
//        }
//
//        // Return an Internal Server error because something wrong happened. This should never be executed
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//
//
//    }
//
    //Similar to the method above.
    @Path("/order")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) //This method accepts form parameters.
    @POST
    //If you were to send a POST through a form submit, this method would be called.
    public Response addProduct(@FormParam("first-name") String firstName,
                            @FormParam("last-name") String lastName, @FormParam("email") String email, @FormParam("telephone") String telephone,
                            @FormParam("address") String address, @FormParam("city") String city, @FormParam("state") String state, 
                            @FormParam("postal-code") String billingZip, @FormParam("country") String country, @FormParam("card-number") String cardNumber,
                            @FormParam("expiration-month") int expirationMonth, @FormParam("expiration-year") int expirationYear,
                            @FormParam("CCV") int ccv, @FormParam("shipping-option") int shippingOption, @FormParam("shipping-address") String shippingAddress,
                            @FormParam("shipping-city") String shippingCity, @FormParam("shipping-state") String shippingState, 
                            @FormParam("shipping-postal-code") int shippingZip, @FormParam("shipping-country") String shippingCountry,
                            @FormParam("quantity") int quantity, @FormParam("grandTotal") float grandTotal){
        try{
            Order order = new Order();
            order.setFirstName(firstName);
            order.setLastName(lastName);
            order.setEmail(email);
            order.setTelephone(telephone);
            order.setAddress(address);
            order.setCity(city);
            order.setState(state);
            order.setBillingZip(billingZip);
            order.setCountry(country);
            order.setCardNumber(cardNumber);
            order.setExpirationMonth(expirationMonth);
            order.setExpirationYear(expirationYear);
            order.setCCV(ccv);
            order.setShippingOption(shippingOption);
            order.setShippingAddress(shippingAddress);
            order.setShippingCity(shippingCity);
            order.setShippingState(shippingState);
            order.setShippingZip(shippingZip);
            order.setShippingCountry(shippingCountry);
            order.setQuantity(quantity);
            order.setGrandTotal(grandTotal);
            ProductService.AddOrder(order);
            return Response.ok().entity("Successful").build();
            } catch (Exception e){
                System.out.println("ERROR MESSAGE: " + e.getMessage());
            } 

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
//
//    //This method represents a PUT request where the id is provided as a path parameter and the request body is provided in JSON
//    @PUT
//    @Path("{id}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response updateProduct(@PathParam("id") int id, Product todo) {
//
//        // Retrieve the todo that you will need to change
//        Product retrievedProduct = ProductService.getProductById(id);
//
//        if(retrievedProduct == null) {
//            //If not found then respond with a 404 response.
//            return Response.status(Response.Status.NOT_FOUND).
//                    entity("We could not find the requested resource").build();
//        }
//
//        // This is the todo_object retrieved from the json request sent.
//        todo.setId(id);
//
//        // if the user has provided null, then we set the retrieved values.
//        // This is done so that a null value is not passed to the todo object when updating it.
//        if(todo.getDescription() == null) {
//            todo.setDescription(retrievedProduct.getDescription());
//        }
//
//        //Same as above. We only change fields in the todo_resource when the user has entered something in a request.
//        if (todo.getSummary() == null) {
//            todo.setSummary(retrievedProduct.getSummary());
//        }
//
//        //This calls the JDBC method which in turn calls the the UPDATE SQL command
//        if(ProductService.updateProduct(todo)) {
//
//            return Response.ok().entity(todo).build();
//        }
//
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//
//
//    }
//
//    //This method represents a DELETE request where the id is provided as a path parameter and the request body is provided in JSON
    @DELETE
    @Path("/cancelOrder")
    
    @Consumes({MediaType.APPLICATION_JSON})
    public Response cancelOrder() {
        try{
            ProductService.deleteOrder();
            return Response.ok().entity("Deleted Successfully").build();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
     //This method represents a PUT request where the id is provided as a path parameter and the request body is provided in JSON
    @PUT
    @Path("/update/{id}")
//    @Produces( { MediaType.APPLICATION_JSON })
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateTodo(@PathParam("id") int id) throws SQLException {
        // Retrieve the todo that you will need to change
        Order retrievedOrder = ProductService.getOrderById(id);
        System.out.println(retrievedOrder);
        if(retrievedOrder == null) {
            //If not found then respond with a 404 response.
            return Response.status(Response.Status.NOT_FOUND).
                    entity("We could not find the requested resource").build();
        }
        else{
        //This calls the JDBC method which in turn calls the the UPDATE SQL command
            if(ProductService.updateOrder(id, retrievedOrder)) {
                return Response.ok().entity("Successfully updated").build();
            }
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    }

}
