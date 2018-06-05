package com.f.dao;

import com.f.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface EmployeeDao extends GenericCrudMapper<Employee, Integer> {
    List<Employee> getUserByCondition(Map<String, Object> map);
}
