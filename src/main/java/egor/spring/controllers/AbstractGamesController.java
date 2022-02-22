package egor.spring.controllers;

import egor.core.errors.GameErrorObject;
import org.springframework.ui.Model;

public abstract class AbstractGamesController {

    public abstract String gamesPage(Model model, boolean wasRedirectedFromDelete);

    protected String redirectToGamesPageWithErrorObject(String id, Model model){
        model.addAttribute("errorObj", new GameErrorObject(id));
        return gamesPage(model, false);
    }

}
