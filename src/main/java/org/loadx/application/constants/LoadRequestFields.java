package org.loadx.application.constants;

public enum LoadRequestFields {
    TYPE("type", true),
    URL("url", true),
    TIMEOUT("timeout", false),
    EXPECTED_RESULT("expectedResult", false);

    protected String value;
    protected boolean required;

    LoadRequestFields(String value, boolean required) {
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
