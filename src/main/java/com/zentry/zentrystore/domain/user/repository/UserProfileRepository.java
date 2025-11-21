package com.zentry.zentrystore.domain.user.repository;

import com.zentry.zentrystore.domain.user.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // Búsqueda por user_id
    Optional<UserProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    // Búsquedas por ubicación
    List<UserProfile> findByCity(String city);

    List<UserProfile> findByState(String state);

    List<UserProfile> findByCountry(String country);

    List<UserProfile> findByCityAndCountry(String city, String country);

    // Búsquedas por nombre
    List<UserProfile> findByFirstNameContainingIgnoreCase(String firstName);

    List<UserProfile> findByLastNameContainingIgnoreCase(String lastName);

    @Query("SELECT p FROM UserProfile p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<UserProfile> searchByName(@Param("search") String search);

    // Búsquedas por idioma y zona horaria
    List<UserProfile> findByPreferredLanguage(String language);

    List<UserProfile> findByTimezone(String timezone);

    // Perfiles completos
    @Query("SELECT p FROM UserProfile p WHERE " +
            "p.firstName IS NOT NULL AND p.lastName IS NOT NULL AND " +
            "p.phoneNumber IS NOT NULL AND p.city IS NOT NULL AND p.country IS NOT NULL")
    List<UserProfile> findCompleteProfiles();

    // Perfiles incompletos
    @Query("SELECT p FROM UserProfile p WHERE " +
            "p.firstName IS NULL OR p.lastName IS NULL OR " +
            "p.phoneNumber IS NULL OR p.city IS NULL OR p.country IS NULL")
    List<UserProfile> findIncompleteProfiles();

    // Búsqueda por número de teléfono
    Optional<UserProfile> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    // Contar perfiles por país
    @Query("SELECT p.country, COUNT(p) FROM UserProfile p WHERE p.country IS NOT NULL GROUP BY p.country")
    List<Object[]> countProfilesByCountry();
}