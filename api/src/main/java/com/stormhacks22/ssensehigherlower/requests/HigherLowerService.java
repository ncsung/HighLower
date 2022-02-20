package com.stormhacks22.ssensehigherlower.requests;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.stormhacks22.ssensehigherlower.bucket.BucketName;
import com.stormhacks22.ssensehigherlower.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HigherLowerService {

    private final FileStore fileStore;

    @Autowired
    public HigherLowerService(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    public byte[] downloadImage(String imageKey) {
//        // TODO: Figure out what the right input parameter should be
//        String path = String.format("%s/%s",
//                BucketName.PROFILE_IMAGE.getBucketName(),
//                imageKey);

        String path = BucketName.PROFILE_IMAGE.getBucketName();

        return fileStore.download(path, imageKey);
    }

    // User hits this with some information...
    public List<S3ObjectSummary> getBucketItems() {

        return fileStore.getBucketItems();
    }

    public void getBucketMetadata() {
        fileStore.getBucketMetadata();
    }
}
