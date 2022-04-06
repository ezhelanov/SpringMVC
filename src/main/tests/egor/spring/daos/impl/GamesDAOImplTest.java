package egor.spring.daos.impl;

import egor.core.entities.Game;
import egor.core.entities.GameType;
import egor.spring.daos.rowmappers.GameRowMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) // видит аннотации @Mock, @InjectMocks
public class GamesDAOImplTest {

    private static Game DEFAULT_GAME;
    private static String DEFAULT_ID;

    @BeforeClass
    public static void setUpStatic(){
        DEFAULT_GAME = new Game("Fallout New Vegas", 2011, GameType.RPG);
        DEFAULT_ID = "n000g";
    }

    @InjectMocks // создаёт объект класса GamesDAOImpl, внедряет в него поля, помеченные @Mock
    private GamesDAOImpl gamesDAOImpl;

    @Mock // mock(JdbcTemplate.class)
    private JdbcTemplate jdbcMock;

    @Mock
    private GameRowMapper gameRowMapperMock;

    @Before
    public void setUp(){
        given(jdbcMock.queryForObject("SELECT * FROM games WHERE id = ?", gameRowMapperMock, DEFAULT_ID))
                .willReturn(DEFAULT_GAME);
    }

    @Test
    public void test_getGameById(){

        Game gameFromDAO = gamesDAOImpl.getGameById(DEFAULT_ID);

        verify(jdbcMock, only()).queryForObject("SELECT * FROM games WHERE id = ?", gameRowMapperMock, DEFAULT_ID);
        verifyNoMoreInteractions(jdbcMock);
        assertSame(DEFAULT_GAME, gameFromDAO);
    }

    @Test
    public void test_all_fields_were_edited(){
        Game patchedGame = new Game("Fallout New California", 2013, GameType.STEALTH);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        verify(jdbcMock, times(3)).update(anyString(), any(), anyString());
    }

    @Test
    public void test_no_fields_were_edited(){

        gamesDAOImpl.editGame(DEFAULT_ID, DEFAULT_GAME);

        verify(jdbcMock, never()).update(anyString(), any(), anyString());
    }

    @Test
    public void test_name_and_type_were_edited(){
        Game patchedGame = new Game("Fallout New California", DEFAULT_GAME.getYear(), GameType.STEALTH);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        InOrder inOrder = inOrder(jdbcMock);
        inOrder.verify(jdbcMock).update("UPDATE games SET name = ? WHERE id = ?", patchedGame.getName(), DEFAULT_ID);
        inOrder.verify(jdbcMock).update("UPDATE games SET type = ? WHERE id = ?", patchedGame.getType().toString(), DEFAULT_ID);
    }

}
