package clean.arch.domain.common;

import clean.arch.domain.entity.LottoNumber;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoGenerator {
    public Set<LottoNumber> generate() {
        var numbers = IntStream.range(1, 45)
            .mapToObj(Integer::valueOf)
            .toList();

        Collections.shuffle(numbers);

       return numbers.subList(0, 6)
           .stream()
           .map(LottoNumber::new)
           .collect(Collectors.toSet());
    }
}