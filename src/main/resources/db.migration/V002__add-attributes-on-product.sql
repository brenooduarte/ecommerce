    alter table if exists tb_product
        add column description varchar(255) not null,
        add column image varchar(255),
        add column active boolean not null,
        add column highlight boolean,
        add column promotion boolean not null,
        add column price_promotion numeric(38,2),
        add column sold int;

    alter table if exists tb_product_order
        alter column created_at set not null;

    alter table if exists tb_product_order
            alter column total set not null;

    alter table if exists tb_product_order
        add column quantity int not null;