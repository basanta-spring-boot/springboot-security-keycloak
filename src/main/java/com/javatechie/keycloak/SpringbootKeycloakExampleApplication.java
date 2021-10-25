package com.javatechie.keycloak;

import com.javatechie.keycloak.entity.Employee;
import com.javatechie.keycloak.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
@RequestMapping("/employees")
public class SpringbootKeycloakExampleApplication {

    @Autowired
    private EmployeeRepository repository;

    @PostConstruct
    public void initEmployees() {
        repository.saveAll(Stream.of(
                new Employee("basant", 50000),
                new Employee("santosh", 80000),
                new Employee("Ajay", 60000)
        ).collect(Collectors.toList()));
    }

    @GetMapping("/{employeeId}")
    @RolesAllowed("user")
    public ResponseEntity<Employee> getEmployee(@PathVariable int employeeId) {
        return ResponseEntity.ok(repository.findById(employeeId).get());
    }

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(repository.findAll());
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootKeycloakExampleApplication.class, args);
    }

}
