-- ============================================================
--  Movie Booking System – Database Schema
--  MySQL 8.x  |  DB: movie_booking_db
-- ============================================================

CREATE DATABASE IF NOT EXISTS movie_booking_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE movie_booking_db;

-- -------------------------------------------------------
-- 1. Theatres
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS theatres (
    id          VARCHAR(36)  PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    location    VARCHAR(500) NOT NULL,
    total_seats INT          NOT NULL DEFAULT 100,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------------------------
-- 2. Movies  (each movie belongs to exactly one theatre)
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS movies (
    id           VARCHAR(36)    PRIMARY KEY,
    theatre_id   VARCHAR(36)    NOT NULL,
    title        VARCHAR(255)   NOT NULL,
    genre        VARCHAR(100)   NOT NULL,
    language     VARCHAR(100)   NOT NULL DEFAULT 'English',
    duration_min INT            NOT NULL COMMENT 'Duration in minutes',
    show_time    DATETIME       NOT NULL,
    ticket_price DECIMAL(10,2)  NOT NULL,
    available_seats INT         NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_movie_theatre FOREIGN KEY (theatre_id)
        REFERENCES theatres(id) ON DELETE CASCADE
);

-- -------------------------------------------------------
-- 3. Users
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id             VARCHAR(36)   PRIMARY KEY,
    name           VARCHAR(255)  NOT NULL,
    email          VARCHAR(255)  NOT NULL UNIQUE,
    phone          VARCHAR(20)   NOT NULL,
    password_hash  VARCHAR(255)  NOT NULL,
    wallet_balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    created_at     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------------------------
-- 4. Bookings
-- -------------------------------------------------------
CREATE TABLE IF NOT EXISTS bookings (
    id             VARCHAR(36)   PRIMARY KEY,
    user_id        VARCHAR(36)   NOT NULL,
    movie_id       VARCHAR(36)   NOT NULL,
    seats_booked   INT           NOT NULL DEFAULT 1,
    total_amount   DECIMAL(10,2) NOT NULL,
    status         ENUM('CONFIRMED','CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
    booked_at      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelled_at   TIMESTAMP     NULL,
    CONSTRAINT fk_booking_user  FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    CONSTRAINT fk_booking_movie FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);