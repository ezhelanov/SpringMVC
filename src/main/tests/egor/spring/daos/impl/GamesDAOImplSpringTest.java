package egor.spring.daos.impl;

import egor.core.entities.Game;
import egor.core.entities.GameType;
import egor.spring.daos.rowmappers.GameRowMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class) // алиас для SpringJUnit4ClassRunner.class
@ContextConfiguration(classes = GamesDAOImplSpringTest.GamesDAOImplSpringTestConfiguration.class)
@TestPropertySource(properties = {
        "database.driver=com.mysql.cj.jdbc.Driver"
})

public class GamesDAOImplSpringTest {

    @ComponentScan(value = {"egor.spring.daos"})
    public static class GamesDAOImplSpringTestConfiguration {
        @Bean
        public Random randomizer(){
            return mock(Random.class);
        }
        @Bean
        public DriverManagerDataSource dataSource(@Value("${database.driver}") String driverName){
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverName);
            return dataSource;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(/*@Autowired*/ DriverManagerDataSource dataSourceBean){
            return new JdbcTemplate(dataSourceBean);
        }
    }

    private static final Game DEFAULT_GAME = new Game("Fallout New Vegas", 2011, GameType.RPG);;
    private static final String DEFAULT_ID = "n000g";;

    @Autowired
    @InjectMocks
    private GamesDAOImpl gamesDAOImpl;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        doReturn(DEFAULT_GAME).when(jdbcTemplate).queryForObject(anyString(), any(GameRowMapper.class), anyString());
    }

    @Test
    public void test_mock_annotation() {
        assertNull(gamesDAOImpl.getJdbcTemplate().getDataSource());
        assertSame(gamesDAOImpl.getJdbcTemplate(), jdbcTemplate);
    }

    @Test
    public void test_getGameById(){
        Game gameFromDAO = gamesDAOImpl.getGameById(DEFAULT_ID);

        verify(jdbcTemplate).queryForObject(anyString(), any(GameRowMapper.class), anyString());
        verifyNoMoreInteractions(jdbcTemplate);
        assertSame(DEFAULT_GAME, gameFromDAO);
    }

    @Test
    public void test_all_fields_were_edited(){
        Game patchedGame = new Game("Fallout New California", 2013, GameType.STEALTH);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        verify(jdbcTemplate, times(3)).update(anyString(), any(), anyString());
    }

    @Test
    public void test_no_fields_were_edited(){
        gamesDAOImpl.editGame(DEFAULT_ID, DEFAULT_GAME);

        verify(jdbcTemplate, never()).update(anyString(), any(), anyString());
    }

    @Test
    public void test_name_and_type_were_edited(){
        Game patchedGame = new Game("Fallout New California", DEFAULT_GAME.getYear(), GameType.STEALTH);

        gamesDAOImpl.editGame(DEFAULT_ID, patchedGame);

        InOrder inOrder = inOrder(gamesDAOImpl.getJdbcTemplate());
        inOrder.verify(jdbcTemplate).update("UPDATE games SET name = ? WHERE id = ?", patchedGame.getName(), DEFAULT_ID);
        inOrder.verify(jdbcTemplate).update("UPDATE games SET type = ? WHERE id = ?", patchedGame.getType().toString(), DEFAULT_ID);
    }

}
