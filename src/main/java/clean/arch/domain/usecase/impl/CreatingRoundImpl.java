package clean.arch.domain.usecase.impl;

import clean.arch.domain.entity.Round;
import clean.arch.domain.entity.store.RoundStore;
import clean.arch.domain.usecase.CreatingRound;

public class CreatingRoundImpl implements CreatingRound {
    private final RoundStore roundStore;

    public CreatingRoundImpl(RoundStore roundStore) {
        this.roundStore = roundStore;
    }

    @Override
    public void create(Request request) {
        var round = request.toEntity();
        var duplicatedRound = roundStore.existsByNameOrDrawnAt(
            request.name(),
            request.drawnAt()
        );

        if (duplicatedRound) throw new DuplicatedRoundException();

        roundStore.create(round);
    }

    static class DuplicatedRoundException extends IllegalArgumentException {
    }
}
