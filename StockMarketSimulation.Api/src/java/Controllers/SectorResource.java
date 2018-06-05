/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Repositories.SectorRepository;
import Repositories.StockTransactionRepository;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author lakshan
 */
@Path("Sector")
public class SectorResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SectorResource
     */
    public SectorResource() {
    }

    /**
     * Retrieves representation of an instance of Controllers.SectorResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        SectorRepository sr = new SectorRepository();
        return Response.ok(sr.GetAllSectors(), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }


    /**
     * PUT method for updating or creating an instance of SectorResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
