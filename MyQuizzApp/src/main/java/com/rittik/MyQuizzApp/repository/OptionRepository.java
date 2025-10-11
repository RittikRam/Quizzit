package com.rittik.MyQuizzApp.repository;

import com.rittik.MyQuizzApp.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
