package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductStatusType;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderStatisticsServiceTest {
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("결제완료 주문들을 조회화여 결제 완료 메일을 전송한다")
    void 결제완료_주문들을_조회화여_결제_완료_메일을_전송한다() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);

        Product product1 = createProduct("001", "아메리카노1", 1000);
        Product product2 = createProduct("002", "아메리카노2", 2000);
        Product product3 = createProduct("003", "아메리카노3", 3000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);
        Order order1 = createPaymentCompleteOrder(LocalDateTime.of(2023, 3, 4, 23, 59), products);
        Order order2 = createPaymentCompleteOrder(LocalDateTime.now(), products);
        Order order3 = createPaymentCompleteOrder(LocalDateTime.now(), products);

        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        //then
        assertThat(result).isTrue();
    }

    private Order createPaymentCompleteOrder(LocalDateTime now, List<Product> products) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now).build();

        return orderRepository.save(order);
    }

    private Product createProduct(String productNumber, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductStatusType.SELLING)
                .name(name)
                .price(price)
                .build();
    }
}