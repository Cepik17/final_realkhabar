create table if not exists article
(
    id bigserial not null primary key,
    title text,
    description varchar(255),
    text text,
    post_time timestamp,
    img_url text,
    is_news_of_the_day boolean,
    author_id bigint,
    foreign key (author_id) references users (id)
)