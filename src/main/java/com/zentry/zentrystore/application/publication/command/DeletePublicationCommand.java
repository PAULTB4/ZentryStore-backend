package com.zentry.zentrystore.application.publication.command;

public class DeletePublicationCommand {

    private final Long publicationId;

    public DeletePublicationCommand(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getPublicationId() { return publicationId; }
}