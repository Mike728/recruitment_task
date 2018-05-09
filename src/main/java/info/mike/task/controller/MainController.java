package info.mike.task.controller;

import info.mike.task.domain.CurrencyJson;
import info.mike.task.domain.Input;
import info.mike.task.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private final RestService restService;

    private Input input;

    public MainController(RestService restService) {
        this.restService = restService;
    }

    @RequestMapping({"","/","mainView"})
    public String mainView(Model model) {
        model.addAttribute("input", new Input());
        return "mainView";
    }

    @PostMapping("/process")
    public String process(@ModelAttribute("input") Input input, Model model) {
        this.input = input;
        return "redirect:/info";
    }

    @RequestMapping("/info")
    public String infoView(Model model) {
        CurrencyJson currencyJson = restService.getObject(input);
        model.addAttribute("average", restService.getAverageBuyingRate(currencyJson));
        model.addAttribute("std_dev", restService.getStandardDeviationOfSalesRates(currencyJson));
        return "infoView";
    }

    @ExceptionHandler(RestClientException.class)
    public ModelAndView handleNotFoundException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }


}
