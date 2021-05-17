create
    table
        user(
            id bigint unsigned auto_increment,
            username varchar(255) not null unique,
            email varchar(255) not null unique,
            password varchar(255) not null,
            account_expired boolean not null default false,
            account_locked boolean not null default false,
            credentials_expired boolean not null default false,
            enabled boolean not null default true,
            nickname varchar(255),
            avatar varchar(255),
            phone varchar(255),
            description text,
            created_at datetime default current_timestamp,
            primary key(id),
            index
                using btree(username),
            index
                using btree(email)
        );

create
    table
        composition(
            id bigint unsigned auto_increment,
            author_id bigint unsigned not null,
            name varchar(255) not null,
            description text not null,
            image varchar(1024),
            likes bigint unsigned,
            viewed bigint unsigned,
            status varchar(20) not null default 'DRAFT',
            created_at datetime default current_timestamp,
            last_modified datetime,
            issued_at datetime,
            for_sale boolean not null default false,
            primary key(id)
        );

create
    table
        product(
            id bigint unsigned auto_increment,
            price decimal(
                65,
                2
            ),
            stock bigint,
            primary key(id),
            foreign key(id) references composition(id)
        );

create
    table
        cart(
            owner_id bigint unsigned not null,
            product_id bigint unsigned not null,
            quantity int not null default 0,
            primary key(
                owner_id,
                product_id
            ),
            foreign key(owner_id) references user(id),
            foreign key(product_id) references product(id)
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
            foreign key(customer_id) references user(id)
        );

create
    table
        follow(
            id bigint unsigned auto_increment,
            followee_id bigint unsigned not null,
            follower_id bigint unsigned not null,
            primary key(id)
        );
