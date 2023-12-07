package study.sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void 덧셈_연산을_할_수_있다() throws Exception {
        //given
        long num1 = 2;
        String operator = "+";
        long num2 = 3;
        Calculator calculator = new Calculator();
        //when
        long result = calculator.calculate(num1, operator, num2);

        //then
        assertEquals(5, result);
        assertThat(result).isEqualTo(5);
    }
    @Test
    void 뺄셈_연산을_할_수_있다() throws Exception {
        //given
        long num1 = 2;
        String operator = "-";
        long num2 = 3;
        Calculator calculator = new Calculator();
        //when
        long result = calculator.calculate(num1, operator, num2);

        //then
        assertEquals(-1, result);
        assertThat(result).isEqualTo(-1);
    }
    @Test
    void 곱셈_연산을_할_수_있다() throws Exception {
        //given
        long num1 = 2;
        String operator = "*";
        long num2 = 3;
        Calculator calculator = new Calculator();
        //when
        long result = calculator.calculate(num1, operator, num2);

        //then
        assertEquals(6, result);
        assertThat(result).isEqualTo(6);
    }
    @Test
    void 나눗셈_연산은_할_수_있다() throws Exception {
        //given
        long num1 = 2;
        String operator = "/";
        long num2 = 3;
        Calculator calculator = new Calculator();
        //when
        long result = calculator.calculate(num1, operator, num2);

        //then
        assertEquals(0, result);
        assertThat(result).isEqualTo(0);
    }
    @Test
    void 잘못된_연산자가_요청으로_들어올_경우_에러가_발생한다() throws Exception {
        //given
        long num1 = 2;
        String operator = "x";
        long num2 = 3;
        Calculator calculator = new Calculator();
        //when
        assertThatThrownBy(() -> calculator.calculate(num1, operator, num2))
                .isInstanceOf(InvalidOperatorException.class);

        //then
    }


}