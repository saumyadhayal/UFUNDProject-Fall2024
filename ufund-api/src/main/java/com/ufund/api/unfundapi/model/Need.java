package com.ufund.api.ufundapi.model;

import java.util.Objects;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Need entity
 * 
 * @author SWEN Faculty
 */
public class Need {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [id=%d, name=%s, price=%.2f, quantity=%d, type=%s]";     //

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;       //
    @JsonProperty("quantity") private int quantity;    //
    @JsonProperty("type") private String type;         //

    /**
     * Create a Need with the given id and name
     * @param id The id of the Need
     * @param name The name of the Need
     * @param price the price of the Need    //
     * @param quantity the quantity of the need    //
     * @param type the type of the need       //
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price,         //
    @JsonProperty("quantity") int quantity, @JsonProperty("type") String type) {         //
        this.id = id;
        this.name = name;
        this.price = price;     //
        this.quantity = quantity;   //
        this.type = type;      //
    }

    /**
     * Retrieves the id of the Need
     * @return The id of the Need
     */
    public int getId() {return id;}

    /**
     * Sets the name of the Need - necessary for JSON object to Java object deserialization
     * @param name The name of the Need
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the Need
     * @return The name of the Need
     */
    public String getName() {return name;}

    /**
     * Retrieves the price of the Need.
     * @return the price of the Need
     */
    public double getPrice() {return price;}             //

    /**
     * Sets the price of the Need - necessary for JSON object to Java object deserialization
     * @param price The price of the Need
     */
    public void setPrice(double price) {this.price = price;}     //

    /**
     * Retrieves the quantity of the Need
     * @return the quantity of the Need
     */
    public int getQuantity() {return quantity;}             //

    /**
     * Sets the quantity of the Need - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the Need
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}     //

    /**
     * Retrieves the type of the Need
     * @return the type of the Need
     */
    public String getType() {return type;}             //

    /**
     * Sets the type of the Need - necessary for JSON object to Java object deserialization
     * @param type The type of the Need
     */
    public void setType(String type) {
        this.type = type;
    } //

    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, price, quantity, type); //
    }
    
    /**
     * 
     * @return true if the types are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Need need = (Need) o;
        return id == need.id && price == need.price && quantity == need.quantity
                && Objects.equals(name, need.name) && Objects.equals(type, need.type);
    }

    /**
     * 
     * @return hashcode of the need
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, type);
    }
}