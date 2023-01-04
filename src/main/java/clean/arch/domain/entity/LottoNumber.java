package clean.arch.domain.entity;

public class LottoNumber {
    private final int number;

    public LottoNumber(int number) {
        this.validateRange(number);
        this.number = number;
    }

    private void validateRange(int number) {
        if (number < 1 || number > 45) {
            throw new IllegalArgumentException("로또 번호는 1부터 45까지의 숫자로 이루어져야 합니다.");
        }
    }
}
