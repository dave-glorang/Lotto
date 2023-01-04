package clean.arch.domain.usecase.impl;

import clean.arch.domain.common.LottoGenerator;
import clean.arch.domain.entity.LottoNumber;
import clean.arch.domain.entity.Round;
import clean.arch.domain.entity.WinningLotto;
import clean.arch.domain.entity.store.RoundStore;
import clean.arch.domain.usecase.DrawingRound;
import clean.arch.utils.DateTimeTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static clean.arch.utils.DateTimeTestUtils.fastSaturday;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

/*
 * - 추첨 성공
 * - 추첨일이 아닐 경우 실패
 *
 */
@ExtendWith(MockitoExtension.class)
class DrawingRoundTest {

    @InjectMocks
    private Service service;

    @Mock
    private RoundStore roundStore;

    @Mock
    private LottoGenerator lottoGenerator;

    @DisplayName("추첨 성공")
    @Test
    void success() {
        //given
        var request = new DrawingRound.Request(
            "testName",
            fastSaturday(now())
        );
        var round = Round.create(
          request.name(),
          request.now(),
          now().minusDays(7)
        );
        var mockLotto = Set.of(1, 2, 3, 4, 5, 6)
            .stream()
            .map(LottoNumber::new)
            .collect(Collectors.toSet());
        given(roundStore.findByName(eq(request.name())))
            .willReturn(Optional.of(round));
        given(lottoGenerator.generate())
            .willReturn(mockLotto);

        //when
        var throwable = catchThrowable(() -> {
            service.draw(request);
        });

        //then
        assertThat(throwable).isNull();
    }

    static class Service implements DrawingRound {
        private final RoundStore roundStore;
        private final LottoGenerator lottoGenerator;

        public Service(RoundStore roundStore, LottoGenerator lottoGenerator) {
            this.roundStore = roundStore;
            this.lottoGenerator = lottoGenerator;
        }

        @Override
        public void draw(Request request){
            var round = roundStore.findByName(request.name())
                .orElseThrow();
            var numbers = lottoGenerator.generate();
            var winningLotto = new WinningLotto(numbers);
        }
    }
}