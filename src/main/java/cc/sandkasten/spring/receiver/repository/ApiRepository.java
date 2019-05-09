package cc.sandkasten.spring.receiver.repository;

import cc.sandkasten.spring.receiver.domain.KeyCounter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class ApiRepository {
    private final Map<String, Integer> counterMap = new HashMap<>();
    private final Map<String, Integer> statistics = new HashMap<>();

    public void increment(String key) {
        counterMap.putIfAbsent(key, 0);
        counterMap.put(key, counterMap.get(key) + 1);
    }

    public void increment(String key, String userAgent) {
        increment(key);
        statistics.putIfAbsent(userAgent, 0);
        statistics.put(userAgent, statistics.get(userAgent) + 1);
    }

    public Integer incrementAndGetCount(String key) {
        increment(key);
        return counterMap.get(key);
    }

    public Integer incrementAndGetCount(String key, String userAgent) {
        increment(key, userAgent);
        return counterMap.get(key);
    }

    public Optional<Integer> get(String key) {
        return Optional.ofNullable(counterMap.getOrDefault(key, null));
    }

    public Set<KeyCounter> getAll() {
        return counterMap.entrySet().stream().map(e -> new KeyCounter(e.getKey(), e.getValue())).collect(toSet());
    }

    public List<KeyCounter> getAllSortedByCounter() {
        return counterMap.entrySet().stream()
                .map(e -> new KeyCounter(e.getKey(), e.getValue()))
                .sorted(comparing(KeyCounter::getCount))
                .collect(toList());
    }

    public List<KeyCounter> getAllSortedByKey() {
        return counterMap.entrySet().stream()
                .map(e -> new KeyCounter(e.getKey(), e.getValue()))
                .sorted(comparing(KeyCounter::getKey))
                .collect(toList());
    }

    public List<KeyCounter> getStatistics() {
        return this.statistics.entrySet().stream()
                .map(e -> new KeyCounter(e.getKey(), e.getValue()))
                .sorted(comparing(KeyCounter::getKey))
                .collect(toList());
    }

    public void reset() {
        counterMap.clear();
        statistics.clear();
    }
}
