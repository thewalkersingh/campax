package com.campax.repository;

import com.campax.entity.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
	
	// ── Lookup ────────────────────────────────────────────────────────────────
	Optional<UserIdentity> findByEmail(String email);
	
	Optional<UserIdentity> findByPhone(String phone);
	
	// ── Existence ─────────────────────────────────────────────────────────────
	// Use these before creating Teacher/Student/Parent to avoid duplicate identity
	boolean existsByEmail(String email);
	
	boolean existsByPhone(String phone);
	
}