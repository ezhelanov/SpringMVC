package egor.spring.utils;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EgorUtilsTest.EgorUtilsTestConfiguration.class)
@TestPropertySource(properties = {
        "server.sleep.time.ms=1660"
})
public class EgorUtilsTest {

    @Configuration
    public static class EgorUtilsTestConfiguration {
        @Bean
        public EgorUtils egorUtils(){ return new EgorUtils(); }
    }

    @Autowired
    private EgorUtils egorUtils;

    @Ignore
    @Test(timeout = 1660)
    public void sleeping_time_test(){
        egorUtils.sleepServer();
    }

    @Test
    public void test_property_source(){
        assertEquals(1660, egorUtils.getTimeToSleepInMs());
    }

}
