package company;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtils {

    @SneakyThrows
    public static void sleep(Long timeToSleep) {
        Thread.sleep(timeToSleep);
    }

}
