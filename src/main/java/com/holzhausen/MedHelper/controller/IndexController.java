package com.holzhausen.MedHelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping(value = "/")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }

}
