package com.demo.ejb;

import com.demo.jpa.User;
import java.util.Collection;
import jakarta.ejb.Local;

/**
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Local
public interface UserService {
     User findByID(int id);
    
    Collection<User> getAll();
    
    int insert(User user);
    void update(User user);
    void delete(User user);

    public User fakeLogin(String uname, String password);
}
