package com.ssafy.cafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 새로고침 오류대응용으로 만들었음. 
 * SpringBoot에서 Vue.js를 service하고 있다.
 * Spring이므로 모든요청을 DispatcherServlet이 받는다.  
 * /comment/1 --> 1번 상품 조회처리가 되어야 하지만, 새로고침하면 comment/1 로 Spring에서 받고 404오류가 난다.
 * 오류가 발생하면 index.html로 이동시켜서, vuejs에서 처리한다.      
 * 
 */
@Controller
public class Custom404ErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(Custom404ErrorController.class);

	private final String ERROR_PATH = "/error";

	@GetMapping(ERROR_PATH)
	public String redirectRoot() {
		return "index.html";
	}

	public String getErrorPath() {
		return null;
	}
}