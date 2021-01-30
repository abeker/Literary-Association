-- noinspection SpellCheckingInspectionForFile

insert into literary_user(id, username, password) values
('f538aab6-31b1-11eb-adc1-0242ac120002', 'admin', '$2y$12$pX2eXOUilJhK7ywx3qhcoe8fE6knGB7Mbp.H04DtaJYlmKXcueoEm');
insert into literary_user(id, username, password) values
('a351022a-cc7a-4a17-a79b-8e0cf258db08', 'admin1', '$2y$12$pX2eXOUilJhK7ywx3qhcoe8fE6knGB7Mbp.H04DtaJYlmKXcueoEm');

--<Role>123!!!
insert into user_entity(id, city, country, deleted, email, first_name, last_name, last_password_reset_date, password, user_type, username, is_approved) values
    ('f219fc63-be00-42c2-86d0-6c2d9e969709', 'Novi Sad', 'Serbia', false, 'admin@gmail.com', 'Glen', 'Greenwald', '2020-11-12 21:58:58.508-07', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'ADMIN', 'admin', true),
    ('2b052fd4-fbc0-462f-8db3-650b3c89e20a', 'Belgrade', 'Serbia', false, 'editor@gmail.com', 'Marc', 'Calmer', '2020-11-12 21:58:58.508-07', '$2y$12$NXtdgD/KkoCIRKBz3nK2OOvje.3l9P.Lsu/Ya5FYsleceiki6e.7G', 'EDITOR', 'editor', true),
    ('be18edb8-2bd1-4d77-a0b9-9edf9e66a4d2', 'Nis', 'Serbia', false, 'majorEditor@gmail.com', 'Marek', 'Hamsik', '2020-11-12 21:58:58.508-07', '$2y$12$NXtdgD/KkoCIRKBz3nK2OOvje.3l9P.Lsu/Ya5FYsleceiki6e.7G', 'EDITOR', 'm_editor', true),
    ('cfd4b4a6-4d0b-4b33-bc25-3e87607b4f24', 'Banja Luka', 'Bosnia and Herzegovina', false, 'lector@gmail.com', 'Vic', 'Kohen', '2020-11-12 21:58:58.508-07', '$2y$12$fDPoGa8V.lq7DBojL6aAZ.2MEUZOorerDFiwT45ZBJa19tFDEeeGq', 'LECTOR', 'lector', true),
    ('f4d9353c-be37-4272-b631-7262e2e2d28b', 'Podgorica', 'Montenegro', false, 'reader@gmail.com', 'Andrey', 'Grim', '2020-11-12 21:58:58.508-07', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader', true),
    ('16805d59-3133-4ee5-9a42-6fc2578a627a', 'Niksic', 'Montenegro', false, 'reader1@gmail.com', 'John', 'Grim', '2020-11-12 21:58:58.508-07', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader1', true),
    ('fb70ab80-925d-4b4a-8eab-97728e46fac1', 'Bijelo Polje', 'Montenegro', false, 'reader2@gmail.com', 'Jack', 'Grim', '2020-11-12 21:58:58.508-07', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader2', true),
    ('70249358-0f4d-4010-9361-26c5e533a979', 'Novi Sad', 'Serbia', false, 'writer@gmail.com', 'Marc', 'Paliestri', '2020-11-12 21:58:58.508-07', '$2y$12$bAOFqaGgFr4CKERAoscGDu9SzjHG29MM8c9zEPqVX.AeQKo6hpGBe', 'WRITER', 'writer', true),
    ('0896abd9-932d-4998-8a83-2a00d9bf77e5', 'Subotica', 'Serbia', false, 'majorCommittee@gmail.com', 'Garet', 'Potson', '2020-11-12 21:58:58.508-07', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'm_committee', true),
    ('3166f4d2-cb44-401a-bca4-32f9e871ebf2', 'Leskovac', 'Serbia', false, 'thirdCommittee@gmail.com', 'Mika', 'Mikic', '2020-11-12 21:58:58.508-07', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'committee', true),
    ('ca4e550a-b66f-46b0-a27e-52551a6fb012', 'Kikinda', 'Serbia', false, 'oneCommittee@gmail.com', 'Zika', 'Zikic', '2020-11-12 21:58:58.508-07', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'committee1', true),
    ('d0f20303-468b-4ebe-bb23-48003fc8a38e', 'Valjevo', 'Serbia', false, 'secondCommittee@gmail.com', 'Jova', 'Jovic', '2020-11-12 21:58:58.508-07', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'committee2', true);


insert into membership (id, amount, date_opened, date_closed) values
    ('ff5c1778-c150-4ea9-9100-a4c78d9b81a6', 1000, '2020-02-11', null),
    ('53d377d7-506f-4723-89e3-3c51debb182d', 2000, '2020-12-21', null),
    ('1497507f-7f27-4f66-9383-0dfb3f02e4f8', 500, '2020-02-12', null);

insert into writer_membership_status (id, try_counter)
values ('334567b2-645d-4102-9688-a6d30563b5aa', 1);

insert into editor (major, id) values
    (false, '2b052fd4-fbc0-462f-8db3-650b3c89e20a'),
    (true, 'be18edb8-2bd1-4d77-a0b9-9edf9e66a4d2');
insert into lector (id) values ('cfd4b4a6-4d0b-4b33-bc25-3e87607b4f24');
insert into reader (id) values ('f4d9353c-be37-4272-b631-7262e2e2d28b');
insert into committee_member (id, major) values ('0896abd9-932d-4998-8a83-2a00d9bf77e5', true);
insert into writer (id, writer_membership_status_id, membership_id) values 
('70249358-0f4d-4010-9361-26c5e533a979', '334567b2-645d-4102-9688-a6d30563b5aa', 'ff5c1778-c150-4ea9-9100-a4c78d9b81a6');
insert into reader (id) values ('16805d59-3133-4ee5-9a42-6fc2578a627a');
insert into reader (id) values ('fb70ab80-925d-4b4a-8eab-97728e46fac1');
insert into committee_member (id, major) values ('ca4e550a-b66f-46b0-a27e-52551a6fb012', false);
insert into committee_member (id, major) values ('d0f20303-468b-4ebe-bb23-48003fc8a38e', false);

insert into writer (id, writer_membership_status_id, membership_id) values ('0896abd9-932d-4998-8a83-2a00d9bf77e5', '334567b2-645d-4102-9688-a6d30563b5aa', 'ff5c1778-c150-4ea9-9100-a4c78d9b81a6');

insert into genre(id, code, genre_name, deleted) values ('b10c65ec-3a63-47c5-a87c-500aa71d9427', '5656545', 'thriller', false);
insert into genre(id, code, genre_name, deleted) values ('10b2d90b-0cbc-4ec2-b165-6c5c959737c6', '546484978', 'fiction', false);
insert into genre(id, code, genre_name, deleted) values ('fb5443bd-3b1a-40cf-a979-d7ad9c45e315', '15689546', 'poetry', false);

insert into beta_reader (id, penalty_point, reader_id) values
('51871e79-679f-4b57-9bce-f53f5fcb7e36', 0, 'f4d9353c-be37-4272-b631-7262e2e2d28b'),
('96e0c136-6f86-45c6-af54-46dd36ed6c5c', 0, '16805d59-3133-4ee5-9a42-6fc2578a627a'),
('d87974f4-ff25-4e8f-bbe2-98062bf733b6', 0, 'fb70ab80-925d-4b4a-8eab-97728e46fac1');


insert into beta_reader_genres (beta_reader_id, genres_id) values
    ('51871e79-679f-4b57-9bce-f53f5fcb7e36', 'b10c65ec-3a63-47c5-a87c-500aa71d9427'),
    ('51871e79-679f-4b57-9bce-f53f5fcb7e36', 'fb5443bd-3b1a-40cf-a979-d7ad9c45e315'),
    ('96e0c136-6f86-45c6-af54-46dd36ed6c5c', 'fb5443bd-3b1a-40cf-a979-d7ad9c45e315'),
    ('d87974f4-ff25-4e8f-bbe2-98062bf733b6', '10b2d90b-0cbc-4ec2-b165-6c5c959737c6');


insert into editor_comment (id, comment_text, editor_id) values
    ('cd484a86-fd3e-4bc3-80c8-f81182787769', 'This is awesome!', '2b052fd4-fbc0-462f-8db3-650b3c89e20a');

insert into book_request (id, synopsis, title, editor_comment_id, approved) values
    ('e38d8dad-0327-4b6a-ac04-b74a509946a0', 'This is great synopsis', 'The Great Idea', 'cd484a86-fd3e-4bc3-80c8-f81182787769', true);

insert into handwrite (id, handwrite_file_name, book_request_id) values
    ('e95aaf6e-2ac6-49ac-a486-d9d7923c8e4e', 'C:\Program Files\Handwrites', 'e38d8dad-0327-4b6a-ac04-b74a509946a0');

insert into book (id, isbn, publish_year, publish_place, number_of_pages, deleted, book_request_id) values
    ('fc8eeb62-9f4f-42bf-b344-90102672a554', '978-3-16-148410-0', '2012-11-11', 'Futog', 317, false, 'e38d8dad-0327-4b6a-ac04-b74a509946a0');