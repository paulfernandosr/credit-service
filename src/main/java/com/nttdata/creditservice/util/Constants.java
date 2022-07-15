package com.nttdata.creditservice.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final String FIVE_CURLY_BRACKETS = "{}{}{}{}{}";
    public static final String SLASH = "/";
    public static final String COLON = ": ";
    public static final String IN = " in ";

    // Keys
    public static final String TIMESTAMP = "timestamp";
    public static final String REQUEST_ID = "requestId";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String VALIDATIONS = "validations";
    public static final String ID = "id";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String CUSTOMER_ID = "customerId";

    // Exception messages
    public static final String TIMEOUT_EXCEPTION = "Timeout greater than 2 seconds";
    public static final String CREDIT_NOT_FOUND = "Credit not found with %s: %s";
    public static final String NULL_REQUEST = "The request is null";
    public static final String VIOLATED_CONSTRAINTS = "Restrictions were violated";
    public static final String NOT_NULL = "Must not be null";
    public static final String LESS_THAN_ZERO = "Must not be less than zero";
    public static final String INVALID_CUSTOMER_TYPE = "Invalid type for customer with %s: %s";
    public static final String CREDIT_DUPLICATED_BY_A_FIELD = "There is already a credit with %s: %s";
    public static final String CREDIT_DUPLICATED_BY_TWO_FIELDS = "There is already a credit with %s: %s or %s: %s";
    public static final String CREDIT_CARD_DUPLICATED_BY_A_FIELD = "There is already a credit card with %s: %s";
    public static final String CREDIT_CARD_DUPLICATED_BY_TWO_FIELDS = "There is already a credit card with %s: %s or %s: %s";
    public static final String UTILITY_CLASS = "Utility class";
    public static final String ERROR_RESPONSE_IN_SERVICE = "%s -> %s";


    // Collections
    public static final String CREDITS_COLLECTION = "credits";

    // Controller paths
    public static final String CREDIT_CONTROLLER = "/credits";

    // Method paths
    public static final String GET_ALL_METHOD = "/all";
    public static final String GET_BY_ID_METHOD = "/{" + ID + "}";
    public static final String GET_BY_CUSTOMER_ID_METHOD = "/customers/{" + CUSTOMER_ID + "}";
    public static final String REGISTER_PERSONAL_CREDIT_METHOD = "/personal-credits";
    public static final String REGISTER_BUSINESS_CREDIT_METHOD = "/business-credits";
    public static final String REGISTER_PERSONAL_CREDIT_CARD_METHOD = "/personal-credit-cards";
    public static final String REGISTER_BUSINESS_CREDIT_CARD_METHOD = "/business-credit-cards";
    public static final String UPDATE_BY_ID_METHOD = "/{" + ID + "}";
    public static final String DELETE_BY_ID_METHOD = "/{" + ID + "}";

    // Types
    public static final String PERSONAL_CREDIT = "CREDIT.PERSONAL";
    public static final String BUSINESS_CREDIT = "CREDIT.BUSINESS";
    public static final String PERSONAL_CREDIT_CARD = "CREDIT_CARD.PERSONAL";
    public static final String BUSINESS_CREDIT_CARD = "CREDIT_CARD.BUSINESS";
    public static final String PERSONAL_CUSTOMER = "PERSONAL";
    public static final String BUSINESS_CUSTOMER = "BUSINESS";

    // Customer service
    public static final String CUSTOMER_SERVICE_BASE_URL = "${customerService.baseUrl}";
    public static final String GET_CUSTOMER_BY_ID_METHOD = "${customerService.method.getCustomerById}";

}
