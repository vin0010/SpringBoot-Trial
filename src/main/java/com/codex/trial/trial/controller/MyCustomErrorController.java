package com.codex.trial.trial.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codex.trial.trial.model.Book;

@Controller
public class MyCustomErrorController implements ErrorController {

	@RequestMapping("/error")
	@ResponseBody
	public Book handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		Book b = new Book();
		b.setAuthor("vinoth");
		b.setId(0);
		b.setTitle("til");
		return b;
//		return String.format(
//				"<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
//						+ "<div>Exception Message: <b>%s</b></div><body></html>",
//				statusCode, exception == null ? "N/A" : exception.getMessage());
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}