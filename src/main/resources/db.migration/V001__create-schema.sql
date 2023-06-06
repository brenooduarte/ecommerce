
    create table tb_address (
       id bigserial not null,
        additional varchar(255),
        cep varchar(40),
        neighborhood varchar(100),
        number varchar(20),
        street varchar(100),
        primary key (id)
    );

    create table tb_city (
       id bigserial not null,
        name varchar(50) not null,
        state_id bigint not null,
        primary key (id)
    );

    create table tb_order (
       id bigserial not null,
        cancellation_date timestamp(6),
        confirmation_date timestamp(6),
        creation_date timestamp(6) not null,
        delivery_date timestamp(6),
        freight_charge numeric(38,2) not null,
        status_order smallint not null,
        subtotal numeric(38,2) not null,
        total_amount numeric(38,2) not null,
        user_customer_id bigint not null,
        primary key (id)
    );

    create table tb_order_items (
       order_id bigint not null,
        items_id bigint not null
    );

    create table tb_product (
       id bigserial not null,
        name varchar(255),
        price float(53) not null,
        primary key (id)
    );

    create table tb_product_order (
       id bigserial not null,
        created_at timestamp(6),
        shipped timestamp(6),
        total float(53) not null,
        order_id bigint not null,
        product_id bigint not null,
        primary key (id)
    );

    create table tb_state (
       id bigserial not null,
        name varchar(40) not null,
        primary key (id)
    );

    create table tb_user (
       id bigserial not null,
        email varchar(40),
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table tb_user_address (
       id bigserial not null,
        address_type varchar(255),
        tb_address bigint,
        tb_user bigint,
        primary key (id)
    );

    alter table if exists tb_order_items
       add constraint UK_OrderItems_ItemsId unique (items_id);

    alter table if exists tb_city
       add constraint FK_City_State
       foreign key (state_id)
       references tb_state;

    alter table if exists tb_order
       add constraint FK_Order_User
       foreign key (user_customer_id)
       references tb_user;

    alter table if exists tb_order_items
       add constraint FK_OrderItems_ProductOrder
       foreign key (items_id)
       references tb_product_order;

    alter table if exists tb_order_items
       add constraint FK_OrderItems_Order
       foreign key (order_id)
       references tb_order;

    alter table if exists tb_product_order
       add constraint FK_ProductOrder_Order
       foreign key (order_id)
       references tb_order;

    alter table if exists tb_product_order
       add constraint FK_ProductOrder_Product
       foreign key (product_id)
       references tb_product;

    alter table if exists tb_user_address
       add constraint FK_UserAddress_Address
       foreign key (tb_address)
       references tb_address;

    alter table if exists tb_user_address
       add constraint FK_UserAddress_User
       foreign key (tb_user)
       references tb_user;