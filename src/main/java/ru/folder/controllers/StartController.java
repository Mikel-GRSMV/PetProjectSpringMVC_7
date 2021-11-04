package ru.folder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartController {
    @RequestMapping(path = "/startPage", method = RequestMethod.GET)
    public ModelAndView getStartPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("startPage");
        return modelAndView;
    }
}
