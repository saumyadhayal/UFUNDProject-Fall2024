package com.ufund.api.ufundapi;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;

@Tag("Model")
public class UserTest {
    @Test
    public void testConstructor() {
        User expected = new User(1, "Aaron", null, false, "pass@123", 10);
        assertTrue(expected instanceof User);
    }
    @Test
    public void testGetID() {
        User user1 = new User(1, "Aaron", null, false, "pass@123", 10);
        int actual = user1.getId();
        assertEquals(1, actual);
    }
    @Test
    public void testGetName() {
        User user1 = new User(1, "Aaron", null, false, "pass@123", 10);
        String actual = user1.getName();
        assertEquals("Aaron", actual);
    }
    @Test
    public void testSetName() {
        User user1 = new User(1, "Aaron", null, false, "pass@123", 10);
        user1.setName("Bob");
        assertEquals("Bob", user1.getName(), "Name should be updated to 'Bob'");
    }
    @Test
    public void testGetCart() {
        Need[] cart = new Need[] {
            new Need(1, "Toy", 10.0, 2, "toy"),
            new Need(2, "Shirt", 20.0, 1, "clothing")
        };
        User user1 = new User(1, "Aaron", cart, false, "pass@123", 10);
        assertArrayEquals(cart, user1.getCart(), "Cart should match the initialized values");
    }
    @Test
    public void testGetAdmin_DefaultFalse() {
        User user1 = new User(1, "Aaron", null, false, "pass@123", 10);
        assertFalse(user1.getAdmin(), "User should not be admin by default");
    }
    @Test
    public void testSetAdmin() {
        User user1 = new User(1, "Aaron", null, false, "pass@123", 10);
        user1.setAdmin(true);
        assertTrue(user1.getAdmin(), "User should be updated to admin");
    }
    @Test
    public void testGetPassword() {
        User user1 = new User(1, "Aaron", null, false, "pass@123", 10);
        assertEquals("pass@123", user1.getPassword(), "Password should be 'pass@123'");
    }
    @Test
    public void testToString() {
        Need[] cart = new Need[] {
                new Need(1, "Toy", 10.0, 2, "toy"),
                new Need(2, "Shirt", 20.0, 1, "clothing")
        };
        User user1 = new User(1, "Alice", cart, false, "password123", 10);
        String expected = "user [id=1, name=Alice, cart=[Need [id=1, name=Toy, price=10.00, quantity=2, type=toy], "
                + "Need [id=2, name=Shirt, price=20.00, quantity=1, type=clothing]], isAdmin=false, password=password123, totalSpent=10]";
        assertEquals(expected, user1.toString(), "toString output should match the expected format");
    }
    @Test
    public void testHashCode_DefaultValues() {
        User user1 = new User(0, null, null, null, null, 0);
        User user2 = new User(0, null, null, null, null, 0);

        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();

        assertEquals(hash1, hash2, "Hash codes should be equal for objects with default null/zero values");
    }
    @Test
    public void testHashCode_NullAndEmptyCart() {
        User user1 = new User(0, null, new Need[] {}, null, null, 0);
        User user2 = new User(0, null, new Need[] {}, null, null, 0);

        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();

        assertEquals(hash1, hash2, "Hash codes should be equal for objects with empty cart arrays");
    }
    @Test
    public void testHashCode_ObjectsWithDifferentIds() {
        // Arrange
        User user1 = new User(1, null, null, null, null, 0);
        User user2 = new User(2, null, null, null, null, 0);

        // Act
        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();

        // Assert
        assertNotEquals(hash1, hash2, "Hash codes should not be equal for objects with different IDs");
    }

    @Test
    public void testHashCode_ObjectsWithSameFields() {
        // Arrange
        Need[] cart = new Need[]{new Need(1, "bed", 100.0, 1, "furniture")};
        User user1 = new User(123, "John", cart, true, "secret", 100);
        User user2 = new User(123, "John", cart, true, "secret", 100);

        // Act
        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();

        // Assert
        assertEquals(hash1, hash2, "Hash codes should be equal for identical objects with the same fields");
    }

    @Test
    public void testHashCode_ObjectsWithDifferentCarts() {
        // Arrange
        Need[] cart1 = new Need[]{new Need(1, "pants", 15.0, 3, "clothing")};
        Need[] cart2 = new Need[]{new Need(2, "ice pack", 10.0, 1, "health")};
        User user1 = new User(123, "John", cart1, true, "secret", 10);
        User user2 = new User(123, "John", cart2, true, "secret", 10);

        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();

        assertNotEquals(hash1, hash2, "Hash codes should not be equal for objects with different cart contents");
    }
    @Test
    public void testEquals_SameObject() {
        User user = new User(1, "John", new Need[]{new Need(1, "shoes", 20.0, 1, "clothing")}, true, "password123", 20);

        assertTrue(user.equals(user), "An object should be equal to itself");
    }
    @Test
    public void testEquals_IdenticalObjects() {
        Need[] cart = new Need[]{new Need(1, "shirt", 10.0, 2, "clothing")};
        User user1 = new User(1, "John", cart, true, "password123", 30);
        User user2 = new User(1, "John", cart, true, "password123", 30);

        assertTrue(user1.equals(user2), "Two objects with the same fields should be equal");
    }
    @Test
    public void testEquals_DifferentIds() {
        User user1 = new User(1, "John", new Need[] { new Need(1, "bandaid", 5.0, 5, "healthcare") }, true, "password123", 20);
        User user2 = new User(2, "John", new Need[] { new Need(1, "bandaid", 5.0, 5, "healthcare") }, true, "password123", 20);

        assertFalse(user1.equals(user2), "Objects with different IDs should not be equal");
    }
    @Test
    public void testEquals_DifferentNames() {
        User user1 = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "password123", 80);
        User user2 = new User(1, "Jane", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "password123", 80);

        assertFalse(user1.equals(user2), "Objects with different names should not be equal");
    }
    @Test
    public void testEquals_DifferentCartContents() {
        User user1 = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "password123", 80);
        User user2 = new User(1, "John", new Need[]{new Need(2, "sheets", 20.0, 2, "furniture")}, true, "password123", 80);

        assertFalse(user1.equals(user2), "Objects with different cart contents should not be equal");
    }
    @Test
    public void testEquals_DifferentIsAdmin() {
        User user1 = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "password123", 80);
        User user2 = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, false, "password123", 80);

        assertFalse(user1.equals(user2), "Objects with different isAdmin values should not be equal");
    }
    @Test
    public void testEquals_DifferentPasswords() {
        User user1 = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "password123", 80);
        User user2 = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "differentPassword", 80);

        assertFalse(user1.equals(user2), "Objects with different passwords should not be equal");
    }
    @Test
    public void testEquals_NullObject() {
        User user = new User(1, "John", new Need[]{new Need(1, "sneakers", 80.0, 1, "clothing")}, true, "password123", 80);

        assertFalse(user.equals(null), "An object should not be equal to null");
    }
}





