package egor.spring.daos.impl;

import egor.core.entities.Game;
import egor.core.entities.GameType;
import egor.spring.daos.rowmappers.GameRowMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class GamesDAOImplTest {

    private static Game DEFAULT_GAME = new Game("Fallout New Vegas", 2011, GameType.RPG);
    private static String DEFAULT_ID = "n456g";
    private static String QUERY_BY_ID = "SELECT * FROM games WHERE id = ?";
    private static String UPDATE_NAME;
    private static String UPDATE_YEAR;
    private static String UPDATE_TYPE;

    @BeforeClass
    public static void setUpQueries(){
        String prefix = "UPDATE games SET ";
        String postfix = " = ? WHERE id = ?";
        UPDATE_NAME = prefix + "name" + postfix;
        UPDATE_YEAR = prefix + "year" + postfix;
        UPDATE_TYPE = prefix + "type" + postfix;
    }

    private GamesDAOImpl gamesDAOImpl;

    private JdbcTemplate jdbcTemplateMock;
    private RowMapper<Game> gameRowMapperMock;

    @Before
    public void setUp(){
        gamesDAOImpl = new GamesDAOImpl();
        jdbcTemplateMock = mock(JdbcTemplate.class);
        gameRowMapperMock = mock(GameRowMapper.class);
        gamesDAOImpl.setJdbcTemplate(jdbcTemplateMock);
        gamesDAOImpl.setGameRowMapper(gameRowMapperMock);

        when(jdbcTemplateMock.queryForObject(QUERY_BY_ID, gameRowMapperMock, DEFAULT_ID)).thenReturn(DEFAULT_GAME);
    }

    @Test
    public void get_game_by_id_test(){

        Game gameFromDAO = gamesDAOImpl.getGameById(DEFAULT_ID);

        verify(jdbcTemplateMock, only()).queryForObject(QUERY_BY_ID, gameRowMapperMock, DEFAULT_ID);
        verifyNoMoreInteractions(jdbcTemplateMock);
        assertSame(DEFAULT_GAME, gameFromDAO);
    }

    @Test
    public void edit_all_fields_test(){
        Game patchedGame = new Game("Fallout New California", 2013, GameType.STEALTH);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        verify(jdbcTemplateMock, times(3)).update(anyString(), any(), anyString());
    }

    @Test
    public void edit_nothing_test(){
        Game patchedGame = new Game("Fallout New Vegas", 2011, GameType.RPG);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        verify(jdbcTemplateMock, never()).update(anyString(), any(), anyString());
    }

    @Test
    public void edit_year_field_test(){
        Game patchedGame = new Game("Fallout New Vegas", 2018, GameType.RPG);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        verify(jdbcTemplateMock, times(1)).update(UPDATE_YEAR, patchedGame.getYear(), DEFAULT_ID);
    }

}
