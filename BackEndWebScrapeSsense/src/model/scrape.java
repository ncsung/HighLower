package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


//public class scrape {
//
//    public static void main(String[] args) {
//
//        final String url =
//                "https://www.ssense.com/en-ca/men/clothing";
//
//        try {
//            final Document document = (Document) Jsoup.connect(url).ignoreHttpErrors(true).timeout(6000).get();
//            Elements row = document.select("div.plp-products__row");
//
//            int i = 0;
//
//            for (Element list : row) {
//                i++;
//                System.out.println((i + " " + list.getE("a").first().text()));
//            }
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

public class scrape {

    public static void main(String[] args) throws IOException {

        final String url =
                "https://www.ssense.com/en-ca/men/clothing";

//        try {
        final Document document = (Document) Jsoup.connect(url).ignoreHttpErrors(true).timeout(6000).get();
        Elements row = document.select("div.plp-products__row");

        System.out.println(row.select("div.product-tile__description").text());
    }
}



//
//
//            int i = 0;
//
//            for (Element list : row) {
//                i++;
//                System.out.println((i + " " + list.getElementsByTag("a").first().text()));
//            }
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
