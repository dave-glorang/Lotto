package clean.arch.domain.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LottoGeneratorTest {

    @DisplayName("생성 성공")
    @Test
    void success() {
        //given
        var lottoGenerator = new LottoGenerator();

        //when
        var result = lottoGenerator.generate();

        //then
    }

}