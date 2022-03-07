package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

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
            // From the <script> tag
            String jsonString = String.valueOf(product.select("script"));
            jsonString = jsonString.replace("<script type=\"application/ld+json\">", "");
            jsonString = jsonString.replace("</script>","");

            JSONObject jsonProduct = new JSONObject(jsonString);

            // Some additional product info in the html
            Element productInfo = product.select("a").get(0);
            String htmlString = productInfo.select("span").toString();
            String lines[] = htmlString.split("\\r?\\n");

            JSONObject nestedJsonObject = new JSONObject();

            if (lines.length == 3) {
                // Line 0 is brand
                String brand = htmlSpanToJson(lines, 0);

                // Line 1 is item name
                String item = htmlSpanToJson(lines, 1);

                // Line 2 is price
                String price = htmlSpanToJson(lines, 2);

                nestedJsonObject.put("scraped-brand", brand);
                nestedJsonObject.put("scraped-item-name", item);
                nestedJsonObject.put("scraped-price", parseInt(price));
                jsonProduct.put("scraped", nestedJsonObject);
            }

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

    public void parseJson() throws IOException, InterruptedException {
        Parser parser = new Parser();

        String jsonData = parser.readFile("ssense.json");
        JSONObject obj = new JSONObject(jsonData);
        JSONArray jsonArray = obj.getJSONArray("products");

        String BASE_URL = "https://www.ssense.com/en-ca";

//        for (int i = 0; i < jsonArray.length(); i++) {
        for (int i = 0; i < 4; i++) {
            JSONObject explorObject = jsonArray.getJSONObject(i);

            // All the image links in the JSON file
            String brand_name = explorObject.getJSONObject("scraped").getString("scraped-brand");
            String item_name  = explorObject.getJSONObject("scraped").getString("scraped-item-name");
            int price         = explorObject.getJSONObject("scraped").getInt("scraped-price");
            String product_id = explorObject.getString("productID");
            String img_url    = explorObject.getString("image");
            String item_url   = BASE_URL + explorObject.getString("url");

            System.out.println("Brand: " + brand_name);
            System.out.println("Item: " + item_name);
            System.out.println("Price: " + price);
            System.out.println("ProductID: " + product_id);
            System.out.println("Image: " + img_url);
            System.out.println("URL: " + item_url);
            System.out.println("================================");

            parser.saveImage(img_url, item_name + "_" + product_id + ".jpg");

            // Pause for 2 seconds
            Thread.sleep(2000);
        }
    }

//    public void saveImage() throws IOException {
//        String imageUrl = "https://img.ssensemedia.com/images/221735M191001_1/craig-green-undefined.jpg";
//        String destinationFile = "image.jpg";
//
//        saveImage(imageUrl, destinationFile);
//    }

    public void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    private String htmlSpanToJson(String lines[], int index) {
        String line;
        line = lines[index].substring(lines[index].indexOf(">")+1, lines[index].indexOf("</span>"));
        line = line.trim();
        line = line.replace("$", "");
        return line;
    }

    public void compress() throws IOException {
        // SOURCE: https://stackoverflow.com/questions/44565500/how-can-i-compress-images-using-java
        File input = new File("Green Knot Jumper_8893741.jpg");
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File("compressed_image.jpg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.45f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }
}
