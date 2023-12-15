package sample.cafekiosk.unit.beverages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AmericanoTest {

    @Test
    @DisplayName("getName")
    void getName() throws Exception {
        //given
        //when
        Americano americano = new Americano();

        //then
//        assertEquals(americano.getName(), "아메리카노");
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    @DisplayName("getPrice")
    void getPrice() throws Exception {
        //given
        //when
        Americano americano = new Americano();

        //then
        assertThat(americano.getPrice()).isEqualTo(4000);
    }
    
}