package com.zentry.zentrystore.domain.user.repository;

import com.zentry.zentrystore.domain.user.model.ContactMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {

    // Búsquedas por usuario
    List<ContactMethod> findByUserId(Long userId);

    List<ContactMethod> findByUserIdAndVerifiedTrue(Long userId);

    Optional<ContactMethod> findByUserIdAndContactType(Long userId, String contactType);

    // Búsquedas por tipo de contacto
    List<ContactMethod> findByContactType(String contactType);

    List<ContactMethod> findByContactTypeAndVerifiedTrue(String contactType);

    // Búsquedas por valor de contacto
    Optional<ContactMethod> findByContactValue(String contactValue);

    Optional<ContactMethod> findByContactTypeAndContactValue(String contactType, String contactValue);

    boolean existsByContactValue(String contactValue);

    boolean existsByUserIdAndContactType(Long userId, String contactType);

    // Búsquedas por verificación
    List<ContactMethod> findByVerifiedTrue();

    List<ContactMethod> findByVerifiedFalse();

    Optional<ContactMethod> findByVerificationCode(String verificationCode);

    // Métodos de contacto preferidos
    List<ContactMethod> findByPreferredTrue();

    Optional<ContactMethod> findByUserIdAndPreferredTrue(Long userId);

    // Verificación con código y validez
    @Query("SELECT cm FROM ContactMethod cm WHERE cm.verificationCode = :code " +
            "AND cm.verificationExpiresAt > :now AND cm.verified = false")
    Optional<ContactMethod> findByValidVerificationCode(@Param("code") String code, @Param("now") LocalDateTime now);

    // Códigos de verificación expirados
    @Query("SELECT cm FROM ContactMethod cm WHERE cm.verified = false " +
            "AND cm.verificationExpiresAt IS NOT NULL AND cm.verificationExpiresAt < :now")
    List<ContactMethod> findExpiredVerificationCodes(@Param("now") LocalDateTime now);

    // Contar métodos de contacto por tipo
    @Query("SELECT cm.contactType, COUNT(cm) FROM ContactMethod cm GROUP BY cm.contactType")
    List<Object[]> countByContactType();

    // Contar métodos de contacto verificados por usuario
    @Query("SELECT COUNT(cm) FROM ContactMethod cm WHERE cm.user.id = :userId AND cm.verified = true")
    Long countVerifiedContactMethodsByUserId(@Param("userId") Long userId);

    // Eliminar métodos de contacto no verificados antiguos
    @Query("DELETE FROM ContactMethod cm WHERE cm.verified = false AND cm.createdAt < :cutoffDate")
    void deleteUnverifiedOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}