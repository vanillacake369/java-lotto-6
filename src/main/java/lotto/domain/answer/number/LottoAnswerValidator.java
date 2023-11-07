package lotto.domain.answer.number;

import static lotto.domain.policy.LottoNumberRangePolicy.END_RANGE;
import static lotto.domain.policy.LottoNumberRangePolicy.START_RANGE;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lotto.domain.exception.ExceptionFormat;

public final class LottoAnswerValidator {
    private final static String LOTTO_ANSWER_REGEX = "^[1-9]+(\\,[1-9]+){5}$";
    private final static String BONUS_REGEX = "^[1-9]\\d*$";

    public static void validateLottoAnswerInput(String lottoAnswerInput) throws IllegalArgumentException {
        // 당첨번호 길이 검증
        validSize(lottoAnswerInput);
        // 각 숫자별 범위 검증
        List<Integer> answers = Arrays.stream(lottoAnswerInput.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        validNumberRange(answers);
    }

    static void validNumberRange(List<Integer> answers) {
        for (Integer answer : answers) {
            if (START_RANGE.getRange() > answer || answer > END_RANGE.getRange()) {
                throw new IllegalArgumentException(ExceptionFormat.formatException("당첨번호는 1이상 45이하의 정수만 입력할 수 있습니다."));
            }
        }
    }

    static void validSize(String lottoAnswerInput) throws IllegalArgumentException {
        if (!Pattern.matches(LOTTO_ANSWER_REGEX, lottoAnswerInput)) {
            throw new IllegalArgumentException(ExceptionFormat.formatException("당첨번호는 6개만 입력 가능합니다."));
        }
    }

    public static void validateBonusInput(String bonusInput) {
        if (!Pattern.matches(BONUS_REGEX, bonusInput)) {
            throw new IllegalArgumentException(ExceptionFormat.formatException("보너스 번호는 정수만 입력가능합니다."));
        }
        int bonus = Integer.parseInt(bonusInput);
        if (bonus < START_RANGE.getRange() || bonus > END_RANGE.getRange()) {
            throw new IllegalArgumentException(ExceptionFormat.formatException("보너스 번호의 범위는 1이상, 45이하입니다."));
        }
    }
}
