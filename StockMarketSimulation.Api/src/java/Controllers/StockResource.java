/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Repositories.SectorRepository;
import Repositories.StockRepository;
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
@Path("Stock")
public class StockResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StockResource
     */
    public StockResource() {
    }

    /**
     * Retrieves representation of an instance of Controllers.StockResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("/GetBySectorId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySectorId(@QueryParam("sectorId") int sectorId) {
        StockRepository sr = new StockRepository();
        return Response.ok(sr.GetBySectorId(sectorId), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("getDataForPredicate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetDataForPredicate(@QueryParam("sectorId") int sectorId) {
        StockRepository sr = new StockRepository();
        return Response.ok(sr.GetDataForPredicate(), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("getValueChangeForYears")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ValueChangeForYears(@QueryParam("sectorId") int sectorId) {
        StockRepository sr = new StockRepository();
        return Response.ok(sr.ValueChangeForYears()).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * PUT method for updating or creating an instance of StockResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
