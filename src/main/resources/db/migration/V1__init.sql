create sequence author_seq;
create sequence book_seq;

create table author (
        id bigserial primary key,
        birthday timestamp not null,
        email varchar(255) unique not null,
        fio varchar(255) not null
);

create table book (
        id bigserial primary key,
        number_of_pages integer not null,
        title varchar(255) not null,
        author_id bigint not null,
        constraint book_author_fk foreign key (author_id)
            references author (id) match simple
            on update no action on delete no action
);

create index emailloc ON author(email);
create index titleloc ON book (title);

