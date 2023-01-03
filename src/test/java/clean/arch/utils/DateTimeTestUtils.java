package clean.arch.utils;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public class DateTimeTestUtils {
    public static ZonedDateTime fastSaturday(ZonedDateTime now) {
        if (now.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return now.plusDays(7);
        }
        var duration = DayOfWeek.SATURDAY.getValue() - now.getDayOfWeek().getValue();
        return now.plusDays(7).plusDays(duration);
    }
}
