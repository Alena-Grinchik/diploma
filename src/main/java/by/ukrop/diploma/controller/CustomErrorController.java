package by.ukrop.diploma.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger log = LogManager.getLogger(CustomErrorController.class);

    @RequestMapping(value = "${server.error.path}")
    public RedirectView handleError(HttpServletRequest request) {
        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

        if (exception == null) {
            log.warn("[ERROR]: " + status + " " + uri);
        } else {
            String message = exception.getMessage();
            log.warn("[ERROR]: " + status + " " + uri + "\n" + message);
        }

        return new RedirectView("/");
    }
}
