/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Repositories.StockTransactionRepository;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author lakshan
 */
@Path("StockTransaction")
public class StockTransactionResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StockTransactionResource
     */
    public StockTransactionResource() {
    }

    /**
     * Retrieves representation of an instance of
     * Controllers.StockTransactionResource
     *
     *
     */
    @GET
    @Path("sell")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Sell(@QueryParam("stockTransactionId") int stockTransactionId, @QueryParam("qty") int qty, @QueryParam("sellingPrice") double sellingPrice, @QueryParam("turnId") int turnId, @QueryParam("bankAccoundId") int bankAccoundId) {
        StockTransactionRepository r = new StockTransactionRepository();
        r.SellItem(stockTransactionId, qty, sellingPrice, turnId, bankAccoundId);
        return Response.ok().header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("buy")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Buy(@QueryParam("stockId") int stockId, @QueryParam("qty") int qty, @QueryParam("turnId") int turnId, @QueryParam("bankAccoundId") int bankAccoundId) {
        StockTransactionRepository r = new StockTransactionRepository();
        r.BuyItem(qty, stockId, turnId, bankAccoundId);
        return Response.ok().header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("History")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetHistory(@QueryParam("roundId") int roundId, @QueryParam("bankAccoundId") int bankAccoundId) {
        StockTransactionRepository r = new StockTransactionRepository();
        return Response.ok(r.GetHistory(bankAccoundId, roundId), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/GetSoldItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSoldItem(@QueryParam("bankId") int bankId, @QueryParam("roundId") int roundId) {
        StockTransactionRepository r = new StockTransactionRepository();
        return Response.ok(r.GetSellingItem(bankId, roundId), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * PUT method for updating or creating an instance of
     * StockTransactionResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
