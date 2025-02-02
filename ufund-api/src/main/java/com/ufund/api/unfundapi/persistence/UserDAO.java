package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.User;

public interface UserDAO {

     User createUser(User user) throws IOException;

     boolean deleteUser(int id) throws IOException;

     User[] getUsers() throws IOException;

     User[] findUsers(String name) throws IOException;
     
     User updateUser(User user) throws IOException;

     User getUser(int id) throws IOException;

}