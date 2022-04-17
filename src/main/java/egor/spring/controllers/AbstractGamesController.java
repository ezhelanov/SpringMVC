package egor.spring.controllers;

import egor.core.errors.GameErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

public abstract class AbstractGamesController {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractGamesController.class);

    public abstract String gamesPage(Model model, boolean wasRedirectedFromDelete);

    protected String forwardToGamesPageWithError(String id, Model model){
        LOG.debug("Forwarding to \"/\" from \"/{}\"", id);
        model.addAttribute("errorObj", new GameErrorObject(id));
        return gamesPage(model, false);
    }

}
