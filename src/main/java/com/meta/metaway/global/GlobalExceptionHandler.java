package com.meta.metaway.global;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public String handleNotFound(HttpServletRequest request, Model model) {
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", "The requested resource was not found on this server.");
        return "error/404";
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String handleForbidden(HttpServletRequest request, Model model) {
        model.addAttribute("error", "Forbidden");
        model.addAttribute("message", "You don't have permission to access this resource.");
        return "error/403";
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("error", "Internal Server Error");
        modelAndView.addObject("message", "Something went wrong on the server.");
        modelAndView.addObject("exception", ex);

        return modelAndView;
    }
}
