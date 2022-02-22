package egor.spring.daos.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class IdRowMapper implements RowMapper<String> {
  @Override
  public String mapRow(ResultSet resultSet, int i) throws SQLException {
    return resultSet.getString("id");
  }
}
