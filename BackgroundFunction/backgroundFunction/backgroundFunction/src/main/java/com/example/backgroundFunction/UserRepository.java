package com.example.backgroundFunction;

import org.springframework.data.repository.CrudRepository;

import com.example.backgroundFunction.User;

//AUTO IMPLEMENTED by Spring to a Bean called userRepository
//Crud refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, Integer> {

}