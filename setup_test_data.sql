insert into users VALUES (0, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'siteengineer@buildit.com', 0);
insert into users VALUES (1, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'worksengineer@buildit.com', 0);
insert into users VALUES (2, 't', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin', 0);

insert into authorities VALUES (0, 'ROLE_SITE_ENGINEER', 0);
insert into authorities VALUES (1, 'ROLE_WORKS_ENGINEER', 0);
insert into authorities VALUES (2, 'ROLE_ADMIN', 0);

insert into assignments VALUES(0, 0, 0, 0);
insert into assignments VALUES(1, 0, 1, 1);
insert into assignments VALUES(2, 0, 2, 2);

insert into site_engineer VALUES (0, 'siteengineer@buildit.com', 'Bob', 'Builder', 0);
insert into works_engineer VALUES (0, 'worksengineer@buildit.com', 'Mike', 'Worker', 0);
