CREATE
    TABLE
        USER(
            id BIGINT unsigned auto_increment,
            username VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL,
            password VARCHAR(255) NOT NULL,
            avatar VARCHAR(255),
            phone VARCHAR(255),
            description text,
            created_at datetime DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY(id),
            UNIQUE(
                id,
                username,
                email
            )
        );

CREATE
    TABLE
        composition(
            id BIGINT unsigned auto_increment,
            author BIGINT unsigned NOT NULL,
            name VARCHAR(255) NOT NULL,
            description text NOT NULL,
            created_at datetime DEFAULT CURRENT_TIMESTAMP,
            lastModified datetime,
            heat BIGINT unsigned NOT NULL,
            likes BIGINT unsigned NOT NULL,
            dislikes BIGINT unsigned NOT NULL,
            image VARCHAR(1024),
            stock BIGINT,
            price DOUBLE,
            PRIMARY KEY(id),
            UNIQUE(id),
            FOREIGN KEY(author) REFERENCES USER(id)
        );

CREATE
    TABLE
        comment(
            id BIGINT unsigned auto_increment,
            author BIGINT unsigned NOT NULL,
            composition_id BIGINT NOT NULL,
            content text NOT NULL,
            created_at datetime DEFAULT CURRENT_TIMESTAMP,
            lastModified datetime,
            likes BIGINT unsigned NOT NULL,
            dislikes BIGINT unsigned NOT NULL,
            PRIMARY KEY(id),
            UNIQUE(id),
            FOREIGN KEY(author) REFERENCES USER(id)
        );

CREATE
    TABLE
        orders(
            id BIGINT unsigned auto_increment,
            customer_id BIGINT unsigned NOT NULL,
            orderDate datetime NOT NULL,
            requiredDate datetime NOT NULL,
            shippedDate datetime NOT NULL,
            memo text,
            status INT NOT NULL,
            PRIMARY KEY(id),
            UNIQUE(id),
            FOREIGN KEY(customer_id) REFERENCES USER(id)
        );

CREATE
    TABLE
        follow(
            id BIGINT unsigned auto_increment,
            followee_id BIGINT unsigned NOT NULL,
            follower_id BIGINT unsigned NOT NULL,
            PRIMARY KEY(id),
            UNIQUE(id)
        );
