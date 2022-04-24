//package com.example.grpcclient.service;
//
//import io.github.bucket4j.Bucket;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//@RequiredArgsConstructor
//public class RateLimitingService {
//
//    private final Map<UUID, Bucket> bucketCache = new ConcurrentHashMap<UUID, Bucket>();
//    private final UserPlanMappingRepository userPlanMappingRepository;
//
//    public Bucket resolveBucket(final UUID userId) {
//        return bucketCache.computeIfAbsent(userId, this::newBucket);
//    }
//
//    public void deleteIfExists(final UUID userId) {
//        bucketCache.remove(userId);
//    }
//
//    private Bucket newBucket(UUID userId) {
//        final var plan = userPlanMappingRepository.findByUserIdAndIsActive(userId, true).get().getPlan();
//        final Integer limitPerHour = plan.getLimitPerHour();
//        return Bucket4j.builder()
//                .addLimit(Bandwidth.classic(limitPerHour, Refill.intervally(limitPerHour, Duration.ofHours(1))))
//                .build();
//    }
//}
