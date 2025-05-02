package ru.hpclab.hl.module1.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ObservabilityService {
    private final Map<String, ConcurrentLinkedQueue<Long>> timings = new HashMap<>();
    private final Map<String, Long> startTimes = new HashMap<>();
    private static final Logger logger = LogManager.getLogger();

    @Value("${statsInterval}")
    private int periodToStat;

    private String getMethodName() {
        return StackWalker.getInstance()
                .walk(s -> s.skip(2).findFirst())
                .get()
                .getMethodName();
    }

    public void start() {
        startTimes.put(getMethodName(), System.currentTimeMillis());
    }

    public void stop() {
        String methodName = getMethodName();
        Long startTime = startTimes.get(methodName);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            recordTiming(methodName, duration);
        }
    }

    public void recordTiming(String methodName, long durationMs) {
        timings.computeIfAbsent(methodName, k -> new ConcurrentLinkedQueue<>()).add(durationMs);
    }

    public Map<String, Object> getStatistics(String methodName, long periodMs) {
        Map<String, Object> stats = new HashMap<>();
        ConcurrentLinkedQueue<Long> queue = timings.get(methodName);
        if (queue == null || queue.isEmpty()) {
            return stats;
        }

        long[] durations = queue.stream()
                .mapToLong(Long::longValue)
                .toArray();

        long currentTime = System.currentTimeMillis();
        if (startTimes.get(methodName) == null) {
            return stats;
        } else if (startTimes.get(methodName) + periodToStat < currentTime) {
            startTimes.remove(methodName);
            return stats;
        } else if (durations.length == 0) {
            return stats;
        } else {
            stats.put("count", durations.length);
            stats.put("min", calculateMin(durations));
            stats.put("max", calculateMax(durations));
            stats.put("avg", calculateAvg(durations));

            return stats;
        }
    }

    private long calculateMin(long[] durations) {
        long min = durations[0];
        for (long d : durations) {
            if (d < min) min = d;
        }
        return min;
    }

    private long calculateMax(long[] durations) {
        long max = durations[0];
        for (long d : durations) {
            if (d > max) max = d;
        }
        return max;
    }

    private double calculateAvg(long[] durations) {
        long sum = 0;
        for (long d : durations) {
            sum += d;
        }
        return (double) sum / durations.length;
    }

    @Scheduled(fixedDelayString = "${logInterval}")
    public void logStatistics() {
        StringBuilder message = new StringBuilder();
        message.append("Stats for last calls: ");
        timings.keySet().forEach(method -> {
            Map<String, Object> methodStat = getStatistics(method, periodToStat);
            if (methodStat.isEmpty()) {
                message.append("No stats available");
            } else {
                message.append(String.format(" %s: %s;", method, methodStat));
            }
        }
        );
        logger.info(message);
    }
}