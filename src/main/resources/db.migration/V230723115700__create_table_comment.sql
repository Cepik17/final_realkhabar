create table if not exists comment
(
    id bigserial not null primary key,
    comment_text text,
    comment_time timestamp,
    author_id bigint,
    article_id bigint,
    foreign key (author_id) references users (id),
    foreign key (article_id) references article (id)
)