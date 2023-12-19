package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다")
    void 재고의_수량이_제공된_수량보다_작은지_확인한다() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;
        //when
        boolean result = stock.isQuantityLessThan(quantity);
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다")
    void 재고를_주어진_개수만큼_차감할_수_있다() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다")
    void 재고보다_많은_수의_수량으로_차감_시도하는_경우_예외가_발생한다() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when
        //then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }
}