truncate users cascade;
truncate authorities cascade;
truncate assignments cascade;
truncate site_engineer cascade;
truncate works_engineer cascade;
truncate site cascade;
truncate supplier cascade;
truncate plant_hire_request cascade;

insert into users VALUES (0, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'siteengineer@buildit.com', 0);
insert into users VALUES (1, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'worksengineer@buildit.com', 0);
insert into users VALUES (2, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin', 0);
insert into users VALUES (3, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user@rentit.com', 0);

insert into authorities VALUES (0, 'ROLE_SITE_ENGINEER', 0);
insert into authorities VALUES (1, 'ROLE_WORKS_ENGINEER', 0);
insert into authorities VALUES (2, 'ROLE_ADMIN', 0);
insert into authorities VALUES (3, 'ROLE_SUPPLIER', 0);

insert into assignments VALUES(0, 0, 0, 0);
insert into assignments VALUES(1, 0, 1, 1);
insert into assignments VALUES(2, 0, 2, 2);
insert into assignments VALUES(3, 0, 3, 3);

insert into site_engineer VALUES (0, 'siteengineer@buildit.com', 'Bob', 'Builder', 0);
insert into works_engineer VALUES (0, 'worksengineer@buildit.com', 'Mike', 'Worker', 0);

insert into site VALUES (0, 'BuildingX', 0);
insert into supplier VALUES (0, 'Rentit', 0);
