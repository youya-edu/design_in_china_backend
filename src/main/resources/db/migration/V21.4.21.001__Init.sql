CREATE
    TABLE
        USER(
            id BIGINT unsigned auto_increment,
            username VARCHAR(255) NOT NULL UNIQUE,
            email VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL,
            account_expired BOOLEAN NOT NULL DEFAULT FALSE,
            account_locked BOOLEAN NOT NULL DEFAULT FALSE,
            credentials_expired BOOLEAN NOT NULL DEFAULT FALSE,
            enabled BOOLEAN NOT NULL DEFAULT TRUE,
            nickname VARCHAR(255),
            avatar VARCHAR(255),
            phone VARCHAR(255),
            description text,
            created_at datetime DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY(id),
            INDEX
                USING btree(username),
            INDEX
                USING btree(email)
        );

CREATE
    TABLE
        composition(
            id BIGINT unsigned auto_increment,
            author_id BIGINT unsigned NOT NULL,
            name VARCHAR(255) NOT NULL,
            description text NOT NULL,
            image VARCHAR(1024),
            likes BIGINT unsigned,
            viewed BIGINT unsigned,
            status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
            created_at datetime DEFAULT CURRENT_TIMESTAMP,
            last_modified datetime,
            issued_at datetime,
            for_sale BOOLEAN NOT NULL DEFAULT FALSE,
            PRIMARY KEY(id)
        );

CREATE
    TABLE
        product(
            id BIGINT unsigned auto_increment,
            price DECIMAL(
                65,
                2
            ),
            stock BIGINT,
            composition_id BIGINT unsigned NOT NULL,
            PRIMARY KEY(id),
            FOREIGN KEY(composition_id) REFERENCES composition(id)
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
            FOREIGN KEY(customer_id) REFERENCES USER(id)
        );

CREATE
    TABLE
        follow(
            id BIGINT unsigned auto_increment,
            followee_id BIGINT unsigned NOT NULL,
            follower_id BIGINT unsigned NOT NULL,
            PRIMARY KEY(id)
        );
