package com.newtouch.commons.springmvc;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FastjsonExceptionHandler implements HandlerExceptionResolver {

    public static final Logger LOGGER = LoggerFactory.getLogger(FastjsonExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
            /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        view.addStaticAttribute("error", ex.getClass().getSimpleName());
        //view.addStaticAttribute("message", ex.getMessage());
        mv.setView(view);
        LOGGER.error("JSON异常", ex);
        return mv;
    }
}