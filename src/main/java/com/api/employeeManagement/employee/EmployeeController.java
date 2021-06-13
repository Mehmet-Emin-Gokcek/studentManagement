package com.api.employeeManagement.employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    //@Autowired: tells the spring to instantiate studentService which is a spring component/spring bean
    // and inject into the studentController as a dependency (e.i. dependency injection)
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }


    @GetMapping
    public List<Employee> getEmployees(){

        return employeeService.getEmployees();
    }


    @GetMapping(path = "{employeeId}")
    public Optional<Employee> getEmployee(@PathVariable("employeeId") Long employeeId){

        return employeeService.getEmployee(employeeId);
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

/*    @PutMapping(path = "{employeeId}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") Long employeeId){
        employeeService.updateEmployee(employee, employeeId);
    }*/

    //Put Request path: employee/1?name=Maria&email=mariam.jamal@gmail.com
    @PutMapping(path="{employeeId}")
    public void updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        employeeService.updateEmployee(employeeId, name, email);

    }
}

