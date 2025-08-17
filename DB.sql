insert into country_master value(1 ,'India');
insert into country_master value(2 ,'USA');

insert into state_master (state_id, state_name, country_id) value(1 ,'MP',1);
insert into state_master (state_id, state_name, country_id) value(2 ,'UP',1);

insert into state_master (state_id, state_name, country_id) value(3 ,'RI',2);
insert into state_master (state_id, state_name, country_id) value(4 ,'NJ',2);

insert into city_master (city_id, city_name, state_id) value(1 ,'Rewa',1);
insert into city_master (city_id, city_name, state_id) value(2 ,'Bhopal',1);

insert into city_master (city_id, city_name, state_id) value(3 ,'Lucknow',2);
insert into city_master (city_id, city_name, state_id) value(4 ,'Kanpur',2);

insert into city_master (city_id, city_name, state_id) value(5 ,'Providence',3);
insert into city_master (city_id, city_name, state_id) value(6 ,'New Port',3);

insert into city_master (city_id, city_name, state_id) value(7 ,'Trenton',4);
insert into city_master (city_id, city_name, state_id) value(8 ,'Newark',4);