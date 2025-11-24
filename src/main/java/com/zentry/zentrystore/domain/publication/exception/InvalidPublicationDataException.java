package com.zentry.zentrystore.domain.publication.exception;

public class InvalidPublicationDataException extends RuntimeException {

    public InvalidPublicationDataException(String message) {
        super(message);
    }

    public static InvalidPublicationDataException forInvalidPrice() {
        return new InvalidPublicationDataException("Price must be greater than zero");
    }

    public static InvalidPublicationDataException forMissingTitle() {
        return new InvalidPublicationDataException("Title is required and cannot be empty");
    }

    public static InvalidPublicationDataException forMissingDescription() {
        return new InvalidPublicationDataException("Description is required and cannot be empty");
    }

    public static InvalidPublicationDataException forMissingCategory() {
        return new InvalidPublicationDataException("Category is required");
    }

    public static InvalidPublicationDataException forInvalidCategory() {
        return new InvalidPublicationDataException("Invalid or inactive category");
    }

    public static InvalidPublicationDataException forInvalidQuantity() {
        return new InvalidPublicationDataException("Available quantity must be greater than zero");
    }

    public static InvalidPublicationDataException forMissingImages() {
        return new InvalidPublicationDataException("At least one image is required");
    }

    public static InvalidPublicationDataException forInvalidStatus(String currentStatus, String targetStatus) {
        return new InvalidPublicationDataException(
                String.format("Cannot change status from %s to %s", currentStatus, targetStatus)
        );
    }

    public static InvalidPublicationDataException forExpiredPublication() {
        return new InvalidPublicationDataException("Cannot modify an expired publication");
    }

    public static InvalidPublicationDataException forSoldPublication() {
        return new InvalidPublicationDataException("Cannot modify a publication that has been sold");
    }

    public static InvalidPublicationDataException forInvalidLocation() {
        return new InvalidPublicationDataException("Location information is incomplete or invalid");
    }
}