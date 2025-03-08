package com.sandeep.respository;

import com.sandeep.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcelSaveRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT e FROM EmployeeEntity e WHERE e.id > :rowNumber")
    List<EmployeeEntity> findAllAfterRow(@Param("rowNumber") Integer rowNumber);
}
