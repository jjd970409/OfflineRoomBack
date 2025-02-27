package com.escape.offlineroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.escape.offlineroom.entity.Review;
import com.escape.offlineroom.service.ReviewService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        // 하드코딩된 리뷰 데이터 생성
        Review review1 = new Review();
        review1.setContent("하드코딩 리뷰 1");
        // 필요한 다른 속성 설정

        Review review2 = new Review();
        review2.setContent("하드코딩 리뷰 2");
        // 필요한 다른 속성 설정

        // 리뷰 목록 반환
        return Arrays.asList(review1, review2);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }
}
