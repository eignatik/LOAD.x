package com.ngload;

import com.ngload.application.appcore.webcore.WebConnector;

public class Main {
    public static void main(String[] args) {
        WebConnector connector = new WebConnector("http://yandex.ru");
        String html = connector.getHtmlByURL("");
        System.out.println(html);
    }
}
