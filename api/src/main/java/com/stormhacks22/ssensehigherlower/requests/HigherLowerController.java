package com.stormhacks22.ssensehigherlower.requests;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/test")
@CrossOrigin("*")
public class HigherLowerController {

    private final HigherLowerService higherLowerService;

    @Autowired
    public HigherLowerController(HigherLowerService higherLowerService) {
        this.higherLowerService = higherLowerService;
    }

    @GetMapping
    public List<S3ObjectSummary> getBucketItems() {
        return higherLowerService.getBucketItems();
    }

    @GetMapping("{imageKey}/image/download")
    public byte[] downloadImage(@PathVariable("imageKey") String imageKey) {
        return higherLowerService.downloadImage(imageKey);
    }

}
