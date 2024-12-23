package com.example.demo22.Controller;

import com.example.demo22.Model.Accounts;
import com.example.demo22.Rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/demo")
@CrossOrigin(origins = "*")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String email,
                                           @RequestParam String fullname,
                                           @RequestParam String phone,
                                           @RequestParam int status) {
        Accounts accounts = new Accounts(); // Updated to 'Accounts'
        accounts.setUsername(username);
        accounts.setPassword(password);
        accounts.setEmail(email);
        accounts.setFullname(fullname);
        accounts.setPhone(phone);;
        accounts.setStatus(status);

        userRepository.save(accounts);
        return "Saved";
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


}
