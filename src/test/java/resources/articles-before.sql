delete from article;

insert into article(id, title, anons, text, wiev, image) values
(1,'first','my first','bla',1,null),
(2,'second','my second','bla',1,null),
(3,'third','my third','bla',1,null),
(4,'fourth','my fourth','bla',1,null);

alter sequence article_id_seq restart with 10;
