package com.stormhacks22.ssensehigherlower.requests;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.stormhacks22.ssensehigherlower.bucket.BucketName;
import com.stormhacks22.ssensehigherlower.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class HigherLowerService {

    private final FileStore fileStore;

    @Autowired
    public HigherLowerService(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    // The key/filename is provided to the frontend via a GET request, and when the frontend needs the associated picture, it gives it back
    public byte[] downloadImage(String imageKey) {
        String path = BucketName.PROFILE_IMAGE.getBucketName();

        return fileStore.download(path, imageKey);
    }

    public List<S3ObjectSummary> getBucketItems() {
        return fileStore.getBucketItems(BucketName.PROFILE_IMAGE.getBucketName());
    }

    public Map<String, Map<String, String>> getBucketMetadata() {
        return fileStore.getBucketMetadata(BucketName.PROFILE_IMAGE.getBucketName());
    }

    public void uploadProduct(String productId, String brand, String productName, int price, MultipartFile file) {
        // 1. Check if image is not empty
        isFileEmpty(file);

        // 2. If file is an image
        isImage(file);

        // 3. Metadata to associate with the item
        Map<String, String> metadata = extractMetadata(productId, brand, productName, price, file);

        // 4. Store the image in s3
        String path = BucketName.PROFILE_IMAGE.getBucketName();
        String filename = file.getOriginalFilename();

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }


    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
        }
    }

    private Map<String, String> extractMetadata(String productId, String brand, String productName, int price, MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("product-id", productId);
        metadata.put("brand", brand);
        metadata.put("item-name", productName);
        metadata.put("price", String.valueOf(price));
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }
}
