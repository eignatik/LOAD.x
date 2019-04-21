package org.loadx.application.constants;

/**
 * Supported request types for loading.
 */
public enum RequestType {
    GET("GET"),
    POST("POST");

    private String value;

    RequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
