package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.model.Need;

/**
 * Handles the REST API requests for the Need resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("/needs")
public class NeedController {
    private static final Logger LOG = Logger.getLogger(NeedController.class.getName());
    private NeedDAO NeedDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param NeedDao The {@link NeedDAO Need Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public NeedController(NeedDAO NeedDao) {
        this.NeedDao = NeedDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Need Need} for the given id
     * 
     * @param id The id used to locate the {@link Need Need}
     * 
     * @return ResponseEntity with {@link Need Need} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        LOG.info("GET /needs/" + id);
        try {
            Need Need = NeedDao.getNeed(id);
            if (Need != null)
                return new ResponseEntity<Need>(Need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Need cupboard}
     * 
     * @return ResponseEntity with array of {@link Need Need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getneeds() {
        LOG.info("GET /needs");
        try {
            Need[] cupboard = NeedDao.getcupboard();
            return new ResponseEntity<Need[]>(cupboard,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for all {@linkplain Need cupboard} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Need cupboard}
     * 
     * @return ResponseEntity with array of {@link Need Need} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all cupboard that contain the text "ma"
     * GET http://localhost:8080/cupboard/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Need[]> searchcupboard(@RequestParam String name) {
        LOG.info("GET /needs/?name="+name);

        // Replace below with your implementation
        try {
            Need[] name_cupboard = NeedDao.findcupboard(name);
            return new ResponseEntity<Need[]>(name_cupboard,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Need Need} with the provided Need object
     * 
     * @param Need - The {@link Need Need} to create
     * 
     * @return ResponseEntity with created {@link Need Need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need Need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need Need) {          //
        LOG.info("POST /needs " + Need);

        try{
            Need new_Need = NeedDao.createNeed(Need);
            return new ResponseEntity<Need>(new_Need, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Need Need} with the provided {@linkplain Need Need} object, if it exists
     * 
     * @param Need The {@link Need Need} to update
     * 
     * @return ResponseEntity with updated {@link Need Need} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need Need) {             //
        LOG.info("PUT /needs " + Need);

        try{
            Need updated_Need = NeedDao.updateNeed(Need);
            if(updated_Need != null){
                return new ResponseEntity<Need>(updated_Need, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Need Need} with the given id
     * 
     * @param id The id of the {@link Need Need} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {       //
        LOG.info("DELETE /needs/" + id);
        
        try{
            boolean delete_Need = NeedDao.deleteNeed(id);
            if(delete_Need){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
