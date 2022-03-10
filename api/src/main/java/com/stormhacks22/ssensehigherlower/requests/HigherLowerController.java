package com.stormhacks22.ssensehigherlower.requests;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/items")
@CrossOrigin("*")
public class HigherLowerController {

    private final HigherLowerService higherLowerService;

    @Autowired
    public HigherLowerController(HigherLowerService higherLowerService) {
        this.higherLowerService = higherLowerService;
    }

    @GetMapping
    public Map<String, Map<String, String>> getBucketMetadata() {
        return higherLowerService.getBucketMetadata();
    }

    @GetMapping("/summary")
    public List<S3ObjectSummary> getBucketItems() {
        return higherLowerService.getBucketItems();
    }

    @GetMapping("{imageKey}/image/download")
    public byte[] downloadImage(@PathVariable("imageKey") String imageKey) {
        return higherLowerService.downloadImage(imageKey);
    }

    @PostMapping(
            path = "{product-id}/{brand}/{product-name}/{price}/image/upload"
    )
    public void uploadProduct(@PathVariable("product-id") String productId,
                              @PathVariable("brand") String brand,
                              @PathVariable("product-name") String productName,
                              @PathVariable("price") int price,
                              @RequestParam("file") MultipartFile file) {
        System.out.println("=============");
        System.out.println(productId);
        System.out.println(brand);
        System.out.println(productName);
        System.out.println(price);
        System.out.println(file.getContentType());
        System.out.println("=============");
        higherLowerService.uploadProduct(productId,brand, productName, price, file);
    }


}
