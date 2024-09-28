package org.example;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient
public interface PayeeServiceClient {

    @GET
    @Path("/payees/{payeeId}")
    Payee getPayeeById(@PathParam("payeeId") Long payeeId);
}


