package cc.sandkasten.spring.receiver.controller;

import cc.sandkasten.spring.receiver.domain.KeyCounter;
import cc.sandkasten.spring.receiver.repository.ApiRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.Set;

@Controller
@Slf4j
public class ApiController {
    private static final Gson GSON = new Gson();

    private final ApiRepository repository;

    @Autowired
    public ApiController(ApiRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/increment")
    @ResponseBody
    public String increment(@RequestParam String key, @RequestHeader(value = "User-Agent") String userAgent) {
        log.info("request to increment key {}, User-Agent: {}", key, userAgent);
        final int currentCount = repository.incrementAndGetCount(key, userAgent);
        final KeyCounter KeyCounter = new KeyCounter(key, currentCount);
        return GSON.toJson(KeyCounter);
    }

    @GetMapping("/api/get")
    @ResponseBody
    public String getKey(@RequestParam String key, @RequestHeader(value = "User-Agent") String userAgent) {
        log.info("request to get key {}, User-Agent: {}", key, userAgent);
        final Optional<Integer> currentCount = repository.get(key);
        final KeyCounter KeyCounter = new KeyCounter(key, currentCount.orElse(0));
        return GSON.toJson(KeyCounter);
    }

    @GetMapping("/api/all")
    @ResponseBody
    public String getAllKeys(@RequestHeader(value = "User-Agent") String userAgent) {
        log.info("request to get all keys, User-Agent: {}", userAgent);
        final Set<KeyCounter> allCounter = repository.getAll();
        return GSON.toJson(allCounter);
    }

    @GetMapping("/api/reset")
    @ResponseBody
    public String reset(@RequestHeader(value = "User-Agent") String userAgent) {
        log.info("request to reset all keys, User-Agent: {}", userAgent);
        repository.reset();
        return "done";
    }
}
