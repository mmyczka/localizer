CREATE DATABASE localizer;

USE localizer;

-- Create the Items table
CREATE TABLE items (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id INT,
    FOREIGN KEY (parent_id) REFERENCES items(id)
);
