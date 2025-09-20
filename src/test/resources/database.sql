CREATE TABLE public.users
(
	id serial primary key ,
	name_ varchar(128) unique not null,
	email varchar(128) unique not null,
	age int not null,
	created_at date not null
);

insert into users(name_, email, age, created_at)
						values('first',		'first@email.ru',		20,	'01.09.25'),
									('second',	'second@email.ru',	20,	'02.09.25'),
									('third',		'third@email.ru',		20,	'03.09.25'),
									('fourth',	'fourth@email.ru',	20,	'04.09.25');