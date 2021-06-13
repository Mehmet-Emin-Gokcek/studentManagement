package com.api.employeeManagement.employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class EmployeeConfig {

    @Bean
    CommandLineRunner commandLineRunner (EmployeeRepository repository)
    {
        return args -> {
            ArrayList<Employee> employees = new ArrayList<>();

            employees.add(new Employee("Mariam", "mariam.jamal@gmail.com",
                            LocalDate.of(2000, Month.JUNE, 12)
                   ));
            employees.add(new Employee("Alex", "alex@gmail.com",
                    LocalDate.of(2002, Month.FEBRUARY, 22)
            ));

            employees.add(new Employee("John", "john@gmail.com",
                    LocalDate.of(1999, Month.NOVEMBER, 02)
            ));

            //check to see if email provided already exists in the database
            //each employee must have unique email address
            Consumer<Employee> saveEmployee = new Consumer<Employee>() {
                @Override
                public void accept(Employee employee) {
                    Optional<Employee> employeeOptional = repository.findEmployeebyEmail(employee.getEmail());
                    if(!employeeOptional.isPresent()) {
                        repository.save(employee);
                    }
                }
            };
            employees.forEach(saveEmployee);
        };
    }
}
