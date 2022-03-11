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
import java.util.*;

@Service
public class FileStore {

    private final AmazonS3 s3;
    private static Map<String, Map<String, String>> productMap;

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    /**
     * Download a specific object from the bucket as a byte array (image)
     *
     * @param bucketName - s3 bucket name
     * @param key - {s3-image-file-key}/image/download
     * @return the image from s3
     */
    public byte[] download(String bucketName, String key) {
        try {
            S3Object object = s3.getObject(bucketName, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download file from s3", e);
        }
    }

    /**
     * Get a summary of all bucket items
     *
     * @param bucketName - s3 bucket name
     * @return a list of all items in the s3 bucket
     */
    public List<S3ObjectSummary> getBucketItems(String bucketName) {
        ObjectListing listing = s3.listObjects(bucketName);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();


        while (listing.isTruncated()) {
            listing = s3.listNextBatchOfObjects (listing);
            summaries.addAll (listing.getObjectSummaries());
        }

        return summaries;
    }

    /**
     * Get the metadata of all bucket items
     *
     * @param bucketName - s3 bucket name
     * @return a JSON-like nested structure with items and their metadata
     */
    public Map<String, Map<String, String>> getBucketMetadata(String bucketName) {
        // TODO: figure out a better way to get a random image
        // Imitates a JSON nested structure
        //  String: filename
        //      Map<String, String>: key (brand/item-name/price), value pairs
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName);
        ObjectListing objectListing;
        do {
            objectListing = s3.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary
                    : objectListing.getObjectSummaries()) {

                String imageKey = objectSummary.getKey();

                /** To get user defined metadata **/
                ObjectMetadata objectMetadata = s3.getObjectMetadata(BucketName.PROFILE_IMAGE.getBucketName(), imageKey);
                Map userMetadataMap = objectMetadata.getUserMetadata(); // Brand, item-name, price
                map.put(imageKey, userMetadataMap);

            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());

        // Allocate a static productMap that gets generated once, and then use this to get a random key
        productMap = map;

        return map;
    }

    /**
     *
     * @param filepath
     * @param filename
     * @param optionalMetadata
     * @param inputStream
     */
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
}
