package egor.spring.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EgorUtils {

    @Value("${server.sleep.time.ms:500}")
    private int timeToSleepInMs;

    public void sleepServer(){
        try {
            Thread.sleep(timeToSleepInMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTimeToSleepInMs() {
        return timeToSleepInMs;
    }
}
