package clean.arch.domain.entity;

import java.util.Set;

public class WinningLotto {
    private Set<LottoNumber> lottoNumbers;

    public WinningLotto(Set<LottoNumber> lottoNumbers) {
        this.validateSize(lottoNumbers);
        this.lottoNumbers = lottoNumbers;
    }

    private void validateSize(Set<LottoNumber> lottoNumbers) {
        if (lottoNumbers.size() != 6) {
            throw new IllegalArgumentException("로또 숫자는 6개여야 합니다.");
        }
    }
}
