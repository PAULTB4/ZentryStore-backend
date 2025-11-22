package com.zentry.zentrystore.domain.publication.exception;

public class PublicationNotFoundException extends RuntimeException {

    public PublicationNotFoundException(String message) {
        super(message);
    }

    public PublicationNotFoundException(Long publicationId) {
        super("Publication not found with id: " + publicationId);
    }

    public static PublicationNotFoundException byId(Long id) {
        return new PublicationNotFoundException(id);
    }

    public static PublicationNotFoundException byUserAndTitle(Long userId, String title) {
        return new PublicationNotFoundException(
                String.format("Publication not found for user %d with title: %s", userId, title)
        );
    }

    public static PublicationNotFoundException bySlug(String slug) {
        return new PublicationNotFoundException("Publication not found with slug: " + slug);
    }
}