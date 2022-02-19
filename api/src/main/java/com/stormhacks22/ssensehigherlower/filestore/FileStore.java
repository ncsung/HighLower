package com.stormhacks22.ssensehigherlower.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.stormhacks22.ssensehigherlower.bucket.BucketName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    private final AmazonS3 s3;

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String filepath,
                     String filename,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {

        // User can optionally provide metadata with the file
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach((key, value) -> objectMetadata.addUserMetadata(key, value));
            }
        });

        try {
            s3.putObject(filepath, filename, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload content to s3.", e);
        }
    }

    public byte[] download(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file from s3", e);
        }
    }

    public List<S3ObjectSummary> getBucketItems() {
        ObjectListing listing = s3.listObjects(BucketName.PROFILE_IMAGE.getBucketName());
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();

        while (listing.isTruncated()) {
            listing = s3.listNextBatchOfObjects (listing);
            summaries.addAll (listing.getObjectSummaries());
        }

        return summaries;
    }
}
