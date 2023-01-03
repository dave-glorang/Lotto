package clean.arch.domain.entity;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

public class Round {
    private String name;
    private ZonedDateTime drawnAt;

    private Round(String name, ZonedDateTime drawnAt) {
        this.name = name;
        this.drawnAt = drawnAt;
    }

    public static Round create(String name,
                               ZonedDateTime drawnAt,
                               ZonedDateTime now) {
        validateDrawnAt(drawnAt, now);
        return new Round(name, drawnAt);
    }

    private static void validateDrawnAt(ZonedDateTime drawnAt, ZonedDateTime now) {
        if (drawnAt.getDayOfWeek() != DayOfWeek.SATURDAY) {
            throw new InvalidRoundException("추첨일은 토요일만 지정 가능합니다.");
        }

        if (drawnAt.isBefore(now)) {
            throw new InvalidRoundException("추첨일은 과거로 지정할 수 없습니다.");
        }
    }

    public String name() {
        return name;
    }

    public ZonedDateTime drawnAt() {
        return drawnAt;
    }

    public static class InvalidRoundException extends IllegalArgumentException {
        InvalidRoundException(String s) {
            super(s);
        }
    }

    public static class NotYetDrawingDateException extends IllegalArgumentException {

    }
}
