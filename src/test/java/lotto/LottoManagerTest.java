package lotto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LottoManagerTest {

    @Test
    void 당첨_결과를_산출한다() {
        // given
        List<Lotto> myLotto = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 4, 6, 10, 11)),
                new Lotto(List.of(2, 3, 7, 10, 20, 30))
        );
        Lotto winningLotto = new Lotto(List.of(1, 3, 4, 7, 10, 30));
        Integer bonusNumber = 8;
        LottoManager lottoManager = new LottoManager(myLotto, winningLotto, bonusNumber);

        // when
        List<LottoResult> lottoResult = lottoManager.processResult();

        // then
        assertThat(lottoResult).isEqualTo(List.of(
                LottoResult.THREE,
                LottoResult.NONE,
                LottoResult.FOUR
        ));
    }

    @Test
    void 수익률을_산출한다() {
        // given
        List<Lotto> myLotto = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 4, 6, 10, 11)),
                new Lotto(List.of(2, 3, 7, 10, 20, 30))
        );
        Lotto winningLotto = new Lotto(List.of(1, 3, 4, 7, 10, 30));
        Integer bonusNumber = 8;
        LottoManager lottoManager = new LottoManager(myLotto, winningLotto, bonusNumber);

        // when
        lottoManager.processResult();
        Double profitRate = lottoManager.calculateProfitRate();

        // then
        assertThat(profitRate).isEqualTo(50166.7);
    }

    @Test
    void 빈_로또_리스트를_제공한_경우_예외가_발생한다() {
        List<Lotto> myLotto = List.of();
        Lotto winningLotto = new Lotto(List.of(1, 3, 4, 7, 10, 30));
        Integer bonusNumber = 8;

        assertThatThrownBy(() -> new LottoManager(myLotto, winningLotto, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 보너스_번호의_범위가_올바르지_않은_경우_예외가_발생한다() {
        List<Lotto> myLotto = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 4, 6, 10, 11)),
                new Lotto(List.of(2, 3, 7, 10, 20, 30))
        );
        Lotto winningLotto = new Lotto(List.of(1, 3, 4, 7, 10, 30));
        Integer bonusNumber = 50;

        assertThatThrownBy(() -> new LottoManager(myLotto, winningLotto, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
