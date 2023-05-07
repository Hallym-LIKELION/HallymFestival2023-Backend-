package com.hallym.festival.global.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
@Controller
public class WebController implements ErrorController {

    @GetMapping("/error")
    public String index(HttpServletResponse response) {
        response.setStatus(200);
        return "index.html";
    }
}
