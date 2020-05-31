--connect 'jdbc:derby:radiocompetition;create=true';

CREATE TABLE competitor (
   id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   person_id VARCHAR(15),
   email varchar(50),
   phone varchar(35),
   points integer,
   PRIMARY KEY (id)
);

CREATE TABLE competition (
   id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   description VARCHAR(255),
   rules VARCHAR(1500),
   start_date timestamp,
   inscription_start_date timestamp,
   inscription_end_date timestamp,
   PRIMARY KEY (id)
);

CREATE TABLE inscription (
   id_competition INTEGER references competition(id),
   id_competitor INTEGER references competitor(id),
   inscription_date timestamp
);


insert into competition(description, rules, start_date, inscription_start_date, inscription_end_date) 
    values('competition 1', 'you must ....', '2020-06-15 00:00:00', '2020-06-01 00:00:00', '2020-06-10 00:00:00');


insert into competition(description, rules, start_date, inscription_start_date, inscription_end_date) 
    values('competition 2', 'you must ....', '2020-07-15 00:00:00', '2020-07-01 00:00:00', '2020-07-10 00:00:00');
