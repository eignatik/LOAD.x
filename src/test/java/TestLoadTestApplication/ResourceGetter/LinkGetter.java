package TestLoadTestApplication.ResourceGetter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class LinkGetter {
    public static final Logger logger = LogManager.getLogger(LinkGetter.class.getName());
    private Map<String, String> linkMap = new HashMap<>();
    private ClassLoader classLoader = this.getClass().getClassLoader();
    private List<String> linksList = new ArrayList<>();

    public LinkGetter() {
        fillMap();
    }

    public Object[] getLinkParam(String linkType) {
        return new Object[]{
                linkMap.get(linkType)
        };
    }

    private void fillMap() {
        getLinksJSONString(classLoader.getResource("HTTPConnection/linksjson.txt"));
        for (String str : linksList) {
            String[] elems = replaceSymbols(str.split("\": \"", 2));
            linkMap.put(elems[0], elems[1]);
        }
    }

    private List<String> getLinksJSONString(URL resource) {
        try {
            Scanner input = new Scanner(new File(resource.getFile()));
            while (input.hasNext()) {
                linksList.add(input.nextLine());
            }
            input.close();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return linksList;
    }

    private String[] replaceSymbols(String[] elems) {
        for (int i = 0; i < elems.length - 1; i++) {
            elems[i] = elems[i].replaceAll("\"*,*:*", "");
        }
        return elems;
    }

}
