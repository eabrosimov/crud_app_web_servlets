create table event(
    id int generated by default as identity primary key,
    user_id int references "user"(id) on delete cascade,
    file_id int references file(id) on delete cascade
)