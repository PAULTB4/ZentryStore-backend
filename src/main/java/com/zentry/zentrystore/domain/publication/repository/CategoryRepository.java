package com.zentry.zentrystore.domain.publication.repository;

import com.zentry.zentrystore.domain.publication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Búsqueda por slug
    Optional<Category> findBySlug(String slug);

    boolean existsBySlug(String slug);

    // Búsqueda por nombre
    Optional<Category> findByName(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

    // Búsqueda por estado
    List<Category> findByActiveTrue();

    List<Category> findByActiveFalse();

    // Categorías de nivel superior (sin padre)
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findTopLevelCategories();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.active = true")
    List<Category> findActiveTopLevelCategories();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.displayOrder ASC")
    List<Category> findTopLevelCategoriesOrderByDisplay();

    // Subcategorías
    List<Category> findByParentId(Long parentId);

    List<Category> findByParentIdAndActiveTrue(Long parentId);

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId ORDER BY c.displayOrder ASC")
    List<Category> findSubcategoriesOrderByDisplay(@Param("parentId") Long parentId);

    // Verificar si tiene subcategorías
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.parent.id = :categoryId")
    boolean hasSubcategories(@Param("categoryId") Long categoryId);

    // Contar publicaciones por categoría
    @Query("SELECT COUNT(p) FROM Publication p WHERE p.category.id = :categoryId")
    Long countPublicationsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(p) FROM Publication p WHERE p.category.id = :categoryId AND p.status = 'ACTIVE'")
    Long countActivePublicationsByCategoryId(@Param("categoryId") Long categoryId);

    // Categorías con más publicaciones
    @Query("SELECT c FROM Category c LEFT JOIN c.publications p " +
            "WHERE c.active = true " +
            "GROUP BY c ORDER BY COUNT(p) DESC")
    List<Category> findMostPopularCategories();

    // Búsqueda de categorías y subcategorías
    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId OR c.id = :parentId")
    List<Category> findCategoryAndSubcategories(@Param("parentId") Long parentId);

    // Ordenamiento
    List<Category> findByActiveTrueOrderByDisplayOrderAsc();

    List<Category> findByActiveTrueOrderByNameAsc();

    // Estadísticas
    @Query("SELECT c.name, COUNT(p) FROM Category c LEFT JOIN c.publications p " +
            "GROUP BY c.id, c.name ORDER BY COUNT(p) DESC")
    List<Object[]> getCategoryStatistics();

    @Query("SELECT COUNT(c) FROM Category c WHERE c.parent IS NULL")
    Long countTopLevelCategories();

    @Query("SELECT COUNT(c) FROM Category c WHERE c.parent.id = :parentId")
    Long countSubcategories(@Param("parentId") Long parentId);
}