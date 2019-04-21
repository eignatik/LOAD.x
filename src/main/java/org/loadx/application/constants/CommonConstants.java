package org.loadx.application.constants;

/**
 * Common constants for cross project usage.
 */
public final class CommonConstants {

    /**
     * Metadata data-attribute name that contains MD-5 hash to confirm web-site ownership.
     */
    public static final String DATA_ATTR_OWNERSHIP_HASH = "data-ownership-loadx";
    public static final int DEFAULT_PARALLEL_THRESHOLD = 5;
    public static final int DEFAULT_LOAD_REQUEST_TIMEOUT = 15000;

    private CommonConstants() {
        // private constructor for util class
    }
}
