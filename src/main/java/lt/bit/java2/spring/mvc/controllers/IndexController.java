package lt.bit.java2.spring.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class IndexController {
	@GetMapping
	String index() {
		return "index";
	}
}
