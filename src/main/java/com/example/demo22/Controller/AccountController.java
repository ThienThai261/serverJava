package com.example.demo22.Controller;

import com.example.demo22.Model.AccessLevels;
import com.example.demo22.Model.Accounts;
import com.example.demo22.Rep.AccessLevelsRepository;
import com.example.demo22.Rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/demo")
@CrossOrigin(origins = "*")
public class AccountController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessLevelsRepository accessLevelsRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String email,
                                           @RequestParam String fullname,
                                           @RequestParam String phone,
                                           @RequestParam int status) {
        try {
            System.out.println("Received signup request:");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Fullname: " + fullname);
            System.out.println("Phone: " + phone);
            System.out.println("Status: " + status);
            // Create and save the account
            Accounts account = new Accounts();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setFullname(fullname);
            account.setPhone(phone);
            account.setStatus(status);

            Accounts savedAccount = userRepository.save(account);

            // Create and save the access level
            AccessLevels accessLevel = new AccessLevels();
            accessLevel.setRole(1); // Default role = 1
            accessLevel.setIdAccount(savedAccount.getId());

            accessLevelsRepository.save(accessLevel);

            return "Account and Access Level saved successfully.";
        } catch (Exception e) {
            System.out.println("Error in signup: " + e.getMessage());
            e.printStackTrace();
            return "Error while saving account: " + e.getMessage();
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Accounts> getAllUsers() { // Updated to 'Accounts'
        return userRepository.findAll();
    }
    @GetMapping("/id")
    public @ResponseBody Optional<Accounts> getID(@RequestParam int id) {
        return userRepository.findById(id);
    }
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteAccount(@PathVariable("id") int id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new IllegalArgumentException("User with ID " + id + " does not exist.");
            }
            userRepository.deleteById(id);
            System.out.println("Successfully deleted user with ID: " + id);
            return "Account deleted successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete account: " + e.getMessage();
        }
    }

    @PutMapping("/updateStatus/{id}")
    public @ResponseBody String updateAccountStatus(
            @PathVariable("id") int id,
            @RequestParam int status) {
        try {
            Optional<Accounts> accountOptional = userRepository.findById(id);

            if (!accountOptional.isPresent()) {
                throw new IllegalArgumentException("Account with ID " + id + " does not exist.");
            }

            Accounts account = accountOptional.get();
            account.setStatus(status);
            userRepository.save(account);

            return "Account status updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update account status: " + e.getMessage();
        }
    }
    @PutMapping("/changePassword/{id}")
    public @ResponseBody String changePassword(
            @PathVariable("id") int id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        try {
            Optional<Accounts> accountOptional = userRepository.findById(id);

            if (!accountOptional.isPresent()) {
                throw new IllegalArgumentException("Account with ID " + id + " does not exist.");
            }

            Accounts account = accountOptional.get();

            // Verify old password matches
            if (!account.getPassword().equals(oldPassword)) {
                return "Current password is incorrect";
            }

            // Update password
            account.setPassword(newPassword);
            userRepository.save(account);

            return "Password updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update password: " + e.getMessage();
        }
    }

    @PutMapping("/updateInfo/{id}")
    public @ResponseBody String updateUserInfo(
            @PathVariable("id") int id,
            @RequestParam String fullname,
            @RequestParam String phone) {
        try {
            Optional<Accounts> accountOptional = userRepository.findById(id);

            if (!accountOptional.isPresent()) {
                throw new IllegalArgumentException("Account with ID " + id + " does not exist.");
            }

            Accounts account = accountOptional.get();
            account.setFullname(fullname);
            account.setPhone(phone);
            userRepository.save(account);

            return "User information updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update user information: " + e.getMessage();
        }
    }
}
