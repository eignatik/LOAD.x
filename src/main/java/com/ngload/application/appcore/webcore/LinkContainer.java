package com.ngload.application.appcore.webcore;

import com.ngload.application.appcore.entities.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to containing links from Parser
 * @Extendable
 */
public class LinkContainer {
    private static final Logger logger = LogManager.getLogger(LinkContainer.class.getName());
    private static Map<String, Page> pageList = new HashMap<>();

    @Deprecated
    public static void addPage(String URL, Page page) {
        try {
            pageList.put(URL, page);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    @Deprecated
    public static boolean isPageExist(Page page) {
        return pageList.containsValue(page);
    }

    @Deprecated
    public static Page getPageByKey(String key) {
        return pageList.get(key);
    }

    @Deprecated
    public static Map<String, Page> getPageList() {
        return pageList;
    }
}
