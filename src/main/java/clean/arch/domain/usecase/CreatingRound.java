package clean.arch.domain.usecase;

import clean.arch.domain.entity.Round;

import java.time.ZonedDateTime;

public interface CreatingRound {
    void create(Request request);

    record Request(
        String name,
        ZonedDateTime drawnAt,
        ZonedDateTime now
    ){
        public Round toEntity() {
            return Round.create(
                name,
                drawnAt,
                now
            );
        }
    }
}
