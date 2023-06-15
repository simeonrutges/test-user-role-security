package nl.novi.automate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUpLoadWithFrontEndCode {
    @GetMapping("/files")
    ModelAndView fileUpload(){
        return new ModelAndView("index.html");
    }
//    de fileUpload() methode zouden worden gebruikt in een traditionele server-side gerenderde webapplicatie waarin de server
//    verantwoordelijk is voor het genereren van de HTML die naar de client wordt gestuurd. als je een React-applicatie gebruikt,
//    heb je de FileUpLoadWithFrontEndCode klasse waarschijnlijk niet nodig, omdat het uploadformulier en de uploadfunctionaliteit
//    aan de client-zijde worden afgehandeld door de React-code.
}
