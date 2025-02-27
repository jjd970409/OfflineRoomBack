package com.escape.offlineroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escape.offlineroom.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
