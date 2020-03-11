package com.example.demo.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.model.User;

public class  UserProcessor implements ItemProcessor<User, User> {
	 private static final Logger log = LoggerFactory.getLogger(UserProcessor.class);


		@Override
		public User process(User user) throws Exception {
		 final String firstName = user.getFirstName().toUpperCase();
		 final String lastName = user.getLastName().toUpperCase();
		 final String middleName = user.getMiddleName().toUpperCase();
		 final String phoneNumber = user.getPhoneNumber().toUpperCase();
		 final String note = user.getNote().toUpperCase();
		 final User transformedUser = new User(firstName,middleName,lastName,phoneNumber,note);
		 return transformedUser;
		}
}
