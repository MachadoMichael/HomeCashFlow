package com.machado.HomeCashFlow.services.user;

import com.machado.HomeCashFlow.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

     User save(User user);

     List<User> getAll();

     Optional<User> getOne(UUID user_id);

     User getByEmail(String email);

     void delete(User user);
}
