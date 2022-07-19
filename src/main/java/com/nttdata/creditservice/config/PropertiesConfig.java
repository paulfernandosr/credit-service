package com.nttdata.creditservice.config;

import com.nttdata.creditservice.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @Value(Constants.CUSTOMER_SERVICE_BASE_URL)
    public String customerServiceBaseUrl;

    @Value(Constants.GET_CUSTOMER_BY_ID_METHOD)
    public String getCustomerByIdMethod;

    @Value(Constants.MOVEMENT_SERVICE_BASE_URL)
    public String movementServiceBaseUrl;

    @Value(Constants.GET_MOVEMENTS_BY_CREDIT_ID_METHOD)
    public String getMovementsByCreditId;

}
