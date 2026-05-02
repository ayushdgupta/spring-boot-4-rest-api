package com.guptaji.springboot_learning.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.guptaji.springboot_learning.constant.Constants.*;

@RestController
@RequestMapping(path = "/csrf/v{version}", version = "1")
public class CsrfController {

    private static final Logger LOG = LoggerFactory.getLogger(CsrfController.class);

    @GetMapping(path = "/csrfToken")
    public ResponseEntity<?> fetchCsrfToken(HttpServletRequest httpServletRequest){
        LOG.info("Extracting CSRF token");
        CsrfToken token = (CsrfToken) httpServletRequest.getAttribute(CSRF_KEY);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
