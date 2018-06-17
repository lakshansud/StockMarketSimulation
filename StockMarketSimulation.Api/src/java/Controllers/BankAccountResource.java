/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DatabaseConnection.DB;
import Models.BankAccountViewModel;
import Models.CurrentBankInfoViewModel;
import Repositories.BankAccountRepository;
import Repositories.StockTransactionRepository;
import java.sql.Connection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("bankAccount")
public class BankAccountResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BankResource
     */
    public BankAccountResource() {
    }

    /**
     * Retrieves representation of an instance of Controllers.BankResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response Create() {
        try {

            BankAccountViewModel vm = new BankAccountViewModel();

            StockTransactionRepository r = new StockTransactionRepository();

            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("maxAccountNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMaxAccountNumber() {
        try {
            BankAccountRepository r = new BankAccountRepository();
            return Response.ok(r.GetMaxAccountNumber(), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        } catch (Exception e) {
            return Response.serverError().header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(@QueryParam("username") String userName, @QueryParam("password") String password) {
        try {
            BankAccountRepository bankR = new BankAccountRepository();
            BankAccountViewModel currentUserByUserName = bankR.GetByUserName(userName);
            if (currentUserByUserName.Id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User not exist: " + userName).header("Access-Control-Allow-Origin", "*").build();
            } else {
                boolean isLogin = bankR.Login(userName, password);
                if (isLogin) {
                    return Response.ok(bankR.GetUserInfo(userName)).header("Access-Control-Allow-Origin", "*").build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("User name or Password not match").header("Access-Control-Allow-Origin", "*").build();
                }
            }

        } catch (Exception e) {
            return Response.serverError().header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Path("currentBankInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response CurrentBankInfo(@QueryParam("bankAccountId") int bankAccountId) {
        try {
            BankAccountRepository bankR = new BankAccountRepository();
            CurrentBankInfoViewModel currentBankInfoViewModel = bankR.GetCurrentBalanceInfo(bankAccountId);

            return Response.ok(currentBankInfoViewModel).header("Access-Control-Allow-Origin", "*").build();

        } catch (Exception e) {
            return Response.serverError().header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Create(@QueryParam("playerName") String playerName, @QueryParam("userName") String userName, @QueryParam("password") String password, @QueryParam("accountNumber") int accountNumber) {
        try {
            BankAccountViewModel requestBody = new BankAccountViewModel();
            requestBody.AccountNumber = accountNumber;
            requestBody.Balance = 1000;
            requestBody.PlayerName = playerName;
            requestBody.Password = password;
            requestBody.UserName = userName;

            BankAccountRepository bankR = new BankAccountRepository();
            BankAccountViewModel currentUser = bankR.GetByName(requestBody.PlayerName);
            if (currentUser.Id == 0) {
                BankAccountViewModel currentUserByUserName = bankR.GetByUserName(requestBody.UserName);
                if (currentUserByUserName.Id == 0) {
                    BankAccountViewModel bankAccountViewModel = bankR.Create(requestBody);
                    return Response.ok(bankAccountViewModel).header("Access-Control-Allow-Origin", "*").build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("User name already exsit: " + requestBody.UserName).header("Access-Control-Allow-Origin", "*").build();
                }

            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Player name already exsit: " + requestBody.PlayerName).header("Access-Control-Allow-Origin", "*").build();
            }

        } catch (Exception e) {
            return Response.serverError().header("Access-Control-Allow-Origin", "*").build();
        }
    }

    /**
     * PUT method for updating or creating an instance of BankResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
