-- =============================================
-- ZENTRY STORE - INITIAL DATABASE SCHEMA
-- =============================================

-- 1. ROLES TABLE
CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. USERS TABLE
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       active BOOLEAN DEFAULT TRUE,
                       email_verified BOOLEAN DEFAULT FALSE,
                       verification_token VARCHAR(255),
                       password_reset_token VARCHAR(255),
                       password_reset_expires_at TIMESTAMP,
                       last_login_at TIMESTAMP,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. USER_ROLES (JOIN TABLE)
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- 4. USER_PROFILES
CREATE TABLE user_profiles (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                               first_name VARCHAR(100),
                               last_name VARCHAR(100),
                               phone_number VARCHAR(20),
                               date_of_birth DATE,
                               gender VARCHAR(20),
                               profile_picture_url VARCHAR(500),
                               bio TEXT,
                               city VARCHAR(100),
                               state VARCHAR(100),
                               country VARCHAR(100),
                               postal_code VARCHAR(20),
                               address VARCHAR(255),
                               preferred_language VARCHAR(10),
                               timezone VARCHAR(50),
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. CONTACT_METHODS
CREATE TABLE contact_methods (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                 type VARCHAR(50) NOT NULL,
                                 value VARCHAR(255) NOT NULL,
                                 is_primary BOOLEAN DEFAULT FALSE,
                                 is_verified BOOLEAN DEFAULT FALSE,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. CATEGORIES
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            slug VARCHAR(100) NOT NULL UNIQUE,
                            description TEXT,
                            icon_url VARCHAR(500),
                            parent_id BIGINT REFERENCES categories(id),
                            active BOOLEAN DEFAULT TRUE,
                            display_order INT DEFAULT 0,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. PUBLICATIONS
CREATE TABLE publications (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL REFERENCES users(id),
                              category_id BIGINT NOT NULL REFERENCES categories(id),
                              title VARCHAR(200) NOT NULL,
                              description TEXT NOT NULL,
                              status VARCHAR(20) DEFAULT 'DRAFT',
                              condition VARCHAR(50),
                              available_quantity INT DEFAULT 1,
                              view_count INT DEFAULT 0,
                              favorite_count INT DEFAULT 0,
                              is_featured BOOLEAN DEFAULT FALSE,
                              is_negotiable BOOLEAN DEFAULT FALSE,
                              allows_shipping BOOLEAN DEFAULT FALSE,
                              published_at TIMESTAMP,
                              expires_at TIMESTAMP,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. PRODUCT_IMAGES
CREATE TABLE product_images (
                                id BIGSERIAL PRIMARY KEY,
                                publication_id BIGINT NOT NULL REFERENCES publications(id) ON DELETE CASCADE,
                                image_url VARCHAR(500) NOT NULL,
                                thumbnail_url VARCHAR(500),
                                is_primary BOOLEAN DEFAULT FALSE,
                                display_order INT DEFAULT 0,
                                alt_text VARCHAR(255),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. CONVERSATIONS
CREATE TABLE conversations (
                               id BIGSERIAL PRIMARY KEY,
                               user1_id BIGINT NOT NULL REFERENCES users(id),
                               user2_id BIGINT NOT NULL REFERENCES users(id),
                               last_message_at TIMESTAMP,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 10. MESSAGES
CREATE TABLE messages (
                          id BIGSERIAL PRIMARY KEY,
                          conversation_id BIGINT NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,
                          sender_id BIGINT NOT NULL REFERENCES users(id),
                          content TEXT NOT NULL,
                          status VARCHAR(20) DEFAULT 'SENT',
                          read_at TIMESTAMP,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 11. NOTIFICATIONS
CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               type VARCHAR(50) NOT NULL,
                               title VARCHAR(200) NOT NULL,
                               message TEXT,
                               reference_id BIGINT,
                               reference_type VARCHAR(50),
                               is_read BOOLEAN DEFAULT FALSE,
                               read_at TIMESTAMP,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 12. RATINGS
CREATE TABLE ratings (
                         id BIGSERIAL PRIMARY KEY,
                         rater_id BIGINT NOT NULL REFERENCES users(id),
                         rated_user_id BIGINT NOT NULL REFERENCES users(id),
                         publication_id BIGINT REFERENCES publications(id),
                         score INT NOT NULL CHECK (score >= 1 AND score <= 5),
                         comment TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 13. FAVORITES
CREATE TABLE favorites (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           publication_id BIGINT NOT NULL REFERENCES publications(id) ON DELETE CASCADE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           UNIQUE(user_id, publication_id)
);

-- 14. REPORTS
CREATE TABLE reports (
                         id BIGSERIAL PRIMARY KEY,
                         reporter_id BIGINT NOT NULL REFERENCES users(id),
                         reported_entity_id BIGINT NOT NULL,
                         reported_entity_type VARCHAR(50) NOT NULL,
                         reason VARCHAR(50) NOT NULL,
                         description TEXT,
                         status VARCHAR(20) DEFAULT 'PENDING',
                         reviewed_by BIGINT REFERENCES users(id),
                         reviewed_at TIMESTAMP,
                         resolution_notes TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- INITIAL DATA
-- =============================================
INSERT INTO roles (name, description, created_at) VALUES
                                                      ('ROLE_USER', 'Regular user role', NOW()),
                                                      ('ROLE_ADMIN', 'Administrator role', NOW()),
                                                      ('ROLE_MODERATOR', 'Moderator role', NOW());

INSERT INTO categories (name, slug, description, active, display_order, created_at) VALUES
                                                                                        ('Electronics', 'electronics', 'Electronic devices and gadgets', true, 1, NOW()),
                                                                                        ('Vehicles', 'vehicles', 'Cars, motorcycles and more', true, 2, NOW()),
                                                                                        ('Home & Garden', 'home-garden', 'Furniture and home items', true, 3, NOW()),
                                                                                        ('Fashion', 'fashion', 'Clothing and accessories', true, 4, NOW()),
                                                                                        ('Sports', 'sports', 'Sports equipment and gear', true, 5, NOW());