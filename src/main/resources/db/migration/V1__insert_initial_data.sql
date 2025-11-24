-- =============================================
-- ZENTRY STORE - COMPLETE DATABASE SCHEMA V1
-- Modules: User, Publication, Messaging, Notification, Rating, Favorite, Report, Category
-- =============================================

-- =============================================
-- MODULE: USER
-- =============================================

-- 1. ROLES
CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. USERS
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

-- 3. USER_ROLES (Many-to-Many)
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
                               preferred_language VARCHAR(10) DEFAULT 'en',
                               timezone VARCHAR(50) DEFAULT 'UTC',
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

-- =============================================
-- MODULE: CATEGORY
-- =============================================

-- 6. CATEGORIES
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            slug VARCHAR(100) NOT NULL UNIQUE,
                            description TEXT,
                            icon_url VARCHAR(500),
                            parent_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
                            active BOOLEAN DEFAULT TRUE,
                            display_order INT DEFAULT 0,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- MODULE: PUBLICATION
-- =============================================

-- 7. PUBLICATIONS (with embedded Price and Location)
CREATE TABLE publications (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,
                              title VARCHAR(200) NOT NULL,
                              description TEXT NOT NULL,
                              status VARCHAR(20) DEFAULT 'DRAFT',
                              condition VARCHAR(50),

    -- Embedded Price fields
                              amount DECIMAL(10, 2),
                              currency VARCHAR(3) DEFAULT 'USD',
                              discount_percentage DECIMAL(5, 2),
                              original_amount DECIMAL(10, 2),

    -- Embedded Location fields
                              city VARCHAR(100),
                              state VARCHAR(100),
                              country VARCHAR(100),
                              postal_code VARCHAR(20),
                              address TEXT,
                              latitude DOUBLE PRECISION,
                              longitude DOUBLE PRECISION,

    -- Additional fields
                              available_quantity INT DEFAULT 1,
                              view_count BIGINT DEFAULT 0,
                              favorite_count BIGINT DEFAULT 0,
                              is_featured BOOLEAN DEFAULT FALSE,
                              is_negotiable BOOLEAN DEFAULT TRUE,
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
                                file_size BIGINT,
                                format VARCHAR(50),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- MODULE: MESSAGING
-- =============================================

-- 9. CONVERSATIONS
CREATE TABLE conversations (
                               id BIGSERIAL PRIMARY KEY,
                               user1_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               user2_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               publication_id BIGINT REFERENCES publications(id) ON DELETE SET NULL,
                               last_message_at TIMESTAMP,
                               is_archived_by_user1 BOOLEAN DEFAULT FALSE,
                               is_archived_by_user2 BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 10. MESSAGES
CREATE TABLE messages (
                          id BIGSERIAL PRIMARY KEY,
                          conversation_id BIGINT NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,
                          sender_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          content TEXT NOT NULL,
                          message_type VARCHAR(20) DEFAULT 'TEXT',
                          status VARCHAR(20) DEFAULT 'SENT',
                          read_at TIMESTAMP,
                          attachment_url VARCHAR(500),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- MODULE: NOTIFICATION
-- =============================================

-- 11. NOTIFICATIONS
CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               type VARCHAR(50) NOT NULL,
                               title VARCHAR(200) NOT NULL,
                               message TEXT,
                               reference_id BIGINT,
                               reference_type VARCHAR(50),
                               action_url VARCHAR(500),
                               is_read BOOLEAN DEFAULT FALSE,
                               read_at TIMESTAMP,
                               is_sent BOOLEAN DEFAULT FALSE,
                               sent_at TIMESTAMP,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 12. NOTIFICATION_PREFERENCES
CREATE TABLE notification_preferences (
                                          id BIGSERIAL PRIMARY KEY,
                                          user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                                          email_notifications BOOLEAN DEFAULT TRUE,
                                          push_notifications BOOLEAN DEFAULT TRUE,
                                          sms_notifications BOOLEAN DEFAULT FALSE,
                                          new_message_email BOOLEAN DEFAULT TRUE,
                                          new_message_push BOOLEAN DEFAULT TRUE,
                                          favorite_email BOOLEAN DEFAULT TRUE,
                                          favorite_push BOOLEAN DEFAULT TRUE,
                                          price_change_email BOOLEAN DEFAULT FALSE,
                                          price_change_push BOOLEAN DEFAULT TRUE,
                                          publication_sold_email BOOLEAN DEFAULT TRUE,
                                          publication_sold_push BOOLEAN DEFAULT TRUE,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- MODULE: RATING
-- =============================================

-- 13. RATINGS
CREATE TABLE ratings (
                         id BIGSERIAL PRIMARY KEY,
                         rater_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         rated_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         publication_id BIGINT REFERENCES publications(id) ON DELETE SET NULL,
                         transaction_type VARCHAR(20),
                         score INT NOT NULL CHECK (score >= 1 AND score <= 5),
                         comment TEXT,
                         response TEXT,
                         is_anonymous BOOLEAN DEFAULT FALSE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 14. USER_RATING_SUMMARY
CREATE TABLE user_rating_summary (
                                     id BIGSERIAL PRIMARY KEY,
                                     user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                                     total_ratings INT DEFAULT 0,
                                     average_score DECIMAL(3, 2) DEFAULT 0.00,
                                     five_star_count INT DEFAULT 0,
                                     four_star_count INT DEFAULT 0,
                                     three_star_count INT DEFAULT 0,
                                     two_star_count INT DEFAULT 0,
                                     one_star_count INT DEFAULT 0,
                                     as_seller_count INT DEFAULT 0,
                                     as_buyer_count INT DEFAULT 0,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- MODULE: FAVORITE
-- =============================================

-- 15. FAVORITES
CREATE TABLE favorites (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           publication_id BIGINT NOT NULL REFERENCES publications(id) ON DELETE CASCADE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT unique_favorite UNIQUE(user_id, publication_id)
);

-- 16. FAVORITE_LISTS
CREATE TABLE favorite_lists (
                                id BIGSERIAL PRIMARY KEY,
                                user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                name VARCHAR(100) NOT NULL,
                                description TEXT,
                                is_public BOOLEAN DEFAULT FALSE,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 17. FAVORITE_LIST_ITEMS
CREATE TABLE favorite_list_items (
                                     id BIGSERIAL PRIMARY KEY,
                                     list_id BIGINT NOT NULL REFERENCES favorite_lists(id) ON DELETE CASCADE,
                                     publication_id BIGINT NOT NULL REFERENCES publications(id) ON DELETE CASCADE,
                                     added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT unique_list_item UNIQUE(list_id, publication_id)
);

-- =============================================
-- MODULE: REPORT
-- =============================================

-- 18. REPORTS
CREATE TABLE reports (
                         id BIGSERIAL PRIMARY KEY,
                         reporter_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         reported_entity_id BIGINT NOT NULL,
                         reported_entity_type VARCHAR(50) NOT NULL,
                         reason VARCHAR(50) NOT NULL,
                         description TEXT,
                         status VARCHAR(20) DEFAULT 'PENDING',
                         priority VARCHAR(20) DEFAULT 'MEDIUM',
                         reviewed_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
                         reviewed_at TIMESTAMP,
                         resolution_notes TEXT,
                         action_taken VARCHAR(50),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 19. REPORT_EVIDENCE
CREATE TABLE report_evidence (
                                 id BIGSERIAL PRIMARY KEY,
                                 report_id BIGINT NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
                                 file_url VARCHAR(500) NOT NULL,
                                 file_type VARCHAR(50),
                                 description TEXT,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- INDEXES FOR PERFORMANCE
-- =============================================

-- User indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_active ON users(active);
CREATE INDEX idx_user_profiles_user_id ON user_profiles(user_id);

-- Publication indexes
CREATE INDEX idx_publications_user_id ON publications(user_id);
CREATE INDEX idx_publications_category_id ON publications(category_id);
CREATE INDEX idx_publications_status ON publications(status);
CREATE INDEX idx_publications_created_at ON publications(created_at DESC);
CREATE INDEX idx_publications_city ON publications(city);
CREATE INDEX idx_publications_country ON publications(country);
CREATE INDEX idx_publications_amount ON publications(amount);
CREATE INDEX idx_publications_featured ON publications(is_featured) WHERE is_featured = true;

-- Product images indexes
CREATE INDEX idx_product_images_publication_id ON product_images(publication_id);
CREATE INDEX idx_product_images_primary ON product_images(is_primary) WHERE is_primary = true;
CREATE INDEX idx_product_images_format ON product_images(format);

-- Category indexes
CREATE INDEX idx_categories_slug ON categories(slug);
CREATE INDEX idx_categories_parent_id ON categories(parent_id);
CREATE INDEX idx_categories_active ON categories(active);

-- Messaging indexes
CREATE INDEX idx_conversations_user1_id ON conversations(user1_id);
CREATE INDEX idx_conversations_user2_id ON conversations(user2_id);
CREATE INDEX idx_conversations_last_message ON conversations(last_message_at DESC);
CREATE INDEX idx_messages_conversation_id ON messages(conversation_id);
CREATE INDEX idx_messages_sender_id ON messages(sender_id);
CREATE INDEX idx_messages_created_at ON messages(created_at DESC);

-- Notification indexes
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);
CREATE INDEX idx_notifications_type ON notifications(type);
CREATE INDEX idx_notifications_created_at ON notifications(created_at DESC);

-- Rating indexes
CREATE INDEX idx_ratings_rater_id ON ratings(rater_id);
CREATE INDEX idx_ratings_rated_user_id ON ratings(rated_user_id);
CREATE INDEX idx_ratings_publication_id ON ratings(publication_id);
CREATE INDEX idx_ratings_score ON ratings(score);

-- Favorite indexes
CREATE INDEX idx_favorites_user_id ON favorites(user_id);
CREATE INDEX idx_favorites_publication_id ON favorites(publication_id);
CREATE INDEX idx_favorite_lists_user_id ON favorite_lists(user_id);

-- Report indexes
CREATE INDEX idx_reports_reporter_id ON reports(reporter_id);
CREATE INDEX idx_reports_entity_type_id ON reports(reported_entity_type, reported_entity_id);
CREATE INDEX idx_reports_status ON reports(status);
CREATE INDEX idx_reports_priority ON reports(priority);
CREATE INDEX idx_reports_created_at ON reports(created_at DESC);

-- =============================================
-- INITIAL DATA
-- =============================================

-- Insert default roles
INSERT INTO roles (name, description, created_at) VALUES
                                                      ('ROLE_USER', 'Regular user with basic permissions', NOW()),
                                                      ('ROLE_ADMIN', 'Administrator with full system access', NOW()),
                                                      ('ROLE_MODERATOR', 'Moderator with content management permissions', NOW()),
                                                      ('ROLE_PREMIUM', 'Premium user with additional features', NOW());

-- Insert default categories
INSERT INTO categories (name, slug, description, active, display_order, icon_url, created_at) VALUES
                                                                                                  ('Electronics', 'electronics', 'Electronic devices, computers, phones, and gadgets', true, 1, '/icons/electronics.svg', NOW()),
                                                                                                  ('Vehicles', 'vehicles', 'Cars, motorcycles, boats, and vehicle parts', true, 2, '/icons/vehicles.svg', NOW()),
                                                                                                  ('Home & Garden', 'home-garden', 'Furniture, appliances, and garden equipment', true, 3, '/icons/home.svg', NOW()),
                                                                                                  ('Fashion', 'fashion', 'Clothing, shoes, accessories, and jewelry', true, 4, '/icons/fashion.svg', NOW()),
                                                                                                  ('Sports & Outdoors', 'sports-outdoors', 'Sports equipment, camping gear, and outdoor activities', true, 5, '/icons/sports.svg', NOW()),
                                                                                                  ('Books & Media', 'books-media', 'Books, music, movies, and video games', true, 6, '/icons/books.svg', NOW()),
                                                                                                  ('Toys & Games', 'toys-games', 'Toys, board games, and collectibles', true, 7, '/icons/toys.svg', NOW()),
                                                                                                  ('Real Estate', 'real-estate', 'Properties for sale or rent', true, 8, '/icons/real-estate.svg', NOW()),
                                                                                                  ('Services', 'services', 'Professional and personal services', true, 9, '/icons/services.svg', NOW()),
                                                                                                  ('Other', 'other', 'Items that don''t fit other categories', true, 10, '/icons/other.svg', NOW());

-- Insert subcategories for Electronics
INSERT INTO categories (name, slug, description, parent_id, active, display_order, created_at)
SELECT 'Smartphones', 'smartphones', 'Mobile phones and accessories', id, true, 1, NOW()
FROM categories WHERE slug = 'electronics'
UNION ALL
SELECT 'Laptops', 'laptops', 'Notebooks and portable computers', id, true, 2, NOW()
FROM categories WHERE slug = 'electronics'
UNION ALL
SELECT 'Tablets', 'tablets', 'Tablets and e-readers', id, true, 3, NOW()
FROM categories WHERE slug = 'electronics'
UNION ALL
SELECT 'TVs & Audio', 'tvs-audio', 'Televisions and sound systems', id, true, 4, NOW()
FROM categories WHERE slug = 'electronics'
UNION ALL
SELECT 'Cameras', 'cameras', 'Digital cameras and photography equipment', id, true, 5, NOW()
FROM categories WHERE slug = 'electronics';

-- =============================================
-- TRIGGERS FOR UPDATED_AT
-- =============================================

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ language 'plpgsql';

-- Apply trigger to all tables with updated_at
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_user_profiles_updated_at BEFORE UPDATE ON user_profiles
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_categories_updated_at BEFORE UPDATE ON categories
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_publications_updated_at BEFORE UPDATE ON publications
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_conversations_updated_at BEFORE UPDATE ON conversations
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_messages_updated_at BEFORE UPDATE ON messages
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_notification_preferences_updated_at BEFORE UPDATE ON notification_preferences
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_ratings_updated_at BEFORE UPDATE ON ratings
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_favorite_lists_updated_at BEFORE UPDATE ON favorite_lists
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_reports_updated_at BEFORE UPDATE ON reports
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();