package org.example.proxy;
import jakarta.ws.rs.PUT;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.example.model.Account;

import java.math.BigDecimal;

@RegisterRestClient(configKey="account-service")
public interface AccountServiceClient {

    @GET
    @Path("/accounts/{accountId}")
    Account getAccountById(@PathParam("accountId") Long accountId);

    @PUT
    @Path("/accounts/{accountId}/balance")
    void updateAccountBalance(@PathParam("accountId") Long accountId, BigDecimal newBalance);
}
