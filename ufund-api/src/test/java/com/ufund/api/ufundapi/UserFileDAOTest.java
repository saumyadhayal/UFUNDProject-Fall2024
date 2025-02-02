package com.ufund.api.ufundapi;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.persistence.UserFileDAO;

@Tag("Persistence")
public class UserFileDAOTest {
    UserFileDAO userDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;


    @BeforeEach
    public void setupUserDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        // Create test data for users
        testUsers = new User[2];
        testUsers[0] = new User(1, "Alice", new Need[0], false, "password1", 10);
        testUsers[1] = new User(2, "Bob", new Need[0], false, "password2", 10);
        // Mock the ObjectMapper to return test data
        when(mockObjectMapper.readValue(new File("testUsers.txt"), User[].class)).thenReturn(testUsers);
        // Initialize UserFileDAO with mocked ObjectMapper
        userDAO = new UserFileDAO("testUsers.txt", mockObjectMapper);
        // Mock save method to avoid file writes
        doNothing().when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
    }
    @Test
    public void testGetUsers() throws IOException {
        User[] expectedUsers = testUsers;
        User[] actualUsers = userDAO.getUsers();
        assertArrayEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testGetUser_ExistingUser() throws IOException {
        User expectedUser = testUsers[0];
        User actualUser = userDAO.getUser(1);


        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void testGetUser_NonExistentUser() throws IOException {
        User actualUser = userDAO.getUser(999);
        assertNull(actualUser);
    }
    @Test
    public void testCreateUser() throws IOException {
        User newUser = new User(0, "Charlie", new Need[0], false, "password3", 10);
        // Perform creation
        User createdUser = userDAO.createUser(newUser);
        // Assertions
        assertNotNull(createdUser);
        assertEquals("Charlie", createdUser.getName());
        assertTrue(createdUser.getId() > 0);  // Check for valid assigned ID
    }
    @Test
    public void testUpdateUser_ExistingUser() throws IOException {
        User updatedUser = new User(1, "AliceUpdated", new Need[0], true, "newPassword1", 10);
        User result = userDAO.updateUser(updatedUser);
        assertEquals(updatedUser, result);
        User fetchedUser = userDAO.getUser(1);
        assertEquals(updatedUser, fetchedUser);
    }
    @Test
    public void testUpdateUser_NonExistentUser() throws IOException {
        User nonExistentUser = new User(999, "Ghost", new Need[0], false, "nopassword", 10);
        User result = userDAO.updateUser(nonExistentUser);
        assertNull(result);
    }


    @Test
    public void testDeleteUser_ExistingUser() throws IOException {
        boolean result = userDAO.deleteUser(1);

        assertTrue(result);
        assertNull(userDAO.getUser(1));
    }


    @Test
    public void testDeleteUser_NonExistentUser() throws IOException {
        boolean result = userDAO.deleteUser(999);
        assertFalse(result);
    }
    @Test
    public void testFindUsers_WithMatchingName() throws IOException {
        User[] expectedUsers = { testUsers[0] };
        User[] actualUsers = userDAO.findUsers("Alice");
        assertArrayEquals(expectedUsers, actualUsers);
    }
    @Test
    public void testFindUsers_NoMatchingName() throws IOException {
        User[] actualUsers = userDAO.findUsers("NonExistent");
        assertEquals(0, actualUsers.length);
    }
    /**
     * Helper method to inject a mock NeedDAO into the UserFileDAO using reflection.
     */
    private void injectMockNeedDAO(NeedDAO mockNeedDAO) throws Exception {
        Field needDaoField = UserFileDAO.class.getDeclaredField("needDao");
        needDaoField.setAccessible(true);
        needDaoField.set(userDAO, mockNeedDAO);
    }
}
