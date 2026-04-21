package com.example.eshop;

import com.example.eshop.cart.Cart;
import com.example.eshop.cart.CartItem;
import com.example.eshop.order.Order;
import com.example.eshop.order.OrderService;
import com.example.eshop.product.DigitalProduct;
import com.example.eshop.product.PhysicalProduct;
import com.example.eshop.product.Product;
import com.example.eshop.payment.CreditCardPaymentProcessor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Test
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void getDownloadURL(){
        DigitalProduct p = new DigitalProduct(
                "sus",
                "sussy",
                BigDecimal.valueOf(84154853781.84824),
                "myurl"
        );
        assertSame("myurl", p.getDownloadUrl());
    }

    @Test
    public void setDownloadUrl(){
        DigitalProduct p = new DigitalProduct(
                "sus",
                "sussy",
                BigDecimal.valueOf(84154853781.84824),
                "myurl"
        );
        p.setDownloadUrl("www.sussy.cz");
        assertSame("www.sussy.cz", p.getDownloadUrl());
    }

    @Test
    public void getWeight(){
        PhysicalProduct p = new PhysicalProduct(
                "Car",
                "Cat",
                BigDecimal.valueOf(215184515),
                589654,
                BigDecimal.valueOf(1862642)
        );
        assertEquals(589654.f, p.getWeight());
    }

    @Test
    public void setWeight(){
        PhysicalProduct p = new PhysicalProduct(
                "Car",
                "Cat",
                BigDecimal.valueOf(215184515),
                589654,
                BigDecimal.valueOf(1862642)
        );

        p.setWeight(1575);
        assertEquals(1575.f, p.getWeight());
    }

    @Test
    public void getShippingCost(){
        PhysicalProduct p = new PhysicalProduct(
                "Car",
                "Cat",
                BigDecimal.valueOf(215184515),
                589654,
                BigDecimal.valueOf(1862642)
        );

        assertEquals(BigDecimal.valueOf(1862642), p.getShippingCost());
    }

    @Test
    public void addItem_newProduct_shouldAddItem() {
        Cart cart = new Cart();
        Product product = new DigitalProduct("Test", "Test", BigDecimal.valueOf(100), "url");

        cart.addItem(product, 2);

        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void addItem_existingProduct_shouldIncreaseQuantity() {
        Cart cart = new Cart();
        Product product = new DigitalProduct("Test", "Test", BigDecimal.valueOf(100), "url");

        cart.addItem(product, 2);
        cart.addItem(product, 3);

        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void removeItem_shouldRemoveProduct() {
        Cart cart = new Cart();
        Product product = new DigitalProduct("Test", "Test", BigDecimal.valueOf(100), "url");

        cart.addItem(product, 2);
        cart.removeItem(product);

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void getItems_shouldBeUnmodifiable() {
        Cart cart = new Cart();
        Product product = new DigitalProduct("Test", "Test", BigDecimal.valueOf(100), "url");

        cart.addItem(product, 1);

        assertThrows(UnsupportedOperationException.class, () -> {
            cart.getItems().add(new CartItem(product, 1));
        });
    }

    @Test
    public void calculateTotal_shouldReturnCorrectSum() {
        Cart cart = new Cart();
        Product product1 = new DigitalProduct("A", "A", BigDecimal.valueOf(10), "url");
        Product product2 = new DigitalProduct("B", "B", BigDecimal.valueOf(20), "url");

        cart.addItem(product1, 2); // 20
        cart.addItem(product2, 1); // 20

        assertEquals(BigDecimal.valueOf(40), cart.calculateTotal());
    }

    @Test
    public void calculateTotal_emptyCart_shouldReturnZero() {
        Cart cart = new Cart();

        assertEquals(BigDecimal.ZERO, cart.calculateTotal());
    }

    @Test
    public void clear_shouldRemoveAllItems() {
        Cart cart = new Cart();
        Product product = new DigitalProduct("Test", "Test", BigDecimal.valueOf(100), "url");

        cart.addItem(product, 2);
        cart.clear();

        assertEquals(0, cart.getItems().size());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 10.0, 20.0, 35.0 })
    void testProcessPayment_WithPositiveAmount_True(double amount) {
        assertTrue(new CreditCardPaymentProcessor().processPayment(BigDecimal.valueOf(amount)));
    }

    @Test
    void testPlaceOrder_HasUUID_Success() {
        Product product = new PhysicalProduct("Test Physical Product", "An example physical product", BigDecimal.valueOf(20.00d), 10, BigDecimal.valueOf(2.5));
        Cart cart = new Cart();
        cart.addItem(product, 2);

        OrderService orderService = new OrderService(new CreditCardPaymentProcessor());
        AtomicReference<Order> order = new AtomicReference<>();
        assertDoesNotThrow(() -> order.set(orderService.placeOrder(cart)));
        assertNotNull(order.get());
        assertNotNull(order.get().getId());
    }
}
