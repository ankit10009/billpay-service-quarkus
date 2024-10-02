package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.example.service.BillPayService;
import org.example.model.PaymentRequest;

@Path("/billpay")
@Produces("application/json")
@Consumes("application/json")
public class BillPayResource {

    @Inject
    BillPayService billPayService;

    @POST
    @Path("/payments")
    public Response makePayment(PaymentRequest paymentRequest) {
        return Response.ok(billPayService.makePayment(paymentRequest.getPayeeId(), paymentRequest.getAccountId(), paymentRequest.getAmount())).status(201).build();
    }

    @GET
    @Path("/payments/history")
    public Response getPaymentHistory() {
        return Response.ok(billPayService.getPaymentHistory()).build();
    }
}

