package cc.sandkasten.spring.receiver.controller;

import cc.sandkasten.spring.receiver.domain.KeyCounter;
import cc.sandkasten.spring.receiver.repository.ApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@Slf4j
public class WebController {
    private final ApiRepository repository;

    @Autowired
    public WebController(ApiRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/web/all")
    public String getAllCounter(Model model, @RequestHeader(value = "User-Agent") String userAgent) {
        log.info("web request to get all keys, User-Agent: {}", userAgent);
        final Set<KeyCounter> allCounter = repository.getAll();
        model.addAttribute("keys", allCounter);
        model.addAttribute("statistics", repository.getStatistics());
        model.addAttribute("total", allCounter.stream().mapToInt(KeyCounter::getCount).sum());
        return "all";
    }

    @GetMapping("/web/allByCounter")
    public String getAllByCounter(Model model, @RequestHeader(value = "User-Agent") String userAgent) {
        log.info("web request to get all keys sorted by counter, User-Agent: {}", userAgent);
        final List<KeyCounter> allCounter = repository.getAllSortedByCounter();
        model.addAttribute("keys", allCounter);
        model.addAttribute("statistics", repository.getStatistics());
        model.addAttribute("total", allCounter.stream().mapToInt(KeyCounter::getCount).sum());
        return "all";
    }

    @GetMapping("/web/allByKey")
    public String getAllByKey(Model model, @RequestHeader(value = "User-Agent") String userAgent) {
        log.info("web request to get all keys sorted by key, User-Agent: {}", userAgent);
        final List<KeyCounter> allCounter = repository.getAllSortedByKey();
        model.addAttribute("keys", allCounter);
        model.addAttribute("statistics", repository.getStatistics());
        model.addAttribute("total", allCounter.stream().mapToInt(KeyCounter::getCount).sum());
        return "all";
    }

    @PostMapping("/web/increment")
    public String increment(@RequestParam String key, Model model, @RequestHeader(value = "User-Agent") String userAgent) {
        log.info("web request to increment key {}, User-Agent: {}", key, userAgent);
        repository.incrementAndGetCount(key, userAgent);
        final List<KeyCounter> allCounter = repository.getAllSortedByKey();
        model.addAttribute("keys", allCounter);
        model.addAttribute("statistics", repository.getStatistics());
        model.addAttribute("total", allCounter.stream().mapToInt(KeyCounter::getCount).sum());
        return "all";
    }

    @GetMapping("/web/reset")
    public String reset(@RequestHeader(value = "User-Agent") String userAgent) {
        log.info("web request to reset all keys, User-Agent: {}", userAgent);
        repository.reset();
        return "all";
    }
}
