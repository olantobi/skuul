package com.liferon.skuul.repository;

import com.liferon.skuul.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @project web-metering
 * @created by tobi on 2019-06-04
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, String> {
}
