package com.erupt.interceptor;

import com.erupt.base.model.EruptModel;
import com.erupt.base.model.HttpStatus;
import com.erupt.constant.SessionKey;
import com.erupt.service.CoreService;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liyuepeng on 12/5/18.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final String ERUPT_HEADER_KEY = "eruptKey";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String eruptKey = request.getHeader(ERUPT_HEADER_KEY);
        EruptModel eruptModel = CoreService.ERUPTS.get(eruptKey);
        if (null == eruptModel) {
            return true;
        } else if (eruptModel.getErupt().loginUse()) {
            if (null != request.getSession().getAttribute(SessionKey.IS_LOGIN)) {
                return true;
            } else {
//                response.setStatus(HttpStatus.NO_LOGIN.code);
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}