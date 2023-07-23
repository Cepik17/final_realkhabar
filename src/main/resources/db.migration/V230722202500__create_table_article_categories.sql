create table if not exists article_categories
(
    article_id bigint not null,
    categories_id bigint not null,
    foreign key (article_id) references article (id),
    foreign key (categories_id) references category (id)
)