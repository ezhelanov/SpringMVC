package egor.spring.controllers;

import egor.core.errors.GameErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import java.util.LinkedHashSet;
import java.util.Set;

import static egor.core.constants.EgorConstants.REDIRECT_PREFIX;

public abstract class AbstractGamesController {

    private Set<GameErrorObject> errors = new LinkedHashSet<>();

    private static final Logger LOG = LoggerFactory.getLogger(AbstractGamesController.class);

    public abstract String gamesPage(Model model, boolean wasRedirectedFromDelete);

    protected String redirectToGamesPageWithErrorObject(String id, Model model){
        GameErrorObject errorObj = new GameErrorObject(id);
        if (!errors.contains(errorObj)){
            errors.add(errorObj);
            model.addAttribute("errorObjs", errors);
            LOG.info("model: {}", model);
            LOG.info("Going to: \"gamesPage(model, false)\"");
            return gamesPage(model, false);
        }
        errors.clear();
        LOG.info("Cleared errors set. Going to: \"{}\"", REDIRECT_PREFIX);
        return REDIRECT_PREFIX;
    }

}
