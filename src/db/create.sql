CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(25)
);

CREATE TABLE tokens (
    token VARCHAR(25) PRIMARY KEY,
    roleId INT,

    FOREIGN KEY(roleId) REFERENCES roles(id)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    token VARCHAR(25),

     FOREIGN KEY(token) REFERENCES tokens(token)
);




CREATE TABLE friends (
    first_id INT,
    second_id INT,

    FOREIGN KEY (first_id) REFERENCES users(id),
    FOREIGN KEY (second_id) REFERENCES users(id),
    PRIMARY KEY (first_id, second_id)
);

CREATE TABLE chatrooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE chatroom_user (
    chatroom_id INT NOT NULL,
    user_id INT NOT NULL,

    FOREIGN KEY (chatroom_id) REFERENCES chatrooms(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

