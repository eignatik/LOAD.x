package org.loadx.application.http;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebsiteValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WebsiteValidationUtil.class);

    private WebsiteValidationUtil() {
        // private constructor for util class
    }

    public static String generateHash(String baseUrl) {
        String hash = DigestUtils.md5Hex(baseUrl + baseUrl.length());
        LOG.info("Generating hash={} for baseUrl={}", hash, baseUrl);
        return hash;
    }

}
