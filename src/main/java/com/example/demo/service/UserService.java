package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public  interface UserService{
	public  List<User> findByAllUsers();
	 User save(User user);

public List<User>findByUser_id(long id);
 public List<User> findByFirstName(String firstName);
 public List<User> findByLastName(String lastName) ;
 public void deleteUser(Long user_id)throws RecordNotFoundException;
 
 public ResponseEntity<User> updateDetails(Long user_id, User userdetails) throws RecordNotFoundException;

}