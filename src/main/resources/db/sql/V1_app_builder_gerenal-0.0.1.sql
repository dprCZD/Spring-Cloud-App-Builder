-- DROP DATBASE IF EXISTS app_builder ;
-- CREATE DATBASE app_builder CHARACTER SET UTF8 ;
-- USE app_builder ;
CREATE TABLE IF NOT EXISTS image_build (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    build_id VARCHAR(100) UNIQUE NOT NULL,
    image_name VARCHAR(100) ,
    tenant_name VARCHAR(100),
    status VARCHAR(100),
    create_time DATETIME NOT NULL,
    create_user VARCHAR(100),
    update_time DATETIME,
    update_user VARCHAR(100)
);
