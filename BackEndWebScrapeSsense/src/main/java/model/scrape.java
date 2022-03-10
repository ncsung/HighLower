package model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class scrape {

    public static void main(String[] args) throws IOException, InterruptedException {

        Parser parser = new Parser();

        // Parse JSON
        String jsonData = readFile("ssense.json");
        JSONObject obj = new JSONObject(jsonData);
        JSONArray jsonArray = obj.getJSONArray("products");

        String BASE_URL = "https://www.ssense.com/en-ca";

        int i = 9;
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

        // saveImage
        URL url = new URL(img_url);
        InputStream is = url.openStream();

        // compressAndPost
        BufferedImage image = ImageIO.read(is); // This can read an input stream (without needing to write/read from disk?)

        // Compress file
        BufferedImage scaledImage = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY,image.getWidth(), image.getHeight());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String filename = item_name + "_" + product_id + ".jpg";
            String api = "http://localhost:8080/api/items/"+ product_id + "/" + brand_name +"/" + item_name +"/"+price+"/image/upload";
            URI uri = new URI(api.replace(" ", "%20"));
            HttpPost httpPost = new HttpPost(uri);
            ContentBody cbFile = new ByteArrayBody(bytes, ContentType.create("image/jpeg"),filename);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", cbFile);

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            System.out.println("executing request " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine());

        } catch (IOException e) {

            // handle

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }


    public static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}

