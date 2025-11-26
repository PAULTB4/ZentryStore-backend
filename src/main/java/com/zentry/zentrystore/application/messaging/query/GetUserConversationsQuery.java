package com.zentry.zentrystore.application.messaging.query;

import org.springframework.data.domain.Pageable;

public class GetUserConversationsQuery {
    private final Long userId;
    private final Pageable pageable;

    public GetUserConversationsQuery(Long userId, Pageable pageable) {
        this.userId = userId;
        this.pageable = pageable;
    }

    public Long getUserId() {
        return userId;
    }

    public Pageable getPageable() {
        return pageable;
    }
}