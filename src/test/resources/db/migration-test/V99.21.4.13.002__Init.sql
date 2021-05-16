create
    table
        user(
            id bigint unsigned auto_increment,
            username varchar(255) not null,
            email varchar(255) not null,
            password varchar(255) not null,
            avatar varchar(255),
            phone varchar(255),
            description text,
            created_at datetime default current_timestamp,
            primary key(id),
            unique(
                id,
                username,
                email
            )
        );

create
    table
        composition(
            id bigint unsigned auto_increment,
            author bigint unsigned not null,
            name varchar(255) not null,
            description text not null,
            created_at datetime default current_timestamp,
            lastModified datetime,
            heat bigint unsigned not null,
            likes bigint unsigned not null,
            dislikes bigint unsigned not null,
            image varchar(1024),
            stock bigint,
            price double,
            primary key(id),
            unique(id),
            foreign key(author) references user(id)
        );

create
    table
        comment(
            id bigint unsigned auto_increment,
            author bigint unsigned not null,
            composition_id bigint not null,
            content text not null,
            created_at datetime default current_timestamp,
            lastModified datetime,
            likes bigint unsigned not null,
            dislikes bigint unsigned not null,
            primary key(id),
            unique(id),
            foreign key(author) references user(id)
        );

create
    table
        orders(
            id bigint unsigned auto_increment,
            customer_id bigint unsigned not null,
            orderDate datetime not null,
            requiredDate datetime not null,
            shippedDate datetime not null,
            memo text,
            status int not null,
            primary key(id),
            unique(id),
            foreign key(customer_id) references user(id)
        );

create
    table
        follow(
            id bigint unsigned auto_increment,
            followee_id bigint unsigned not null,
            follower_id bigint unsigned not null,
            primary key(id),
            unique(id)
        );
