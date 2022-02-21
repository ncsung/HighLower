package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Parser {

    public void scrape() throws IOException {
        final String url =
                "https://www.ssense.com/en-ca/men/clothing";

        final Document document = (Document) Jsoup
                .connect(url)
                .header("Host", "www.ssense.com")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-CA,en-US;q=0.7,en;q=0.3")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "cross-site")
                .timeout(6000)
                .get();

        BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ssense.html"), "UTF-8"));
        System.out.println("\n" + document.outerHtml());
        htmlWriter.write(document.toString());
        htmlWriter.flush();
        htmlWriter.close();
    }

    public void htmlToJson() throws IOException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        File input = new File("ssense.html");
        Document document = Jsoup.parse(input, "UTF-8");

        Elements products = document.getElementsByClass("plp-products__column");

        for (Element product: products) {
            String jsonString = String.valueOf(product.select("script"));
            jsonString = jsonString.replace("<script type=\"application/ld+json\">", "");
            jsonString = jsonString.replace("</script>","");

            JSONObject jsonProduct = new JSONObject(jsonString);
            jsonArray.put(jsonProduct);

        }

        jsonObject.put("products", jsonArray);
        System.out.println(jsonObject.toString());

        BufferedWriter jsonWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ssense.json"), "UTF-8"));
        jsonWriter.write(jsonObject.toString());
        jsonWriter.flush();
        jsonWriter.close();
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    public void parseJson() throws IOException {
        Parser parser = new Parser();

        String jsonData = parser.readFile("ssense.json");
        JSONObject obj = new JSONObject(jsonData);
        JSONArray jsonArray = obj.getJSONArray("products");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explorObject = jsonArray.getJSONObject(i);

            // All the image links in the JSON file
            System.out.println(explorObject.getString("image"));
        }
    }
}
