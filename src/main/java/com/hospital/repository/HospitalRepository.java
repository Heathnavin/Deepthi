package com.hospital.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hospital.entities.Hospital;

@Repository("hospitalRepository")
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
	boolean existsByUserName(String userName);
	Optional<Hospital> findByUserName(String userName);
	Optional<Hospital> findByEmail(String email);
	boolean existsByEmail(String email);
}

