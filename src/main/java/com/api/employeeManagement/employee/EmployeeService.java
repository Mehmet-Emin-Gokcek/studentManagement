package com.api.employeeManagement.employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

//@service: means service class will serve as a spring component (e.i. a spring bean)
//that will be used at student controller
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public void addEmployee(Employee employee) {
       Optional<Employee> employeeOptional =  employeeRepository.findEmployeebyEmail(employee.getEmail());

       if(employeeOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) {
            throw new IllegalStateException(
                    "employee with id " + employeeId + " does not exist");
        }
        employeeRepository.deleteById(employeeId);
    }

    public Optional<Employee> getEmployee(Long employeeId) {

        boolean exists = employeeRepository.existsById(employeeId);

        if(!exists) {
            throw new IllegalStateException(
                    "employee with id " + employeeId + " does not exist");
        }
        return employeeRepository.findById(employeeId);
    }

    /*public void updateEmployee(Employee employee, Long employeeId) {
        //check to see if an employee with 'employeeId' exists
        Employee employeeToUpdate = employeeRepository.getById(employeeId);
        if(employeeToUpdate == null) {
            throw new IllegalStateException(
                    "employee with id " + employeeId + " does not exist");
        }
        //check to see if email provided already exists in the database
        //each employee must have unique email address
        Optional<Employee> employeeOptional =  employeeRepository.findEmployeebyEmail(employee.getEmail());
        if(employeeOptional.isPresent() && !Objects.equals(employee.getEmail(), employeeToUpdate.getEmail())) {
            throw new IllegalStateException("email taken");
        }

        //check to see if provided name is null or empty
        if(employee.getName() != null && employee.getName() .length() > 0 ) {
            employeeToUpdate.setName(employee.getName());
        }
        //check to see if provided email is null or empty
        if(employee.getEmail() != null && employee.getEmail() .length() > 0) {
            employeeToUpdate.setEmail(employee.getEmail());
        }
        employeeRepository.save(employeeToUpdate);
    }*/

    //@Transactional: Entity goes into manage state
    //So, we don't need to invoke any of the repository methods to update the employee object
    @Transactional
    public void updateEmployee(Long employeeId, String name, String email) {

        //check to see if employee with 'employeeId' exists or throw exception
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException(
                        "employee with id " + employeeId + " does not exist" ));

        //check to see if name provided is empty/null and isn't the same as the current name in the database
        if(name != null && name.length() > 0 && !Objects.equals(employee.getName(), name) ){
            employee.setName(name);
        }

        //check to see if email provided is empty/null and isn't the same as the current email in the database
        if(email != null && email.length() > 0 && !Objects.equals(employee.getEmail(), email) ){

            //check to see if email provided already exists in the database
            //each employee must have unique email address
            Optional<Employee> employeeOptional = employeeRepository.findEmployeebyEmail(email);
            if(employeeOptional.isPresent()){
                throw  new IllegalStateException("email taken");
            }
            employee.setEmail(email);
        }
    }
}
