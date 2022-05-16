package com.macky.todo.v2.utils;

import com.macky.todo.v2.models.User;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.util.Optional;

public class Validate {

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher match = pattern.matcher(email);
        return match.matches();
    }

    public static boolean isValidAccessData(Optional<User> user, String email) {

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User is not found.");
        }
        Optional<String> emailServ = user.map(User::getEmail);

        return emailServ.get().equals(email);
    }

}
