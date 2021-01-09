-- noinspection SpellCheckingInspectionForFile

insert into literary_user(id, username, password) values
('f538aab6-31b1-11eb-adc1-0242ac120002', 'admin', '$2y$12$pX2eXOUilJhK7ywx3qhcoe8fE6knGB7Mbp.H04DtaJYlmKXcueoEm');
insert into literary_user(id, username, password) values
('a351022a-cc7a-4a17-a79b-8e0cf258db08', 'admin1', '$2y$12$pX2eXOUilJhK7ywx3qhcoe8fE6knGB7Mbp.H04DtaJYlmKXcueoEm');

--<Role>123!!!
insert into user_entity(id, city, country, deleted, email, first_name, last_name, last_password_reset_date, password, user_type, username)
values ('f219fc63-be00-42c2-86d0-6c2d9e969709', 'Novi Sad', 'Serbia', false, 'admin@gmail.com', 'Glen', 'Greenwald', '2020-11-12 21:58:58.508-07', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'ADMIN', 'admin');
insert into user_entity(id, city, country, deleted, email, first_name, last_name, last_password_reset_date, password, user_type, username)
values ('2b052fd4-fbc0-462f-8db3-650b3c89e20a', 'Belgrade', 'Serbia', false, 'editor@gmail.com', 'Marc', 'Calmer', '2020-11-12 21:58:58.508-07', '$2y$12$NXtdgD/KkoCIRKBz3nK2OOvje.3l9P.Lsu/Ya5FYsleceiki6e.7G', 'EDITOR', 'editor');
insert into user_entity(id, city, country, deleted, email, first_name, last_name, last_password_reset_date, password, user_type, username)
values ('cfd4b4a6-4d0b-4b33-bc25-3e87607b4f24', 'Banja Luka', 'Bosnia and Herzegovina', false, 'lector@gmail.com', 'Vic', 'Kohen', '2020-11-12 21:58:58.508-07', '$2y$12$fDPoGa8V.lq7DBojL6aAZ.2MEUZOorerDFiwT45ZBJa19tFDEeeGq', 'LECTOR', 'lector');
insert into user_entity(id, city, country, deleted, email, first_name, last_name, last_password_reset_date, password, user_type, username)
values ('f4d9353c-be37-4272-b631-7262e2e2d28b', 'Podgorica', 'Montenegro', false, 'reader@gmail.com', 'Andrey', 'Grim', '2020-11-12 21:58:58.508-07', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader');
insert into user_entity(id, city, country, deleted, email, first_name, last_name, last_password_reset_date, password, user_type, username)
values ('0896abd9-932d-4998-8a83-2a00d9bf77e5', 'Subotica', 'Serbia', false, 'committee@gmail.com', 'Garet', 'Potson', '2020-11-12 21:58:58.508-07', '$2y$12$gybDgJIv2AUWRDF5cXcrBukmP/SHshcXRic1QyRvq5ymHJK0Siqnu', 'COMMITTEE_MEMBER', 'committee');

insert into editor (approved, major, id)
values (true, true, '2b052fd4-fbc0-462f-8db3-650b3c89e20a');
insert into lector (approved, id)
values (true, '2b052fd4-fbc0-462f-8db3-650b3c89e20a');
insert into reader (approved, id)
values (true, 'f4d9353c-be37-4272-b631-7262e2e2d28b');
insert into committee_member (id, major)
values ('0896abd9-932d-4998-8a83-2a00d9bf77e5', true);

insert into genre(id, code, genre_name)
values ('b10c65ec-3a63-47c5-a87c-500aa71d9427', '5656545', 'thriller');
insert into genre(id, code, genre_name)
values ('10b2d90b-0cbc-4ec2-b165-6c5c959737c6', '546484978', 'fiction');
insert into genre(id, code, genre_name)
values ('fb5443bd-3b1a-40cf-a979-d7ad9c45e315', '15689546', 'poetry');