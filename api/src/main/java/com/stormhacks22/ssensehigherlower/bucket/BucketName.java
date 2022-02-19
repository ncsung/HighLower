package com.stormhacks22.ssensehigherlower.bucket;

public enum BucketName {

    // AWS bucket name
    PROFILE_IMAGE("ssense-higher-lower");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
