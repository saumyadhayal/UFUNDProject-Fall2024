package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.ufund.api.ufundapi.controller.NeedController;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Tag("Controller")
public class NeedsControllerTest {

    private NeedDAO needDao;  
    private NeedController needController;

    @BeforeEach
    public void setup() {
        needDao = mock(NeedDAO.class);  // Mock the DAO
        needController = new NeedController(needDao);  // Inject the mock into the controller
    }

        @Test
    public void testCreateNeed_Success() throws IOException {
        // Arrange
        Need newNeed = new Need(1, "backpack", 20, 5, "extra");
        when(needDao.createNeed(newNeed)).thenReturn(newNeed);

        // Act
        ResponseEntity<Need> response = needController.createNeed(newNeed);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newNeed, response.getBody());
    }

    @Test
    public void testCreateNeed_InternalServerError() throws IOException {
        // Arrange: Simulate an IOException
        Need newNeed = new Need(1, "backpack", 20, 5, "extra");
        when(needDao.createNeed(newNeed)).thenThrow(new IOException());

        // Act
        ResponseEntity<Need> response = needController.createNeed(newNeed);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testUpdateNeed_Success() throws IOException {
        // Arrange
        Need updatedNeed = new Need(1, "backpack", 30, 10, "essential");
        when(needDao.updateNeed(updatedNeed)).thenReturn(updatedNeed);

        // Act
        ResponseEntity<Need> response = needController.updateNeed(updatedNeed);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNeed, response.getBody());
    }

    @Test
    public void testUpdateNeed_NotFound() throws IOException {
        // Arrange
        Need updatedNeed = new Need(1, "backpack", 30, 10, "essential");
        when(needDao.updateNeed(updatedNeed)).thenReturn(null);

        // Act
        ResponseEntity<Need> response = needController.updateNeed(updatedNeed);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testUpdateNeed_InternalServerError() throws IOException {
        // Arrange: Simulate an IOException
        Need updatedNeed = new Need(1, "backpack", 30, 10, "essential");
        when(needDao.updateNeed(updatedNeed)).thenThrow(new IOException());

        // Act
        ResponseEntity<Need> response = needController.updateNeed(updatedNeed);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testDeleteNeed_Success() throws IOException {
        // Arrange
        when(needDao.deleteNeed(1)).thenReturn(true);

        // Act
        ResponseEntity<Need> response = needController.deleteNeed(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testDeleteNeed_NotFound() throws IOException {
        // Arrange
        when(needDao.deleteNeed(1)).thenReturn(false);

        // Act
        ResponseEntity<Need> response = needController.deleteNeed(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testDeleteNeed_InternalServerError() throws IOException {
        // Arrange: Simulate an IOException
        when(needDao.deleteNeed(1)).thenThrow(new IOException());

        // Act
        ResponseEntity<Need> response = needController.deleteNeed(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetNeed_Success() throws IOException {
        // Arrange
        Need expectedNeed = new Need(1, "backpack", 20, 5, "extra");
        when(needDao.getNeed(1)).thenReturn(expectedNeed);

        // Act
        ResponseEntity<Need> response = needController.getNeed(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNeed, response.getBody());
    }

    @Test
    public void testGetNeed_NotFound() throws IOException {
        // Arrange
        when(needDao.getNeed(1)).thenReturn(null);

        // Act
        ResponseEntity<Need> response = needController.getNeed(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetNeed_InternalServerError() throws IOException {
        // Arrange: Simulate an IOException
        when(needDao.getNeed(1)).thenThrow(new IOException());

        // Act
        ResponseEntity<Need> response = needController.getNeed(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testGetNeeds_Success() throws IOException {
        // Arrange
        Need[] expectedNeeds = { new Need(1, "backpack", 20, 5, "extra") };
        when(needDao.getcupboard()).thenReturn(expectedNeeds);

        // Act
        ResponseEntity<Need[]> response = needController.getneeds();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNeeds, response.getBody());
    }

    @Test
    public void testGetNeeds_InternalServerError() throws IOException {
        // Arrange: Simulate an IOException
        when(needDao.getcupboard()).thenThrow(new IOException());

        // Act
        ResponseEntity<Need[]> response = needController.getneeds();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testSearchCupboard_Success() throws IOException {
        // Arrange
        Need[] expectedNeeds = { new Need(1, "backpack", 20, 5, "extra") };
        when(needDao.findcupboard("back")).thenReturn(expectedNeeds);

        // Act
        ResponseEntity<Need[]> response = needController.searchcupboard("back");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedNeeds, response.getBody());
    }

    @Test
    public void testSearchCupboard_InternalServerError() throws IOException {
        // Arrange: Simulate an IOException
        when(needDao.findcupboard("back")).thenThrow(new IOException());

        // Act
        ResponseEntity<Need[]> response = needController.searchcupboard("back");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

}
