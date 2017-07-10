package com.heitian.ssm.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class HomeController {
    @RequestMapping("")
    public String getIndex(Model model,
                           @RequestParam(value="langType", defaultValue="zh") String langType,
                           HttpServletRequest request) {
        HttpSession session = request.getSession();

        if(!model.containsAttribute("contentModel")){

            if(langType.equals("zh")){
                session.setAttribute("langType","zh");
                Locale locale = new Locale("zh", "CN");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else if(langType.equals("en")){
                session.setAttribute("langType","en");
                Locale locale = new Locale("en", "US");
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            }
            else {
                request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
            }
            //从后台代码获取国际化信息
            RequestContext requestContext = new RequestContext(request);
        }
        return "index2";
    }
}
