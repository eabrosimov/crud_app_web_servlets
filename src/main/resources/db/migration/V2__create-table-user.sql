create table "user"(
    id int generated by default as identity primary key,
    name varchar(25) unique not null
)