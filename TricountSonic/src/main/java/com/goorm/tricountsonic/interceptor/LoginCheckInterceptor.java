package com.goorm.tricountsonic.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goorm.tricountsonic.service.MemberService;
import com.goorm.tricountsonic.util.MemberContext;
import com.goorm.tricountsonic.util.TricountApiConst;

public class LoginCheckInterceptor implements HandlerInterceptor {
  @Autowired
  private MemberService memberService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Cookie[] cookies = request.getCookies();

    if (!this.containsUserCookie(cookies)) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
      return false;
    }

    // 이미 로그인 되어있는 경우 -> MemberContext
    for (Cookie cookie : cookies) {
      if (TricountApiConst.LOGIN_MEMBER_COOKIE.equals(cookie.getName())) {
        try {
          MemberContext.setCurrentMember(memberService.findMemberById(Long.parseLong(cookie.getValue())));
          break;
        } catch (Exception e) {
          response.sendError(HttpServletResponse.SC_FORBIDDEN, "MEMBER INFO SET ERROR" + e.getMessage());
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    @Nullable ModelAndView modelAndView) throws Exception {
    MemberContext.clear();
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  private boolean containsUserCookie(Cookie[] cookies) {
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if(TricountApiConst.LOGIN_MEMBER_COOKIE.equals(cookie.getName())) {
          return true;
        }
      }
    }
    return false;
  }

}
