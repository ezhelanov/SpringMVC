package egor.spring.controllers;

import egor.core.entities.Game;
import egor.spring.daos.GamesDAO;
import egor.spring.services.AttributesService;
import egor.spring.utils.EgorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static egor.core.constants.EgorConstants.REDIRECT_PREFIX;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class GamesController extends AbstractGamesController {

    @Autowired
    private GamesDAO gamesDAO;
    @Autowired
    private AttributesService attributesService;
    @Autowired
    private EgorUtils egorUtils;

    private final static Logger LOG = LoggerFactory.getLogger(GamesController.class);

    @GetMapping
    public String gamesPage(Model model, @RequestParam(required = false) boolean wasRedirectedFromDelete) {
        if (wasRedirectedFromDelete) {
            egorUtils.sleepServer();
            return REDIRECT_PREFIX;
        }
        model.addAttribute("ids", gamesDAO.getAllGameIDs());
        return "games/games/gamesPage";
    }

    @GetMapping("{id}")
    public String gameDetailsPage(@PathVariable String id, Model model) {
        LOG.info("Going to: GET /{}", id);
        Game game = gamesDAO.getGameById(id);
        if (isNull(game)){
            return forwardToGamesPageWithError(id, model);
        }
        model.addAttribute("game", game);
        return "games/game-details/gameDetailsPage";
    }

    @GetMapping("new")
    public String postGet(@ModelAttribute Game game, Model model) {
        attributesService.addFormActionAndMethod("new", "post", model);
        return "games/form/form";
    }

    @PostMapping("new")
    public String post(@ModelAttribute @Valid Game game, BindingResult br, Model model) {
        if (br.hasFieldErrors()) {
            attributesService.addErrors(br, model);
            return postGet(game, model);
        }
        gamesDAO.addGame(game);
        return REDIRECT_PREFIX;
    }

    @GetMapping("{id}/edit")
    public String patchGet(@PathVariable String id, Model model) {
        attributesService.addFormActionAndMethod(id, "patch", model);
        if (!attributesService.hasErrors(model)) {
            Game game = gamesDAO.getGameById(id);
            if (isNull(game)){
                return forwardToGamesPageWithError(id, model);
            }
            model.addAttribute("game", game);
        }
        return "games/form/form";
    }

    @PatchMapping("{id}")
    public String patch(@PathVariable String id, @ModelAttribute("game") @Valid Game patchedGame, BindingResult br, Model model) {
        if (br.hasFieldErrors()) {
            attributesService.addErrors(br, model);
            return patchGet(id, model);
        }
        gamesDAO.editGame(id, patchedGame);
        return REDIRECT_PREFIX + id;
    }

    @GetMapping("{id}/replace")
    public String putGet(@PathVariable String id, @ModelAttribute Game game, Model model) {
        attributesService.addFormActionAndMethod(id, "put", model);
        return "games/form/form";
    }

    @PutMapping("{id}")
    public String put(@PathVariable String id, @ModelAttribute("game") @Valid Game putGame, BindingResult br, Model model) {
        if (br.hasFieldErrors()) {
            attributesService.addErrors(br, model);
            return putGet(id, putGame, model);
        }
        gamesDAO.replaceGame(id, putGame);
        return REDIRECT_PREFIX + id;
    }

    @GetMapping("{id}/delete")
    public String deleteGet(@PathVariable String id, Model model) {
        attributesService.addFormActionAndMethod(id, "delete", model);
        return "games/delete/delete";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable String id) {
        gamesDAO.deleteGame(id);
        return REDIRECT_PREFIX + "?" + "wasRedirectedFromDelete" + "=" + true;
    }

}
