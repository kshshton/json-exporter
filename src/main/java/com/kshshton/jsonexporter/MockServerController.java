package com.kshshton.jsonexporter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MockServerController {

    @GetMapping("/one")
    public Map<String, Object> one() {
        return Map.of(
                "message", "One!",
                "status", "ok"
        );
    }

    @GetMapping("/two")
    public Map<String, Object> two() {
        return Map.of(
                "message", "Two!",
                "status", "ok"
        );
    }

    @GetMapping("/three")
    public Map<String, Object> three() {
        return Map.of(
                "message", "Three!",
                "status", "ok"
        );
    }
}
