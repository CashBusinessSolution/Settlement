package com.hps.fee.repositories;

import com.hps.fee.models.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository <Fee, Long> {
}