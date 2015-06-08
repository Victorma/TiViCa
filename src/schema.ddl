
    create table Cart (
        ip varchar(255) not null,
        primary key (ip)
    );

    create table CartLine (
        id integer not null auto_increment,
        cuantity integer,
        cart_ip varchar(255),
        item_id integer,
        primary key (id)
    );

    create table Config (
        id varchar(255) not null,
        value varchar(255),
        primary key (id)
    );

    create table Item (
        id integer not null auto_increment,
        image varchar(255),
        name varchar(255),
        price double precision,
        stock integer,
        primary key (id)
    );

    alter table CartLine 
        add constraint FK_dxi7bpex9xfancefck8igjpaf 
        foreign key (cart_ip) 
        references Cart (ip);

    alter table CartLine 
        add constraint FK_c7ht1xbod99uvg4354c0udd9k 
        foreign key (item_id) 
        references Item (id);
