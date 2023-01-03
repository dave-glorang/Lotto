package clean.arch.domain.usecase.impl;

import clean.arch.domain.entity.Round.InvalidRoundException;
import clean.arch.domain.entity.store.RoundStore;
import clean.arch.domain.usecase.CreatingRound;
import clean.arch.domain.usecase.impl.CreatingRoundImpl.DuplicatedRoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static clean.arch.utils.DateTimeTestUtils.fastSaturday;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/*
 * v 회차 생성 성공
 * v 동일한 회차이름이 있을 경우 실패
 * v 동일한 추첨일이 있을 경우 실패
 * v 추첨일이 토요일이 아니면 실패
 * v 추첨일이 과거인 경우 실패
 */
@ExtendWith(MockitoExtension.class)
class CreatingRoundTest {
    @InjectMocks
    private CreatingRoundImpl service;

    @Mock
    private RoundStore roundStore;


    @DisplayName("회차 생성 성공")
    @Test
    void success() {
        //given
        var request = new CreatingRound.Request(
            "test",
            fastSaturday(now()).minusDays(7),
            now()
        );
        given(roundStore.existsByNameOrDrawnAt(eq(request.name()), eq(request.drawnAt())))
            .willReturn(false);


        //when
        var throwable = catchThrowable(() -> {
           service.create(request);
        });

        //then
        assertThat(throwable).isNull();
    }

    @DisplayName("동일한 회차이름이 있을 경우 실패")
    @Test
    void failWhenDuplicatedName() {
        //given
        var request = new CreatingRound.Request(
            "existing name",
            fastSaturday(now()),
            now()
        );
        given(roundStore.existsByNameOrDrawnAt(eq(request.name()), eq(request.drawnAt())))
            .willReturn(true);

        //when
        var throwable = catchThrowable(() -> {
            service.create(request);
        });

        //then
        assertThat(throwable).isExactlyInstanceOf(DuplicatedRoundException.class);
    }

    @DisplayName("동일한 추첨일이 있을 경우 실패")
    @Test
    void failWhenDuplicatedDrawnAt() {
        //given
        var request = new CreatingRound.Request(
            "testName",
            fastSaturday(now()),
            now()
        );
        given(roundStore.existsByNameOrDrawnAt(eq(request.name()), eq(request.drawnAt())))
            .willReturn(true);

        //when
        var throwable = catchThrowable(() -> {
            service.create(request);
        });

        //then
        assertThat(throwable).isExactlyInstanceOf(DuplicatedRoundException.class);
    }

    @DisplayName("추첨일이 토요일이 아니면 실패")
    @Test
    void failWhenDrawnAtIsNotSaturday() {
        //given
        var request = new CreatingRound.Request(
            "testName",
            fastSaturday(now()).plusDays(1),
            now()
        );

        //when
        var throwable = catchThrowable(() -> {
            service.create(request);
        });

        //then
        assertThat(throwable).isExactlyInstanceOf(InvalidRoundException.class)
            .hasMessage("추첨일은 토요일만 지정 가능합니다.");
    }

    @DisplayName("추첨일이 과거인 경우 실패")
    @Test
    void failWhenDrawnAtIsPast() {
        //given
        var request = new CreatingRound.Request(
            "testName",
            fastSaturday(now()).minusDays(21),
            now()
        );

        //when
        var throwable = catchThrowable(() -> {
            service.create(request);
        });

        //then
        assertThat(throwable).isExactlyInstanceOf(InvalidRoundException.class)
            .hasMessage("추첨일은 과거로 지정할 수 없습니다.");
    }
}