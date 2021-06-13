package com.api.employeeManagement.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findEmployeebyEmail(@Param("email") String email);


    //@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
    //User findUserByStatusAndName(Integer status, String name);
}
