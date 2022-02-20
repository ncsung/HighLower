package com.stormhacks22.ssensehigherlower.requests;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.stormhacks22.ssensehigherlower.bucket.BucketName;
import com.stormhacks22.ssensehigherlower.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
}
