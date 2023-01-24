
create user 'spring'@'localhost' identified by 'springpw';

create database spring character set='utf8MEMBERMEMBER';

grant all privileges on spring.* to 'spring'@'localhost';

create table spring.MEMBER(
                              ID int auto_increment primary key,
                              EMAIL varchar(255),
                              PASSWORD varchar(100),
                              NAME varchar(200),
                              REGDATE datetime,
                              unique key (EMAIL)
) engine=InnoDB character set = 'utf8';



