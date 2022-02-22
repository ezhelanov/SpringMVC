package egor.spring.daos.rowmappers;

import egor.core.entities.Game;
import egor.core.entities.GameType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GameRowMapper implements RowMapper<Game> {
  @Override
  public Game mapRow(ResultSet rs, int i) throws SQLException {
    return new Game(
      rs.getString("name"), rs.getInt("year"), GameType.valueOf(rs.getString("type"))
    );
  }
}
