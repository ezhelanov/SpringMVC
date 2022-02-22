package egor.spring.services.impl;

import egor.spring.services.EgorSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static egor.core.constants.EgorConstants.SESSION_DURATION_IN_SECONDS;
import static java.util.Objects.isNull;

@Component
public class EgorSessionServiceImpl implements EgorSessionService {

    private static final Logger LOG = LoggerFactory.getLogger(EgorSessionServiceImpl.class);

    public HttpSession getCurrentSession(HttpServletRequest request){

        HttpSession currentSession = request.getSession(false);

        if (isNull(currentSession)){
            currentSession = request.getSession();
            currentSession.setMaxInactiveInterval(SESSION_DURATION_IN_SECONDS);
            LOG.info("Создана новая сессия с id - {}", currentSession.getId());
        } else {
            LOG.info("Сессия уже существует - {}", currentSession.getId());
        }

        return currentSession;
    }


}
