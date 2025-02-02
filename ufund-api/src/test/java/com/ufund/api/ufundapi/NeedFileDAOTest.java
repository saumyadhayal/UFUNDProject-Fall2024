package com.ufund.api.ufundapi;

//imports needed for testing
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.beans.Transient;
import java.io.IOException;
import java.io.File;
import java.lang.reflect.Array;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedFileDAO;

@Tag("Persistence")
public class NeedFileDAOTest {
    NeedFileDAO needDAO;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupNeedDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        
        testNeeds = new Need[2];
        testNeeds[0] = new Need(1, "sweater", 20, 1, "clothing");
        testNeeds[1] = new Need(2, "ball", 8, 3, "toy");

        when(mockObjectMapper.readValue(new File("whatever.txt"), Need[].class)).thenReturn(testNeeds);
        needDAO = new NeedFileDAO("whatever.txt", mockObjectMapper);
    }
    @Test
    public void testGetCupboard() throws IOException {
        Need[] expected = new Need[2];
        expected[0] = new Need(1, "sweater", 20, 1, "clothing");
        expected[1] = new Need(2, "ball", 8, 3, "toy");

        Need[] actual = needDAO.getcupboard();
        assertArrayEquals(expected, actual);
    }
    @Test
    public void testFindCupboard_WithMatchingText() throws IOException {
        // If containsText is "sweater", it should return only the Need with "sweater" in the name
        Need[] expectedNeeds = {new Need(1, "sweater", 20, 1, "clothing")};
        // Call the findcupboard method with containsText = "sweater"
        Need[] result = needDAO.findcupboard("sweater");
        // Verify that only the matching Need is returned
        assertArrayEquals(expectedNeeds, result, "The findcupboard method should return the Needs that match 'sweater'.");
    }
    @Test
    public void testGetNeed() throws IOException {
        Need expectedNeed = new Need(1, "sweater", 20, 1, "clothing");
        Need actualNeed = needDAO.getNeed(1);
        assertEquals(expectedNeed, actualNeed);

        Need nonExistentNeed = needDAO.getNeed(3);
        assertEquals(null, nonExistentNeed);
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
    public void testCreateNeed() throws IOException {       //
        Need[] initialNeeds = new Need[2];
        initialNeeds[0] = new Need(1, "sweater", 20, 1, "clothing");
        initialNeeds[1] = new Need(2, "ball", 8, 3, "toy");

        mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.readValue(new File("whatever.txt"), Need[].class)).thenReturn(initialNeeds);

        needDAO = new NeedFileDAO("whatever.txt", mockObjectMapper);
        // create new need and call method
        Need newNeed = new Need(0, "jacket", 50, 1, "clothing");
        Need createdNeed = needDAO.createNeed(newNeed);
        // 
        assertEquals(3, createdNeed.getId());
        assertEquals("jacket", createdNeed.getName());
        assertEquals(50, createdNeed.getPrice());
        assertEquals(1, createdNeed.getQuantity());
        assertEquals("clothing", createdNeed.getType());
        // verify new need matches id
        Need cupboardNeed = needDAO.getNeed(3);
        assertEquals(createdNeed, cupboardNeed);
        // check that cupboard contains items
        Need[] allNeeds = needDAO.getcupboard();
        assertEquals(3, allNeeds.length);
    }
    @Test
    public void testUpdateNeed() throws IOException { //
        Need updatedNeed = new Need(1, "jacket", 50, 2, "clothing");
        Need result = needDAO.updateNeed(updatedNeed);

        assertEquals(updatedNeed, result);

        Need cupboardNeed = needDAO.getNeed(1);
        assertEquals(updatedNeed, cupboardNeed);
    }
    @Test
    public void testUpdateNeed_NonExistentNeed() throws IOException {
        // Attempt to update a Need with a non-existent ID
        int nonExistentId = 3;
        Need nonExistentNeed = new Need(nonExistentId, "jacket", 50, 2, "clothing");
        // Call updateNeed with a non-existent Need
        Need result = needDAO.updateNeed(nonExistentNeed);
        // Verify that the result is null
        assertNull(result, "Expected null because the Need does not exist in the cupboard");
    }
    @Test
    public void testDeleteNeed_ExistingNeed() throws IOException {
        assertTrue(needDAO.getNeed(1) != null);

        boolean result = needDAO.deleteNeed(1);

        assertTrue(result);
        assertTrue(needDAO.getNeed(1) == null);
    }
    @Test
    public void testDeleteNeed_NeedDoesNotExist() throws IOException {
        // Attempt to delete a Need with a non-existent ID
        int nonExistentId = 999;
        // Call deleteNeed with the non-existent ID
        boolean result = needDAO.deleteNeed(nonExistentId);
        // Verify that the result is false, indicating the Need was not found
        assertFalse(result, "Expected false because the Need does not exist in the cupboard");
    }
}
