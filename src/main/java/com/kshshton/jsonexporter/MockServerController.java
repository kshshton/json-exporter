package com.kshshton.jsonexporter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ServerController {

    @GetMapping("/one")
    public Map<String, Object>  one() {
        Map.of(
                "message", "Hello World!",
                "status", "ok"
        );
    }

    @GetMapping("/two")
    public Map<String, Object>  one() {
        Map.of(
                "message", "Hello World!",
                "status", "ok"
        );
    }
}
