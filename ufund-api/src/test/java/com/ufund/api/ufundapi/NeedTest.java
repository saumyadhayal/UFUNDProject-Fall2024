package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;

@Tag("Model")
public class NeedTest {
    @Test
    public void testConstructor() {
        Need expected = new Need(1, "shirt", 8, 3, "clothing");

        assertTrue(expected instanceof Need);
    }
    // test getID
    @Test
    public void testGetID() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        int actual = need.getId();

        assertEquals(1, actual);
    }
    // test setName and getName
    @Test
    public void testGetName() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        need.setName("pants");
        String actual = need.getName();

        assertEquals("pants", actual);
    }

        @Test
    public void testGetPrice() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        need.setPrice(10);
        double actual = need.getPrice();

        assertEquals(10, actual);
    }

    @Test
    public void testGetQuantity() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        need.setQuantity(7);
        int actual = need.getQuantity();

        assertEquals(7, actual);
    }

    @Test
    public void testgetType() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        need.setType("toy");
        String actual = need.getType();

        assertEquals("toy", actual);
    }

    @Test
    public void testtoString() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        String expected = "Need [id=1, name=shirt, price=8.00, quantity=3, type=clothing]";
        String actual = need.toString();

        assertEquals(expected, actual);
    }

    // Test equals: Objects should be equal if all fields are the same
    @Test
    public void testEquals_SameObject() {
        Need need1 = new Need(1, "shirt", 8, 3, "clothing");
        Need need2 = new Need(1, "shirt", 8, 3, "clothing");
        assertEquals(need1, need2);
    }

    @Test
    public void testEquals_DifferentObject() {
        Need need1 = new Need(1, "shirt", 8, 3, "clothing");
        Need need2 = new Need(2, "pants", 10, 5, "clothing");
        assertNotEquals(need1, need2);
    }

    @Test
    public void testEquals_Null() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        assertNotEquals(null, need);
    }

    @Test
    public void testEquals_DifferentClass() {
        Need need = new Need(1, "shirt", 8, 3, "clothing");
        String differentObject = "Not a Need object";
        assertNotEquals(need, differentObject);
    }
    @Test
    public void testEquals_DifferentIds() {
        Need need1 = new Need(1, "shirt", 10.0, 5, "clothing");
        Need need2 = new Need(2, "shirt", 10.0, 5, "clothing");

        assertFalse(need1.equals(need2));
    }

    @Test
    public void testEquals_DifferentNames() {
        // Arrange
        Need need1 = new Need(1, "sneakers", 10.0, 5, "clothing");
        Need need2 = new Need(1, "shirt", 10.0, 5, "clothing");

        // Act & Assert
        assertFalse(need1.equals(need2));
    }

    @Test
    public void testEquals_DifferentPrices() {
        Need need1 = new Need(1, "shirt", 10.0, 5, "clothing");
        Need need2 = new Need(1, "shirt", 20.0, 5, "clothing");

        assertFalse(need1.equals(need2));
    }

    // Test hashCode: Objects with the same state should have the same hash code
    @Test
    public void testHashCode_SameObject() {
        Need need1 = new Need(1, "shirt", 8, 3, "clothing");
        Need need2 = new Need(1, "shirt", 8, 3, "clothing");
        assertEquals(need1.hashCode(), need2.hashCode());
    }

    @Test
    public void testHashCode_DifferentObject() {
        Need need1 = new Need(1, "shirt", 8, 3, "clothing");
        Need need2 = new Need(2, "pants", 10, 5, "clothing");
        assertNotEquals(need1.hashCode(), need2.hashCode());
    }
    @Test
    public void testEquals_NullObject() {
        Need need = new Need(1, "sneakers", 80.0, 1, "clothing");

        assertFalse(need.equals(null), "An object should not be equal to null");
    }
}
