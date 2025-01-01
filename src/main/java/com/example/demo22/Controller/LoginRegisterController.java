package com.example.demo22.Controller;

import com.example.demo22.Model.Accounts;
import com.example.demo22.Rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/accounts")
@CrossOrigin(origins = "*")
public class LoginRegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/register")
    public @ResponseBody String register(@RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String email,
                                         @RequestParam String fullname,
                                         @RequestParam String phone) {
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                return "Error: Username already exists.";
            }

            if (userRepository.findByEmail(email).isPresent()) {
                return "Error: Email already registered.";
            }

            Accounts account = new Accounts();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setFullname(fullname);
            account.setPhone(phone);
            account.setStatus(1); // Default status for new accounts

            userRepository.save(account);
            return "Registration successful.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping(path = "/login")
    public @ResponseBody Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Accounts> account = userRepository.findByUsername(username);

            if (account.isPresent() && account.get().getPassword().equals(password)) {
                Accounts user = account.get();
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("id", user.getId());
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("fullname", user.getFullname());
                response.put("phone", user.getPhone());
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error during login: " + e.getMessage());
        }

        return response;
    }
}
