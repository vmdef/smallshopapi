package com.amtest.smallshop.api.exception;

/**
 * An enumeration of error codes and associated i18n message keys for order
 * related validation errors.
 **/
public enum ErrorCode {
    // Internal Errors: 1 to 0999
    GENERIC_ERROR("ERR-0001", "The system is unable to complete the request. Contact system support."),
    HTTP_MEDIATYPE_NOT_SUPPORTED("ERR-0002", "Requested media type is not supported. Please use application/json as 'Content-Type' header value"),
    HTTP_MESSAGE_NOT_WRITABLE("ERR-0003", "Missing 'Accept' header. Please add 'Accept' header."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE("ERR-0004", "Requested 'Accept' header value is not supported. Please use application/json as 'Accept' value"),
    JSON_PARSE_ERROR("ERR-0005", "Make sure request payload should be a valid JSON object."),
    HTTP_MESSAGE_NOT_READABLE("ERR-0006", "Make sure request payload should be a valid JSON according to 'Content-Type'."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("ERR-0007", "Request method not supported."),
    CONSTRAINT_VIOLATION("ERR-0008", "Validation failed."),
    ILLEGAL_ARGUMENT_EXCEPTION("ERR-0009", "Invalid data passed."),
    RESOURCE_NOT_FOUND("ERR-0010", "Requested resource not found."),
    CUSTOMER_NOT_FOUND("ERR-0011", "Requested customer not found."),
    ITEM_NOT_FOUND("ERR-0012", "Requested item not found."),
    GENERIC_ALREADY_EXISTS("ERR-0013", "Already exists."),
    ACCESS_DENIED("ERR-0014", "Access Denied."),
    UNAUTHORIZED("ERR-0015", "Unauthorized");

    private String errCode;
    private String errMsgKey;

    ErrorCode(final String errCode, final String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @return the errMsgKey
     */
    public String getErrMsgKey() {
        return errMsgKey;
    }

}
