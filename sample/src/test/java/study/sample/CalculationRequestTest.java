package study.sample;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestTest {
    @Test
    void 유효한_숫자를_파싱할_수_있다() throws Exception {
        //given
        String[] parts = {"2", "+", "3"};
        //when
        CalculationRequest calculationRequest = new CalculationRequest(parts);
        //then
        assertThat(calculationRequest.getNum1()).isEqualTo(2);
        assertThat(calculationRequest.getOperator()).isEqualTo("+");
        assertThat(calculationRequest.getNum2()).isEqualTo(3);
    }
    @Test
    void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다() throws Exception {
        //given
        String[] parts = {"232", "+", "123"};
        //when
        CalculationRequest calculationRequest = new CalculationRequest(parts);
        //then
        assertThat(calculationRequest.getNum1()).isEqualTo(232);
        assertThat(calculationRequest.getOperator()).isEqualTo("+");
        assertThat(calculationRequest.getNum2()).isEqualTo(123);
    }
    @Test
    void 유효한_길이의_숫자가_들어오지_않으면_에러를_던진다() throws Exception {
        //given
        String[] parts = {"232", "+"};
        //when
        assertThatThrownBy(() -> new CalculationRequest(parts))
                .isInstanceOf(BadRequestException.class);
        //then
    }
    @Test
    void 유효하지_않은_연산자가_들어오면_에러를_던진다() throws Exception {
        //given
        String[] parts = {"232", "x", "123"};
        //when
        assertThatThrownBy(() -> new CalculationRequest(parts))
                .isInstanceOf(InvalidOperatorException.class);
        //then
    }

    @Test
    void 유효하지_않은_길이의_연산자가_들어오면_에러를_던진다() throws Exception {
        //given
        String[] parts = {"232", "+-", "123"};
        //when
        assertThatThrownBy(() -> new CalculationRequest(parts))
                .isInstanceOf(InvalidOperatorException.class);
        //then
    }

}