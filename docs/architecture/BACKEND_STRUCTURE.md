# ESTRUCTURA COMPLETA DEL BACKEND - ZENTRY STORE
## Spring Boot con Arquitectura CQRS

---

## 📁 ESTRUCTURA DE DIRECTORIOS DEL PROYECTO BACKEND

```
zentry-store-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── unas/
│   │   │           └── zentystore/
│   │   │               │
│   │   │               ├── ZentryStoreApplication.java
│   │   │               │
│   │   │               ├── config/                                    # ⚙️ CONFIGURACIONES
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   ├── CorsConfig.java
│   │   │               │   ├── JwtConfig.java
│   │   │               │   ├── OpenApiConfig.java
│   │   │               │   ├── DatabaseConfig.java
│   │   │               │   ├── EventBusConfig.java
│   │   │               │   ├── AsyncConfig.java
│   │   │               │   ├── CacheConfig.java
│   │   │               │   └── WebMvcConfig.java
│   │   │               │
│   │   │               ├── domain/                                    # 🏗️ CAPA DE DOMINIO
│   │   │               │   │
│   │   │               │   ├── user/                                  # Módulo: Usuarios
│   │   │               │   │   ├── model/
│   │   │               │   │   │   ├── User.java
│   │   │               │   │   │   ├── UserRole.java
│   │   │               │   │   │   ├── UserProfile.java
│   │   │               │   │   │   └── ContactMethod.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   └── UserRepository.java
│   │   │               │   │   │
│   │   │               │   │   ├── exception/
│   │   │               │   │   │   ├── UserNotFoundException.java
│   │   │               │   │   │   ├── DuplicateEmailException.java
│   │   │               │   │   │   └── InvalidCredentialsException.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       ├── UserRegisteredEvent.java
│   │   │               │   │       ├── UserProfileUpdatedEvent.java
│   │   │               │   │       └── UserDeletedEvent.java
│   │   │               │   │
│   │   │               │   ├── publication/                           # Módulo: Publicaciones
│   │   │               │   │   ├── model/
│   │   │               │   │   │   ├── Publication.java
│   │   │               │   │   │   ├── PublicationStatus.java
│   │   │               │   │   │   ├── Category.java
│   │   │               │   │   │   ├── ProductImage.java
│   │   │               │   │   │   ├── Price.java
│   │   │               │   │   │   └── Location.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   ├── PublicationRepository.java
│   │   │               │   │   │   └── CategoryRepository.java
│   │   │               │   │   │
│   │   │               │   │   ├── exception/
│   │   │               │   │   │   ├── PublicationNotFoundException.java
│   │   │               │   │   │   └── InvalidPublicationDataException.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       ├── PublicationCreatedEvent.java
│   │   │               │   │       ├── PublicationUpdatedEvent.java
│   │   │               │   │       ├── PublicationDeletedEvent.java
│   │   │               │   │       └── PublicationStatusChangedEvent.java
│   │   │               │   │
│   │   │               │   ├── messaging/                             # Módulo: Mensajería
│   │   │               │   │   ├── model/
│   │   │               │   │   │   ├── Message.java
│   │   │               │   │   │   ├── Conversation.java
│   │   │               │   │   │   └── MessageStatus.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   ├── MessageRepository.java
│   │   │               │   │   │   └── ConversationRepository.java
│   │   │               │   │   │
│   │   │               │   │   ├── exception/
│   │   │               │   │   │   └── ConversationNotFoundException.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       ├── MessageSentEvent.java
│   │   │               │   │       └── MessageReadEvent.java
│   │   │               │   │
│   │   │               │   ├── notification/                          # Módulo: Notificaciones
│   │   │               │   │   ├── model/
│   │   │               │   │   │   ├── Notification.java
│   │   │               │   │   │   └── NotificationType.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   └── NotificationRepository.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       └── NotificationCreatedEvent.java
│   │   │               │   │
│   │   │               │   ├── rating/                                # Módulo: Calificaciones
│   │   │               │   │   ├── model/
│   │   │               │   │   │   ├── Rating.java
│   │   │               │   │   │   └── RatingScore.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   └── RatingRepository.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       └── RatingCreatedEvent.java
│   │   │               │   │
│   │   │               │   ├── favorite/                              # Módulo: Favoritos
│   │   │               │   │   ├── model/
│   │   │               │   │   │   └── Favorite.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   └── FavoriteRepository.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       ├── FavoriteAddedEvent.java
│   │   │               │   │       └── FavoriteRemovedEvent.java
│   │   │               │   │
│   │   │               │   ├── report/                                # Módulo: Reportes
│   │   │               │   │   ├── model/
│   │   │               │   │   │   ├── Report.java
│   │   │               │   │   │   ├── ReportReason.java
│   │   │               │   │   │   └── ReportStatus.java
│   │   │               │   │   │
│   │   │               │   │   ├── repository/
│   │   │               │   │   │   └── ReportRepository.java
│   │   │               │   │   │
│   │   │               │   │   └── event/
│   │   │               │   │       ├── ReportCreatedEvent.java
│   │   │               │   │       └── ReportResolvedEvent.java
│   │   │               │   │
│   │   │               │   └── shared/                                # Elementos compartidos del dominio
│   │   │               │       ├── AuditableEntity.java
│   │   │               │       ├── DomainEvent.java
│   │   │               │       ├── AggregateRoot.java
│   │   │               │       └── ValueObject.java
│   │   │               │
│   │   │               ├── application/                               # 📋 CAPA DE APLICACIÓN (CQRS)
│   │   │               │   │
│   │   │               │   ├── command/                               # COMMAND SIDE (Escrituras)
│   │   │               │   │   │
│   │   │               │   │   ├── user/
│   │   │               │   │   │   ├── RegisterUserCommand.java
│   │   │               │   │   │   ├── UpdateUserProfileCommand.java
│   │   │               │   │   │   ├── DeleteUserCommand.java
│   │   │               │   │   │   ├── UpdateContactMethodsCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── RegisterUserCommandHandler.java
│   │   │               │   │   │       ├── UpdateUserProfileCommandHandler.java
│   │   │               │   │   │       ├── DeleteUserCommandHandler.java
│   │   │               │   │   │       └── UpdateContactMethodsCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── publication/
│   │   │               │   │   │   ├── CreatePublicationCommand.java
│   │   │               │   │   │   ├── UpdatePublicationCommand.java
│   │   │               │   │   │   ├── DeletePublicationCommand.java
│   │   │               │   │   │   ├── ChangePublicationStatusCommand.java
│   │   │               │   │   │   ├── AddImageCommand.java
│   │   │               │   │   │   ├── RemoveImageCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── CreatePublicationCommandHandler.java
│   │   │               │   │   │       ├── UpdatePublicationCommandHandler.java
│   │   │               │   │   │       ├── DeletePublicationCommandHandler.java
│   │   │               │   │   │       ├── ChangePublicationStatusCommandHandler.java
│   │   │               │   │   │       ├── AddImageCommandHandler.java
│   │   │               │   │   │       └── RemoveImageCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── messaging/
│   │   │               │   │   │   ├── SendMessageCommand.java
│   │   │               │   │   │   ├── MarkMessageAsReadCommand.java
│   │   │               │   │   │   ├── DeleteMessageCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── SendMessageCommandHandler.java
│   │   │               │   │   │       ├── MarkMessageAsReadCommandHandler.java
│   │   │               │   │   │       └── DeleteMessageCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── notification/
│   │   │               │   │   │   ├── CreateNotificationCommand.java
│   │   │               │   │   │   ├── MarkNotificationAsReadCommand.java
│   │   │               │   │   │   ├── MarkAllAsReadCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── CreateNotificationCommandHandler.java
│   │   │               │   │   │       ├── MarkNotificationAsReadCommandHandler.java
│   │   │               │   │   │       └── MarkAllAsReadCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── rating/
│   │   │               │   │   │   ├── CreateRatingCommand.java
│   │   │               │   │   │   ├── UpdateRatingCommand.java
│   │   │               │   │   │   ├── DeleteRatingCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── CreateRatingCommandHandler.java
│   │   │               │   │   │       ├── UpdateRatingCommandHandler.java
│   │   │               │   │   │       └── DeleteRatingCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── favorite/
│   │   │               │   │   │   ├── AddFavoriteCommand.java
│   │   │               │   │   │   ├── RemoveFavoriteCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── AddFavoriteCommandHandler.java
│   │   │               │   │   │       └── RemoveFavoriteCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── report/
│   │   │               │   │   │   ├── CreateReportCommand.java
│   │   │               │   │   │   ├── ResolveReportCommand.java
│   │   │               │   │   │   ├── DismissReportCommand.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── CreateReportCommandHandler.java
│   │   │               │   │   │       ├── ResolveReportCommandHandler.java
│   │   │               │   │   │       └── DismissReportCommandHandler.java
│   │   │               │   │   │
│   │   │               │   │   └── common/
│   │   │               │   │       ├── Command.java
│   │   │               │   │       ├── CommandHandler.java
│   │   │               │   │       └── CommandBus.java
│   │   │               │   │
│   │   │               │   ├── query/                                 # QUERY SIDE (Lecturas)
│   │   │               │   │   │
│   │   │               │   │   ├── user/
│   │   │               │   │   │   ├── GetUserByIdQuery.java
│   │   │               │   │   │   ├── GetUserProfileQuery.java
│   │   │               │   │   │   ├── GetUserPublicationsQuery.java
│   │   │               │   │   │   ├── SearchUsersQuery.java
│   │   │               │   │   │   ├── GetUserRatingQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetUserByIdQueryHandler.java
│   │   │               │   │   │       ├── GetUserProfileQueryHandler.java
│   │   │               │   │   │       ├── GetUserPublicationsQueryHandler.java
│   │   │               │   │   │       ├── SearchUsersQueryHandler.java
│   │   │               │   │   │       └── GetUserRatingQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── publication/
│   │   │               │   │   │   ├── GetPublicationByIdQuery.java
│   │   │               │   │   │   ├── SearchPublicationsQuery.java
│   │   │               │   │   │   ├── GetPublicationsByCategoryQuery.java
│   │   │               │   │   │   ├── GetPublicationsByLocationQuery.java
│   │   │               │   │   │   ├── GetFeaturedPublicationsQuery.java
│   │   │               │   │   │   ├── GetRecentPublicationsQuery.java
│   │   │               │   │   │   ├── GetUserPublicationsQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetPublicationByIdQueryHandler.java
│   │   │               │   │   │       ├── SearchPublicationsQueryHandler.java
│   │   │               │   │   │       ├── GetPublicationsByCategoryQueryHandler.java
│   │   │               │   │   │       ├── GetPublicationsByLocationQueryHandler.java
│   │   │               │   │   │       ├── GetFeaturedPublicationsQueryHandler.java
│   │   │               │   │   │       ├── GetRecentPublicationsQueryHandler.java
│   │   │               │   │   │       └── GetUserPublicationsQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── messaging/
│   │   │               │   │   │   ├── GetConversationQuery.java
│   │   │               │   │   │   ├── GetUserConversationsQuery.java
│   │   │               │   │   │   ├── GetMessagesQuery.java
│   │   │               │   │   │   ├── GetUnreadMessagesCountQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetConversationQueryHandler.java
│   │   │               │   │   │       ├── GetUserConversationsQueryHandler.java
│   │   │               │   │   │       ├── GetMessagesQueryHandler.java
│   │   │               │   │   │       └── GetUnreadMessagesCountQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── notification/
│   │   │               │   │   │   ├── GetUserNotificationsQuery.java
│   │   │               │   │   │   ├── GetUnreadNotificationsCountQuery.java
│   │   │               │   │   │   ├── GetNotificationByIdQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetUserNotificationsQueryHandler.java
│   │   │               │   │   │       ├── GetUnreadNotificationsCountQueryHandler.java
│   │   │               │   │   │       └── GetNotificationByIdQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── rating/
│   │   │               │   │   │   ├── GetUserRatingsQuery.java
│   │   │               │   │   │   ├── GetUserAverageRatingQuery.java
│   │   │               │   │   │   ├── GetRatingByIdQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetUserRatingsQueryHandler.java
│   │   │               │   │   │       ├── GetUserAverageRatingQueryHandler.java
│   │   │               │   │   │       └── GetRatingByIdQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── favorite/
│   │   │               │   │   │   ├── GetUserFavoritesQuery.java
│   │   │               │   │   │   ├── IsFavoriteQuery.java
│   │   │               │   │   │   ├── GetFavoritesCountQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetUserFavoritesQueryHandler.java
│   │   │               │   │   │       ├── IsFavoriteQueryHandler.java
│   │   │               │   │   │       └── GetFavoritesCountQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── report/
│   │   │               │   │   │   ├── GetReportsByStatusQuery.java
│   │   │               │   │   │   ├── GetReportByIdQuery.java
│   │   │               │   │   │   ├── GetAllReportsQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetReportsByStatusQueryHandler.java
│   │   │               │   │   │       ├── GetReportByIdQueryHandler.java
│   │   │               │   │   │       └── GetAllReportsQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   ├── category/
│   │   │               │   │   │   ├── GetAllCategoriesQuery.java
│   │   │               │   │   │   ├── GetCategoryByIdQuery.java
│   │   │               │   │   │   ├── GetCategoriesWithCountQuery.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── handler/
│   │   │               │   │   │       ├── GetAllCategoriesQueryHandler.java
│   │   │               │   │   │       ├── GetCategoryByIdQueryHandler.java
│   │   │               │   │   │       └── GetCategoriesWithCountQueryHandler.java
│   │   │               │   │   │
│   │   │               │   │   └── common/
│   │   │               │   │       ├── Query.java
│   │   │               │   │       ├── QueryHandler.java
│   │   │               │   │       └── QueryBus.java
│   │   │               │   │
│   │   │               │   ├── dto/                                   # Data Transfer Objects
│   │   │               │   │   │
│   │   │               │   │   ├── request/
│   │   │               │   │   │   ├── auth/
│   │   │               │   │   │   │   ├── RegisterRequest.java
│   │   │               │   │   │   │   ├── LoginRequest.java
│   │   │               │   │   │   │   └── RefreshTokenRequest.java
│   │   │               │   │   │   │
│   │   │               │   │   │   ├── user/
│   │   │               │   │   │   │   ├── UpdateProfileRequest.java
│   │   │               │   │   │   │   ├── UpdatePasswordRequest.java
│   │   │               │   │   │   │   └── UpdateContactMethodsRequest.java
│   │   │               │   │   │   │
│   │   │               │   │   │   ├── publication/
│   │   │               │   │   │   │   ├── CreatePublicationRequest.java
│   │   │               │   │   │   │   ├── UpdatePublicationRequest.java
│   │   │               │   │   │   │   └── SearchPublicationsRequest.java
│   │   │               │   │   │   │
│   │   │               │   │   │   ├── message/
│   │   │               │   │   │   │   └── SendMessageRequest.java
│   │   │               │   │   │   │
│   │   │               │   │   │   ├── rating/
│   │   │               │   │   │   │   ├── CreateRatingRequest.java
│   │   │               │   │   │   │   └── UpdateRatingRequest.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── report/
│   │   │               │   │   │       └── CreateReportRequest.java
│   │   │               │   │   │
│   │   │               │   │   └── response/
│   │   │               │   │       ├── auth/
│   │   │               │   │       │   ├── AuthResponse.java
│   │   │               │   │       │   └── TokenResponse.java
│   │   │               │   │       │
│   │   │               │   │       ├── user/
│   │   │               │   │       │   ├── UserResponse.java
│   │   │               │   │       │   ├── UserProfileResponse.java
│   │   │               │   │       │   └── UserSummaryResponse.java
│   │   │               │   │       │
│   │   │               │   │       ├── publication/
│   │   │               │   │       │   ├── PublicationResponse.java
│   │   │               │   │       │   ├── PublicationDetailResponse.java
│   │   │               │   │       │   └── PublicationSummaryResponse.java
│   │   │               │   │       │
│   │   │               │   │       ├── message/
│   │   │               │   │       │   ├── MessageResponse.java
│   │   │               │   │       │   └── ConversationResponse.java
│   │   │               │   │       │
│   │   │               │   │       ├── notification/
│   │   │               │   │       │   └── NotificationResponse.java
│   │   │               │   │       │
│   │   │               │   │       ├── rating/
│   │   │               │   │       │   ├── RatingResponse.java
│   │   │               │   │       │   └── AverageRatingResponse.java
│   │   │               │   │       │
│   │   │               │   │       ├── category/
│   │   │               │   │       │   └── CategoryResponse.java
│   │   │               │   │       │
│   │   │               │   │       └── common/
│   │   │               │   │           ├── PageResponse.java
│   │   │               │   │           ├── ApiResponse.java
│   │   │               │   │           └── ErrorResponse.java
│   │   │               │   │
│   │   │               │   ├── service/                               # Servicios de aplicación
│   │   │               │   │   ├── auth/
│   │   │               │   │   │   ├── AuthenticationService.java
│   │   │               │   │   │   ├── JwtService.java
│   │   │               │   │   │   └── TokenBlacklistService.java
│   │   │               │   │   │
│   │   │               │   │   ├── email/
│   │   │               │   │   │   └── EmailService.java
│   │   │               │   │   │
│   │   │               │   │   ├── storage/
│   │   │               │   │   │   └── FileStorageService.java
│   │   │               │   │   │
│   │   │               │   │   ├── notification/
│   │   │               │   │   │   └── NotificationDispatcher.java
│   │   │               │   │   │
│   │   │               │   │   └── search/
│   │   │               │   │       └── SearchService.java
│   │   │               │   │
│   │   │               │   ├── mapper/                                # Mappers (Entity ↔ DTO)
│   │   │               │   │   ├── UserMapper.java
│   │   │               │   │   ├── PublicationMapper.java
│   │   │               │   │   ├── MessageMapper.java
│   │   │               │   │   ├── NotificationMapper.java
│   │   │               │   │   ├── RatingMapper.java
│   │   │               │   │   ├── CategoryMapper.java
│   │   │               │   │   └── ReportMapper.java
│   │   │               │   │
│   │   │               │   └── event/                                 # Event Listeners
│   │   │               │       ├── user/
│   │   │               │       │   ├── UserRegisteredEventListener.java
│   │   │               │       │   └── UserProfileUpdatedEventListener.java
│   │   │               │       │
│   │   │               │       ├── publication/
│   │   │               │       │   ├── PublicationCreatedEventListener.java
│   │   │               │       │   └── PublicationStatusChangedEventListener.java
│   │   │               │       │
│   │   │               │       └── messaging/
│   │   │               │           └── MessageSentEventListener.java
│   │   │               │
│   │   │               ├── infrastructure/                            # 🔧 CAPA DE INFRAESTRUCTURA
│   │   │               │   │
│   │   │               │   ├── persistence/
│   │   │               │   │   ├── jpa/
│   │   │               │   │   │   ├── repository/
│   │   │               │   │   │   │   ├── JpaUserRepository.java
│   │   │               │   │   │   │   ├── JpaPublicationRepository.java
│   │   │               │   │   │   │   ├── JpaMessageRepository.java
│   │   │               │   │   │   │   ├── JpaNotificationRepository.java
│   │   │               │   │   │   │   ├── JpaRatingRepository.java
│   │   │               │   │   │   │   ├── JpaFavoriteRepository.java
│   │   │               │   │   │   │   ├── JpaReportRepository.java
│   │   │               │   │   │   │   └── JpaCategoryRepository.java
│   │   │               │   │   │   │
│   │   │               │   │   │   └── adapter/
│   │   │               │   │   │       ├── UserRepositoryAdapter.java
│   │   │               │   │   │       ├── PublicationRepositoryAdapter.java
│   │   │               │   │   │       ├── MessageRepositoryAdapter.java
│   │   │               │   │   │       ├── NotificationRepositoryAdapter.java
│   │   │               │   │   │       ├── RatingRepositoryAdapter.java
│   │   │               │   │   │       ├── FavoriteRepositoryAdapter.java
│   │   │               │   │   │       ├── ReportRepositoryAdapter.java
│   │   │               │   │   │       └── CategoryRepositoryAdapter.java
│   │   │               │   │   │
│   │   │               │   │   └── specification/
│   │   │               │   │       ├── PublicationSpecification.java
│   │   │               │   │       ├── UserSpecification.java
│   │   │               │   │       └── MessageSpecification.java
│   │   │               │   │
│   │   │               │   ├── security/
│   │   │               │   │   ├── jwt/
│   │   │               │   │   │   ├── JwtTokenProvider.java
│   │   │               │   │   │   ├── JwtAuthenticationFilter.java
│   │   │               │   │   │   └── JwtAuthenticationEntryPoint.java
│   │   │               │   │   │
│   │   │               │   │   ├── service/
│   │   │               │   │   │   ├── UserDetailsServiceImpl.java
│   │   │               │   │   │   └── CustomUserDetails.java
│   │   │               │   │   │
│   │   │               │   │   └── util/
│   │   │               │   │       ├── PasswordEncoderUtil.java
│   │   │               │   │       └── SecurityUtils.java
│   │   │               │   │
│   │   │               │   ├── email/
│   │   │               │   │   ├── EmailServiceImpl.java
│   │   │               │   │   └── template/
│   │   │               │   │       ├── WelcomeEmailTemplate.java
│   │   │               │   │       ├── PasswordResetEmailTemplate.java
│   │   │               │   │       └── NotificationEmailTemplate.java
│   │   │               │   │
│   │   │               │   ├── storage/
│   │   │               │   │   ├── local/
│   │   │               │   │   │   └── LocalFileStorageServiceImpl.java
│   │   │               │   │   │
│   │   │               │   │   └── cloud/
│   │   │               │   │       ├── CloudinaryStorageServiceImpl.java
│   │   │               │   │       └── S3StorageServiceImpl.java
│   │   │               │   │
│   │   │               │   ├── messaging/
│   │   │               │   │   ├── EventPublisherImpl.java
│   │   │               │   │   └── EventListenerRegistry.java
│   │   │               │   │
│   │   │               │   └── cache/
│   │   │               │       └── RedisCacheServiceImpl.java
│   │   │               │
│   │   │               └── api/                                       # 🌐 CAPA DE PRESENTACIÓN (API REST)
│   │   │                   │
│   │   │                   ├── controller/
│   │   │                   │   ├── auth/
│   │   │                   │   │   └── AuthController.java
│   │   │                   │   │
│   │   │                   │   ├── user/
│   │   │                   │   │   ├── UserController.java
│   │   │                   │   │   └── ProfileController.java
│   │   │                   │   │
│   │   │                   │   ├── publication/
│   │   │                   │   │   ├── PublicationController.java
│   │   │                   │   │   └── PublicationSearchController.java
│   │   │                   │   │
│   │   │                   │   ├── message/
│   │   │                   │   │   └── MessageController.java
│   │   │                   │   │
│   │   │                   │   ├── notification/
│   │   │                   │   │   └── NotificationController.java
│   │   │                   │   │
│   │   │                   │   ├── rating/
│   │   │                   │   │   └── RatingController.java
│   │   │                   │   │
│   │   │                   │   ├── favorite/
│   │   │                   │   │   └── FavoriteController.java
│   │   │                   │   │
│   │   │                   │   ├── report/
│   │   │                   │   │   └── ReportController.java
│   │   │                   │   │
│   │   │                   │   ├── category/
│   │   │                   │   │   └── CategoryController.java
│   │   │                   │   │
│   │   │                   │   └── admin/
│   │   │                   │       ├── AdminDashboardController.java
│   │   │                   │       ├── AdminUserController.java
│   │   │                   │       ├── AdminPublicationController.java
│   │   │                   │       └── AdminReportController.java
│   │   │                   │
│   │   │                   ├── exception/
│   │   │                   │   ├── GlobalExceptionHandler.java
│   │   │                   │   ├── ResourceNotFoundException.java
│   │   │                   │   ├── BadRequestException.java
│   │   │                   │   ├── UnauthorizedException.java
│   │   │                   │   ├── ForbiddenException.java
│   │   │                   │   ├── DuplicateResourceException.java
│   │   │                   │   └── dto/
│   │   │                   │       └── ErrorResponse.java
│   │   │                   │
│   │   │                   └── validation/
│   │   │                       ├── annotation/
│   │   │                       │   ├── UniqueEmail.java
│   │   │                       │   ├── ValidPrice.java
│   │   │                       │   ├── ValidImage.java
│   │   │                       │   └── ValidCategory.java
│   │   │                       │
│   │   │                       └── validator/
│   │   │                           ├── UniqueEmailValidator.java
│   │   │                           ├── ValidPriceValidator.java
│   │   │                           ├── ValidImageValidator.java
│   │   │                           └── ValidCategoryValidator.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       ├── application-test.yml
│   │       │
│   │       ├── db/
│   │       │   └── migration/
│   │       │       ├── V1__create_users_table.sql
│   │       │       ├── V2__create_publications_table.sql
│   │       │       ├── V3__create_categories_table.sql
│   │       │       ├── V4__create_messages_table.sql
│   │       │       ├── V5__create_conversations_table.sql
│   │       │       ├── V6__create_notifications_table.sql
│   │       │       ├── V7__create_ratings_table.sql
│   │       │       ├── V8__create_favorites_table.sql
│   │       │       ├── V9__create_reports_table.sql
│   │       │       └── V10__insert_default_categories.sql
│   │       │
│   │       ├── templates/
│   │       │   ├── email/
│   │       │   │   ├── welcome.html
│   │       │   │   ├── password-reset.html
│   │       │   │   ├── new-message.html
│   │       │   │   └── publication-approved.html
│   │       │   │
│   │       │   └── pdf/
│   │       │       └── report-template.html
│   │       │
│   │       ├── static/
│   │       │   ├── images/
│   │       │   │   └── logo.png
│   │       │   │
│   │       │   └── docs/
│   │       │       └── api-documentation.html
│   │       │
│   │       └── messages/
│   │           ├── messages.properties
│   │           ├── messages_es.properties
│   │           └── messages_en.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── unas/
│                   └── zentystore/
│                       │
│                       ├── unit/
│                       │   ├── domain/
│                       │   │   ├── user/
│                       │   │   ├── publication/
│                       │   │   └── messaging/
│                       │   │
│                       │   ├── application/
│                       │   │   ├── command/
│                       │   │   │   ├── user/
│                       │   │   │   ├── publication/
│                       │   │   │   └── messaging/
│                       │   │   │
│                       │   │   └── query/
│                       │   │       ├── user/
│                       │   │       ├── publication/
│                       │   │       └── messaging/
│                       │   │
│                       │   └── infrastructure/
│                       │       ├── persistence/
│                       │       ├── security/
│                       │       └── email/
│                       │
│                       ├── integration/
│                       │   ├── api/
│                       │   │   ├── AuthControllerIntegrationTest.java
│                       │   │   ├── PublicationControllerIntegrationTest.java
│                       │   │   └── MessageControllerIntegrationTest.java
│                       │   │
│                       │   ├── repository/
│                       │   │   ├── UserRepositoryIntegrationTest.java
│                       │   │   └── PublicationRepositoryIntegrationTest.java
│                       │   │
│                       │   └── service/
│                       │       └── EmailServiceIntegrationTest.java
│                       │
│                       └── e2e/
│                           ├── UserRegistrationE2ETest.java
│                           ├── PublicationCreationE2ETest.java
│                           └── MessagingE2ETest.java
│
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
│
├── .gitignore
├── .env
├── .env.example
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── ARCHITECTURE.md
├── CONTRIBUTING.md
├── docker-compose.yml
├── Dockerfile
└── .gitlab-ci.yml / .github/workflows/ci.yml
```

---

## 📦 ARCHIVOS DE CONFIGURACIÓN PRINCIPALES

### **pom.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.unas</groupId>
    <artifactId>zentry-store-backend</artifactId>
    <version>1.0.0</version>
    <name>Zentry Store Backend</name>
    <description>Backend API for Zentry Store C2C Platform</description>
    
    <properties>
        <java.version>17</java.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <jwt.version>0.12.3</jwt.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
        </dependency>
        
        <!-- JWT -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jwt.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- MapStruct -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${mapstruct.version}</version>
            <scope>provided</scope>
        </dependency>
        
        <!-- OpenAPI / Swagger -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.2.0</version>
        </dependency>
        
        <!-- File Upload -->
        <dependency>
            <groupId>com.cloudinary</groupId>
            <artifactId>cloudinary-http44</artifactId>
            <version>1.36.0</version>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### **application.yml**
```yaml
spring:
  application:
    name: zentry-store-backend
  
  profiles:
    active: dev
  
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:zentry_store}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
        jdbc:
          time_zone: UTC
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
  
  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 10MB
  
  mail:
    host: ${MAIL_HOST:smtp.gmail.com}
    port: ${MAIL_PORT:587}
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

server:
  port: ${PORT:8080}
  servlet:
    context-path: /api/v1
  error:
    include-message: always
    include-stacktrace: on_param

application:
  security:
    jwt:
      secret-key: ${JWT_SECRET:your-secret-key-change-this-in-production}
      expiration: 86400000 # 24 hours
      refresh-token-expiration: 604800000 # 7 days
  
  cors:
    allowed-origins: ${CORS_ORIGINS:http://localhost:5173,http://localhost:3000}
    allowed-methods: GET,POST,PUT,DELETE,PATCH,OPTIONS
    allowed-headers: "*"
    allow-credentials: true
  
  storage:
    type: ${STORAGE_TYPE:local} # local, cloudinary, s3
    local:
      upload-dir: ${UPLOAD_DIR:./uploads}
    cloudinary:
      cloud-name: ${CLOUDINARY_CLOUD_NAME}
      api-key: ${CLOUDINARY_API_KEY}
      api-secret: ${CLOUDINARY_API_SECRET}

logging:
  level:
    root: INFO
    com.unas.zentystore: DEBUG
    org.springframework.web: INFO
    org.hibernate: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/application.log

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

### **.env.example**
```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=zentry_store
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production-min-256-bits

# Email
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# CORS
CORS_ORIGINS=http://localhost:5173,http://localhost:3000

# Storage
STORAGE_TYPE=local
UPLOAD_DIR=./uploads

# Cloudinary (optional)
CLOUDINARY_CLOUD_NAME=
CLOUDINARY_API_KEY=
CLOUDINARY_API_SECRET=

# Server
PORT=8080
```

### **docker-compose.yml**
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: zentry-store-db
    environment:
      POSTGRES_DB: zentry_store
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - zentry-network

  backend:
    build: .
    container_name: zentry-store-backend
    environment:
      DB_HOST: postgres
      DB_PORT: 5432
      DB_NAME: zentry_store
      DB_USERNAME: postgres
      DB_PASSWORD: postgres
      JWT_SECRET: your-super-secret-jwt-key
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - zentry-network
    volumes:
      - ./uploads:/app/uploads

volumes:
  postgres_data:

networks:
  zentry-network:
    driver: bridge
```

---

## 🎯 DESCRIPCIÓN DE LA ARQUITECTURA

### **1. Capa de Dominio (`domain/`)**
- Contiene las **entidades**, **value objects** y **lógica de negocio pura**
- Independiente de frameworks y tecnologías
- Define los **repositorios** como interfaces (puertos)
- Emite **eventos de dominio**

### **2. Capa de Aplicación (`application/`)**
- Implementa el patrón **CQRS**
- **Commands:** Operaciones de escritura (crear, actualizar, eliminar)
- **Queries:** Operaciones de lectura (búsquedas, consultas)
- **DTOs:** Objetos de transferencia de datos
- **Servicios de aplicación:** Lógica orquestadora
- **Mappers:** Conversión entre entidades y DTOs

### **3. Capa de Infraestructura (`infrastructure/`)**
- Implementaciones técnicas concretas
- **Persistencia:** JPA repositories, adapters
- **Seguridad:** JWT, autenticación, autorización
- **Email:** Envío de correos
- **Storage:** Almacenamiento de archivos
- **Cache:** Redis (opcional)

### **4. Capa de Presentación/API (`api/`)**
- **Controllers REST:** Endpoints de la API
- **Exception Handlers:** Manejo global de errores
- **Validaciones:** Validadores personalizados

---

## ✅ VENTAJAS DE ESTA ESTRUCTURA

1. ✅ **Separación clara de responsabilidades** (CQRS)
2. ✅ **Escalabilidad** (comandos y consultas independientes)
3. ✅ **Mantenibilidad** (código organizado por módulos)
4. ✅ **Testeable** (cada capa se testea independientemente)
5. ✅ **Modular** (fácil agregar nuevas funcionalidades)
6. ✅ **Clean Architecture** (dependencias apuntan hacia el dominio)
7. ✅ **Preparado para microservicios** (arquitectura desacoplada)

---

Esta es la estructura completa y profesional para tu BACKEND de Zentry Store con Spring Boot y CQRS. 🚀
