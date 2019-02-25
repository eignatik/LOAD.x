package org.loadx.application.http;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.loadx.application.constants.CommonConstants;
import org.loadx.application.exceptions.LoadxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class that provides methods for the web sites ownership validation.
 *
 * Sites always have to be checked before starting the load  testing so it could prevent unexpected DDoS attacks by the
 * load testing tool.
 */
public final class WebsiteValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WebsiteValidationUtil.class);

    private WebsiteValidationUtil() {
        // private constructor for util class
    }

    /**
     * Generates MD5 hash from given url and it's length.
     * @param url url of the website to be checked.
     * @return generated MD5 hash.
     */
    public static String generateHash(String url) {
        String hash = DigestUtils.md5Hex(url + url.length());
        LOG.info("Generating hash={} for url={}", hash, url);
        return hash;
    }

    /**
     * Validates the given hash presence on the given HTML page.
     * @param hash generated hash to check.
     * @param page target page to check on hash presence.
     * @return boolean value of validation. true if validated.
     */
    public static boolean validate(String hash, String page) {
        if (StringUtils.isEmpty(hash)) {
            String message = "Passed hash is empty";
            LOG.error(message);
            throw new LoadxException(message);
        }
        Elements ownershipMeta = Jsoup.parse(page).getElementsByAttribute(CommonConstants.DATA_ATTR_OWNERSHIP_HASH);
        String hashFromPage = ownershipMeta.attr(CommonConstants.DATA_ATTR_OWNERSHIP_HASH);
        return hash.equals(hashFromPage);
    }

}
