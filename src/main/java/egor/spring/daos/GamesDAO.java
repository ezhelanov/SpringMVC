package egor.spring.daos;

import egor.core.entities.Game;

import java.util.Set;

public interface GamesDAO {

  Set<String> getAllGameIDs();

  Game getGameById(String id);

  void addGame(Game game);

  void editGame(
    String id,
    Game patchedGame
  );

  void replaceGame(
    String id,
    Game putGame
  );

  void deleteGame(String id);

}
