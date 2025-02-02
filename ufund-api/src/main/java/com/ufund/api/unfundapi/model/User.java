package com.ufund.api.ufundapi.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
    static final String STRING_FORMAT = "user [id=%d, name=%s, cart=%s, isAdmin=%b, password=%s, totalSpent=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("cart") private Need[] cart;
    @JsonProperty("isAdmin") private Boolean isAdmin;
    @JsonProperty("password") private String password;
    @JsonProperty("totalSpent") private int totalSpent;

    /**
     * @param id ID of the user
     * @param name Name of the User
     * @param cart User's cart
     * @param isAdmin Is it an admin account? True or False
     * @param password Password for the User
     * @param totalSpent totalSpent for the User
     * 
     */
    public User(
        @JsonProperty("id") int id, 
        @JsonProperty("name") String name, 
        @JsonProperty("cart") Need[] cart, 
        @JsonProperty("isAdmin") Boolean isAdmin, 
        @JsonProperty("password") String password,
        @JsonProperty("totalSpent") int totalSpent)

    {
        this.id = id;
        this.name = name;
        this.cart = cart;
        this.isAdmin = isAdmin;
        this.password = password;
        this.totalSpent = totalSpent;
    }

    public int getId() {return id;}

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public Need[] getCart() {return cart;}

    public void setAdmin(Boolean perms) {this.isAdmin = perms;}

    public Boolean getAdmin() {return isAdmin;}

    public String getPassword() {return password;}

    public int getTotalSpent() {return totalSpent;}

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id &&
            name.equals(user.name) &&
            Arrays.equals(cart, user.cart) &&
            isAdmin.equals(user.isAdmin) &&
            password.equals(user.password) &&
            totalSpent == user.totalSpent;
}

@Override
public int hashCode() {
    int result = Objects.hash(id, name, isAdmin, password, totalSpent);
    result = 31 * result + Arrays.hashCode(cart);
    return result;
}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, Arrays.toString(cart), isAdmin, password, totalSpent);    }
}