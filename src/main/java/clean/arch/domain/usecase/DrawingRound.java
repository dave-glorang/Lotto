package clean.arch.domain.usecase;

import java.time.ZonedDateTime;

public interface DrawingRound {
    void draw(Request request);

    record Request(
        String name,
        ZonedDateTime now
    ){}
}
