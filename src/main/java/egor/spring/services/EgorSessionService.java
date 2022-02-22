package egor.spring.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface EgorSessionService {

    HttpSession getCurrentSession(HttpServletRequest request);

}
