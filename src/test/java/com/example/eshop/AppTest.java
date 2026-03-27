package com.example.eshop;

import com.example.eshop.product.DigitalProduct;
import com.example.eshop.product.PhysicalProduct;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
}
