/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Repositories.StockTransactionRepository;
import Repositories.TurnRepository;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author lakshan
 */
@Path("Turn")
public class TurnResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TurnResource
     */
    public TurnResource() {
    }

    /**
     * Retrieves representation of an instance of Controllers.TurnResource
     * @return an instance of java.lang.String
     */
    
    @GET
     @Path("getCurrentTurn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        TurnRepository r = new TurnRepository();
        return Response.ok(r.GetCurrentTurn()).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * PUT method for updating or creating an instance of TurnResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
