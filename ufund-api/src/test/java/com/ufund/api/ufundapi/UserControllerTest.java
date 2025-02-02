
package com.ufund.api.ufundapi;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.UserController;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new UserController object and inject a mock UserDAO.
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException {
        // Setup
        User user = new User(99, "User", null, false, "password", 10);
        when(mockUserDAO.getUser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws IOException {
        // Setup
        int userId = 99;
        when(mockUserDAO.getUser(userId)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws IOException {
        // Setup
        int userId = 99;
        doThrow(new IOException()).when(mockUserDAO).getUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException {
        // Setup
        User user = new User(0, "User 2", null, false, "password", 10);
        when(mockUserDAO.createUser(user)).thenReturn(user);
        when(mockUserDAO.findUsers(user.getName())).thenReturn(new User[]{});

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException {
        // Setup
        User user = new User(99, "User", null, false, "password", 10);
        when(mockUserDAO.findUsers(user.getName())).thenReturn(new User[]{user});

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws IOException {
        // Setup
        User user = new User(99, "User", null, false, "password", 10);
        doThrow(new IOException()).when(mockUserDAO).createUser(user);
        when(mockUserDAO.findUsers(user.getName())).thenReturn(new User[]{});

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws IOException {
        // Setup
        User user = new User(99, "User", null, false, "password", 10);
        when(mockUserDAO.updateUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUserFailed() throws IOException {
        // Setup
        User user = new User(99, "User", null, false, "password", 10);
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserHandleException() throws IOException {
        // Setup
        User user = new User(99, "User", null, false, "password", 10);
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException {
        // Setup
        User[] users = {
            new User(99, "User A", null, false, "password", 10),
            new User(100, "User B", null, false, "password2", 10)
        };
        when(mockUserDAO.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchUsers() throws IOException {
        // Setup
        String searchString = "User";
        User[] users = {
            new User(99, "User A", null, false, "password", 10),
            new User(100, "User B", null, false, "password2", 10)
        };
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testSearchUsersHandleException() throws IOException {
        // Setup
        String searchString = "User";
        doThrow(new IOException()).when(mockUserDAO).findUsers(searchString);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException {
        // Setup
        int userId = 123;
        when(mockUserDAO.deleteUser(userId)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException {
        // Setup
        int userId = 123;
        when(mockUserDAO.deleteUser(userId)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException {
        // Setup
        int userId = 123;
        doThrow(new IOException()).when(mockUserDAO).deleteUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
