package com.example.les16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//@CrossOrigin
@Controller
public class FileUpLoadWithFrontEndCode {
    @GetMapping("/files")
    ModelAndView fileUpload(){
        return new ModelAndView("index.html");
    }
}
