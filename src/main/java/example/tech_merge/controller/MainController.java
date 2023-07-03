package example.tech_merge.controller;

import example.tech_merge.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("redis/save")
    public String save() {
        try {
            mainService.save();
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("fail")) {
                return "fail";
            }
        }
        return "success";
    }
}
