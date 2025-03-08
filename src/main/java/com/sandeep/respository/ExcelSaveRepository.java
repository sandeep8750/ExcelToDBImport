package com.sandeep.respository;

import com.sandeep.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelSaveRepository extends JpaRepository<EmployeeEntity,Long> {
}
