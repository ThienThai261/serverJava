package com.example.demo22.Controller;

import com.example.demo22.Model.AccessLevels;
import com.example.demo22.Model.Accounts;
import com.example.demo22.Rep.AccessLevelsRepository;
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

    @Autowired
    private AccessLevelsRepository accessLevelsRepository;

    @GetMapping("/access-level/{accountId}")
    public @ResponseBody Map<String, Object> getAccessLevel(@PathVariable int accountId) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("Fetching access level for account ID: " + accountId);

            Optional<AccessLevels> accessLevel = accessLevelsRepository.findAll()
                    .stream()
                    .filter(level -> level.getIdAccount() == accountId)
                    .findFirst();

            if (accessLevel.isPresent()) {
                System.out.println("Access level found for account " + accountId + ": " + accessLevel.get().getRole());
                response.put("success", true);
                response.put("role", accessLevel.get().getRole());
            } else {
                System.out.println("No access level found for account ID: " + accountId);
                response.put("success", false);
                response.put("message", "Access level not found");
            }
        } catch (Exception e) {
            System.out.println("Error fetching access level for account " + accountId + ": " + e.getMessage());
            response.put("success", false);
            response.put("message", "Error fetching access level: " + e.getMessage());
        }

        return response;
    }

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
            System.out.println("Attempting login for username: " + username);
            Optional<Accounts> account = userRepository.findByUsername(username);

            if (account.isPresent() && account.get().getPassword().equals(password)) {
                Accounts user = account.get();
                System.out.println("Login successful for user: " + username);

                // Fetch access level
                Map<String, Object> accessLevelResponse = getAccessLevel(user.getId());

                response.put("success", true);
                response.put("message", "Login successful");
                response.put("id", user.getId());
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("fullname", user.getFullname());
                response.put("phone", user.getPhone());

                // Add access level information to response
                if ((Boolean)accessLevelResponse.get("success")) {
                    response.put("role", accessLevelResponse.get("role"));
                } else {
                    response.put("role", 0); // Default role if not found
                    System.out.println("No access level found for user " + username + ", using default role");
                }
            } else {
                System.out.println("Login failed for username: " + username);
                response.put("success", false);
                response.put("message", "Invalid username or password");
            }
        } catch (Exception e) {
            System.out.println("Error during login for username " + username + ": " + e.getMessage());
            response.put("success", false);
            response.put("message", "Error during login: " + e.getMessage());
        }

        return response;
    }
}