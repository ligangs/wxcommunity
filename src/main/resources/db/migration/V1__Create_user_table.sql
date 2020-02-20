create table user
(
	id bigint auto_increment,
	open_id varchar(50) not null,
	nick_name varchar(50) not null,
	avatar_url varchar(256) not null,
	token char(36) not null,
	gmt_create bigint not null,
	gmt_modified bigint not null,
	constraint user_pk
		primary key (id)
);