package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
    void 상품_타입이_재고_관련_타입인지를_체크한다() {
        //given
        ProductType givenType = ProductType.HANDMADE;
        //when
        boolean result = ProductType.containsStockType(givenType);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다 False")
    void 상품_타입이_재고_관련_타입인지를_체크한다_False() {
        //given
        ProductType givenType = ProductType.BAKERY;
        //when
        boolean result = ProductType.containsStockType(givenType);

        //then
        assertThat(result).isTrue();
    }
}