package com.example.demo22.Controller;

import com.example.demo22.Model.Log;
import com.example.demo22.Rep.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/log")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    // Create or Add a New Log Entry
    @PostMapping(path = "/add")
    public @ResponseBody String addNewLog(@RequestParam(required = false) String ip,
                                          @RequestParam(required = false) String level,
                                          @RequestParam(required = false) String address,
                                          @RequestParam(required = false) String preValue,
                                          @RequestParam(required = false) String value,
                                          @RequestParam(required = false) String date,
                                          @RequestParam(required = false) String country,
                                          @RequestParam(required = false) String status) {
        Log log = new Log();
        log.setIp(ip);
        log.setLevel(level);
        log.setAddress(address);
        log.setPreValue(preValue);
        log.setValue(value);
        log.setDate(date != null ? java.sql.Date.valueOf(date) : null);
        log.setCountry(country);
        log.setStatus(status);

        logRepository.save(log);
        return "Log saved successfully.";
    }

    // Retrieve All Logs
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Log> getAllLogs() {
        System.out.println("main");
        return logRepository.findAll();
    }

    // Retrieve a Log Entry by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Log> getLogById(@PathVariable("id") int id) {
        return logRepository.findById(id);
    }

    // Update an Existing Log Entry
    @PutMapping(path = "/update/{id}")
    public @ResponseBody String updateLog(@PathVariable("id") int id,
                                          @RequestParam(required = false) String ip,
                                          @RequestParam(required = false) String level,
                                          @RequestParam(required = false) String address,
                                          @RequestParam(required = false) String preValue,
                                          @RequestParam(required = false) String value,
                                          @RequestParam(required = false) String date,
                                          @RequestParam(required = false) String country,
                                          @RequestParam(required = false) String status) {
        if (!logRepository.existsById(id)) {
            return "Log with ID " + id + " does not exist.";
        }
        Log log = logRepository.findById(id).orElse(null);
        if (log != null) {
            log.setIp(ip);
            log.setLevel(level);
            log.setAddress(address);
            log.setPreValue(preValue);
            log.setValue(value);
            log.setDate(date != null ? java.sql.Date.valueOf(date) : null);
            log.setCountry(country);
            log.setStatus(status);
            logRepository.save(log);
            return "Log updated successfully.";
        }
        return "Failed to update log.";
    }

    // Delete a Log Entry by ID
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteLog(@PathVariable("id") int id) {
        try {
            if (!logRepository.existsById(id)) {
                throw new IllegalArgumentException("Log with ID " + id + " does not exist.");
            }
            logRepository.deleteById(id);
            return "Log deleted successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete log: " + e.getMessage();
        }
    }
}
