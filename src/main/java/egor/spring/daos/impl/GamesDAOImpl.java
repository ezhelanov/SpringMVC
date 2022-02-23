package egor.spring.daos.impl;

import egor.core.entities.Game;
import egor.spring.daos.GamesDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Set;

import static org.springframework.util.ReflectionUtils.getField;

@Component
public class GamesDAOImpl implements GamesDAO {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Autowired private Random randomizer;

  @Resource private RowMapper idRowMapper;
  @Resource private RowMapper gameRowMapper;

  private final static Logger LOG = LoggerFactory.getLogger(GamesDAOImpl.class);

  @Override
  public Set<String> getAllGameIDs() {
    return Set.copyOf(jdbcTemplate.query("SELECT id FROM games", idRowMapper));
  }

  @Override
  public Game getGameById(String id) {
    try {
      return (Game) jdbcTemplate.queryForObject("SELECT * FROM games WHERE id = ?", gameRowMapper, id);
    } catch (EmptyResultDataAccessException e){
      LOG.error("No game with id \"{}\" found", id);
    }
    return null;
  }

  @Override
  public void addGame(Game game) {
    String id = "n" + (100 + randomizer.nextInt(900)) + "g";
    jdbcTemplate.update(
      "INSERT INTO games (id, name, year, type) VALUE (?,?,?,?)",
      id, game.getName(), game.getYear(), game.getType().toString()
    );
    LOG.info("Added game {} with id \"{}\"", game, id);
  }

  @Override
  public void editGame(String id, Game patchedGame) {
    Game game = getGameById(id);
    for (Field field : Game.class.getDeclaredFields()) {
      field.setAccessible(true);
      Object gameField = getField(field, game);
      Object patchedGameField = getField(field, patchedGame);
      if (!gameField.equals(patchedGameField)) {
        String sql = null;
        switch (field.getName()){
          case "name":
            sql = "UPDATE games SET name = ? WHERE id = ?";
            break;
          case "year":
            sql = "UPDATE games SET year = ? WHERE id = ?";
            break;
          case "type":
            sql = "UPDATE games SET type = ? WHERE id = ?";
            patchedGameField = patchedGameField.toString();
            break;
        }
        jdbcTemplate.update(sql, patchedGameField, id);
        LOG.info("Updated field \"{}\" in game with id \"{}\"", field.getName(), id);
      }
      field.setAccessible(false);
    }
  }

  @Override
  public void replaceGame(String id, Game putGame) {
    jdbcTemplate.update(
      "UPDATE games SET name = ?, year = ?, type = ? WHERE id = ?",
      putGame.getName(), putGame.getYear(), putGame.getType().toString(), id
    );
    LOG.info("Game with id \"{}\" replaced with new one {}", id, putGame);
  }

  @Override
  public void deleteGame(String id) {
    jdbcTemplate.update("DELETE FROM games WHERE id = ?", id);
    LOG.info("Deleted game with id \"{}\"", id);
  }
}
