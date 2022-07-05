package com.nttdata.creditservice.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final String OPENING_CURLY_BRACKET = "{";
    public static final String CLOSING_CURLY_BRACKET = "}";
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
    public static final String PERSONAL_CUSTOMER_ID = "personalCustomerId";
    public static final String BUSINESS_CUSTOMER_ID = "businessCustomerId";

    // Exception messages
    public static final String CREDIT_NOT_FOUND = "Credit not found with %s: %s";
    public static final String NULL_REQUEST = "The request is null";
    public static final String VIOLATED_CONSTRAINTS = "Restrictions were violated";
    public static final String NOT_NULL = "Must not be null";
    public static final String CUSTOMER_ID_IS_REQUIRED = "personalCustomerId or businessCustomerId is required";
    public static final String CREDIT_LINE_IS_LESS_THAN_ZERO = "Credit line is less than 0";
    public static final String CONSUMPTION_IS_LESS_THAN_ZERO = "Consumption is less than 0";
    public static final String CREDIT_DUPLICATED_BY_A_FIELD = "There is already a bank account with %s: %s";
    public static final String CREDIT_DUPLICATED_BY_TWO_FIELDS = "There is already a bank account with %s: %s or %s: %s";
    public static final String UTILITY_CLASS = "Utility class";

    public static final String ERROR_RESPONSE_IN_SERVICE = "%s -> %s";

    // Collections
    public static final String CREDITS_COLLECTION = "credits";
    public static final String CREDIT_CARDS_COLLECTION = "creditCards";

    // Controller paths
    public static final String CREDIT_CONTROLLER = "${controller.credit.base-path}";
    public static final String CREDIT_CARD_CONTROLLER = "${controller.credit-card.base-path}";

    // Method paths
    public static final String GET_ALL_METHOD = "${controller.method.get-all}";
    public static final String GET_BY_ID_METHOD = "${controller.method.get-by-id}";
    public static final String REGISTER_METHOD = "${controller.method.register}";
    public static final String UPDATE_BY_ID_METHOD = "${controller.method.update-by-id}";
    public static final String DELETE_BY_ID_METHOD = "${controller.method.delete-by-id}";
    public static final String REGISTER_PERSONAL_CREDIT_METHOD = "${controller.credit.method.register-personal}";
    public static final String REGISTER_BUSINESS_CREDIT_METHOD = "${controller.credit.method.register-business}";
    public static final String REGISTER_PERSONAL_CREDIT_CARD_METHOD = "${controller.credit-card.method.register-personal}";
    public static final String REGISTER_BUSINESS_CREDIT_CARD_METHOD = "${controller.credit-card.method.register-business}";

    // Path variables
    public static final String PATH_ID_VARIABLE = "${controller.path-variable.id}";

    // Customer service
    public static final String CUSTOMER_SERVICE_BASE_URL = "${customer-service.base-url}";
    public static final String GET_PERSONAL_CUSTOMER_BY_ID_METHOD = "${customer-service.method.get-personal-customer-by-id}";
    public static final String GET_BUSINESS_CUSTOMER_BY_ID_METHOD = "${customer-service.method.get-business-customer-by-id}";

}
