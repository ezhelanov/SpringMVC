package egor.spring.utils;

import org.junit.Before;
import org.junit.Test;

public class EgorUtilsTest {

    private EgorUtils egorUtils;

    @Before
    public void init(){
        egorUtils = new EgorUtils();
        egorUtils.setTimeToSleepInMs(2000);
    }

    @Test(timeout = 2001)
    public void sleeping_time_test(){
        egorUtils.sleepServer();
    }

}
