package org.loadx.application.constants;

public enum LoadTaskFields {
    BASE_URL("baseUrl", true),
    LOADING_TIME("loadingTime", true),
    PARALLEL_THRESHOLD("parallelRequestsThreshold", false),
    PORT("basePort", false),
    REQUESTS("requests", true);

    private String value;
    private boolean required;

    LoadTaskFields(String value, boolean required) {
        this.value = value;
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public boolean isRequired() {
        return required;
    }
}
