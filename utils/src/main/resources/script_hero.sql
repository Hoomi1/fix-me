drop table if exists transactions;
drop table if exists fix_me;

create  table if not exists transactions(
    id serial primary key,
    nameT varchar(1000) not null,
    code varchar(1000) not null,
    amount int8 not null,
    min_buy double precision not null,
    min_sell double precision not null
    );

create table if not exists fix_me(
    transaction varchar(1000)
);

insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Bitcoin',       'BTC',  208,    382.0,  557.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Ethereum',      'ETH',  685,    919.0,  12.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Tether',        'USDT', 387,    499.0,  874.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('USD_Coin',      'USDC', 683,    440.0,  982.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('BNB',           'BNB',  424,    758.0,  293.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Binance_USD',   'BUSD', 625,    182.0,  891.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Solana',        'SOL',  761,    698.0,  786.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Dogecoin',      'DOGE', 471,    29.0,   78.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Polkadot',      'DOT',  585,    857.0,  150.0);
insert into my_db.public.transactions (namet, code, amount, min_buy, min_sell) VALUES ('Polygon',       'MATIC',253,    642.0,  661.0);


