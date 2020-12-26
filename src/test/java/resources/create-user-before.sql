delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1,true,'$2a$08$kXdMpsNYfgMkE.y4R8TFSO8UPDNmJE2B1OTGlYy3rnCkYdg5A8EU6','a'),
(2,true,'$2a$08$bwFm0/WHIe2zGzKeb1M17OR4S5pJi4rpZalEvBSb7gxNYSMvqbbi.','mike');
insert into user_role(user_id, roles) values(1,'USER'),(2,'USER');