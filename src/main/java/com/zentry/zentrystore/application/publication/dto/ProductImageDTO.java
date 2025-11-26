package com.zentry.zentrystore.application.publication.dto;

public class ProductImageDTO {

    private Long id;
    private String imageUrl;
    private String thumbnailUrl;
    private Boolean isPrimary;
    private Integer displayOrder;
    private String altText;
    private Long fileSize;      // ← Agregar este
    private String format;      // ← Agregar este


    // Constructors
    public ProductImageDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }


    // ← Agregar estos getters y setters
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
}