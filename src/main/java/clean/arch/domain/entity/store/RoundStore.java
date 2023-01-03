package clean.arch.domain.entity.store;

import clean.arch.domain.entity.Round;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface RoundStore {
    void create(Round round);
    Optional<Round> findByName(String name);
    boolean existsByNameOrDrawnAt(String name, ZonedDateTime dateTime);
}
