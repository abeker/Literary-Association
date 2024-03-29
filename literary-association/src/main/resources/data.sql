insert into permission (name) values
    ('REGISTERED'),             -- 1
    ('DELETE_UNACTIVE_USERS'),      --2
    ('CREATE_BOOK_REQUEST'), ('APPROVE_BOOK_REQUEST'), ('DENY_BOOK_REQUEST'),       -- 3, 4, 5
    ('VIEW_USER_REQUESTS'), ('APPROVE_WRITER_REQUEST'), ('DENY_WRITER_REQUEST'),        -- 6, 7, 8
    ('SEND_WRITER_REQUEST_COMMENT'), ('SEND_BOOK_REQUEST_COMMENT'),     -- 9, 10
    ('UPLOAD_HANDWRITE'), ('EDIT_HANDWRITE'), ('VIEW_HANDWRITE'),        -- 11, 12, 13
    ('POST_SUGGESTIONS'), ('SEND_TO_PRINT'),            -- 14, 15
    ('VIEW_BOOKS'), ('PURCHASE_BOOK'), ('PURCHASE_MEMBERSHIP'),     -- 16, 17, 18
    ('MARK_PLAGIAT'), ('APPROVE_EDITORS'), ('DENY_EDITORS'), ('APPROVE_LECTORS'), ('DENY_LECTORS'),           -- 19, 20, 21, 22, 23
    ('RETRIEVE_PURCHASED_BOOKS'), ('CREATE_LITERARY_ASSOCIATION'),           -- 24, 25
    ('DELETE_INACTIVE_USERS'), ('GET_SECRET');              -- 26, 27

insert into authority (name) values
    ('ROLE_ADMIN'), ('ROLE_READER'), ('ROLE_WRITER'),
    ('ROLE_BETAREADER'), ('ROLE_COMITTEE'), ('ROLE_EDITOR'),
    ('ROLE_LECTOR'), ('ROLE_ADVANCED_READER');

insert into authorities_permissions (authority_id, permission_id) values
    (1, 1), (1, 2), (1, 16), (1, 20), (1, 21), (1, 22), (1, 23), (1, 25), (1, 26),
    (2, 1), (2, 16), (2, 17), (2, 18), (2, 27),
    (3, 1), (3, 3), (3, 16), (3, 18),
    (4, 1), (4, 10),
    (5, 1), (5, 9), (5, 7), (5, 8), (5, 19),
    (6, 1), (6, 4), (6,5), (6, 12), (6, 13), (6, 14), (6, 15),
    (7, 1), (7, 14),
    (8, 24);

insert into address (id, street_number, city, country, zip_code) values
    ('371e4ee3-2804-4179-a5cb-c5dc91dbd5cb', 'Pionirska 21', 'Futog', 'Serbia', '21410'),
    ('90e5eca8-9faa-4150-ba4c-1f98b46a952d', 'Ive Lole Ribara', 'Novi Sad', 'Serbia', '21400'),
    ('336422ad-39cc-4a12-b555-0a1337ba0d19', 'Dusana Savica', 'Cerevic', 'Serbia', '21311');

insert into membership (id, amount, date_opened, date_closed) values
    ('ff5c1778-c150-4ea9-9100-a4c78d9b81a6', 10, '2020-02-11', null),
    ('53d377d7-506f-4723-89e3-3c51debb182d', 20, '2020-12-21', null),
    ('1497507f-7f27-4f66-9383-0dfb3f02e4f8', 50, '2020-02-12', null);

insert into literary_association (id, name, address_id, membership_id, secret, password) values
    ('d0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Vulkan', '371e4ee3-2804-4179-a5cb-c5dc91dbd5cb', '53d377d7-506f-4723-89e3-3c51debb182d', '$2y$12$EuGJAkwDhO7EkLtptgtBdOUK/LJu4EwNeHsalwx6.lW0Za349XW.K', '$2y$12$.sH7J.TguRCOjm6xTBH1s.QtP7yug9hZXuN2TfQA9SxmYtaozyIBy');

--<Role>123!!!
insert into user_entity(id, city, country, deleted, email, lu_id, first_name, last_name, last_password_reset_date, last_time_active, password, user_type, username, is_approved) values
    ('f219fc63-be00-42c2-86d0-6c2d9e969709', 'Novi Sad', 'Serbia', false, 'admin@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Glen', 'Greenwald', '2020-11-12 21:58:58.508-07', '2021-01-31 12:30:00', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'ADMIN', 'admin', true),
    ('2b052fd4-fbc0-462f-8db3-650b3c89e20a', 'Cerevic', 'Serbia', false, 'editor@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Marc', 'Calmer', '2020-11-12 21:58:58.508-07', '2021-02-03 10:20:00','$2y$12$NXtdgD/KkoCIRKBz3nK2OOvje.3l9P.Lsu/Ya5FYsleceiki6e.7G', 'EDITOR', 'editor', true),
    ('be18edb8-2bd1-4d77-a0b9-9edf9e66a4d2', 'Futog', 'Serbia', false, 'majorEditor@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Marek', 'Hamsik', '2020-11-12 21:58:58.508-07', '2021-01-31 12:00:00', '$2y$12$NXtdgD/KkoCIRKBz3nK2OOvje.3l9P.Lsu/Ya5FYsleceiki6e.7G', 'EDITOR', 'm_editor', true),
    ('cfd4b4a6-4d0b-4b33-bc25-3e87607b4f24', 'Banja Luka', 'Bosnia and Herzegovina', false, 'lector@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Vic', 'Kohen', '2020-11-12 21:58:58.508-07', '2021-01-26 00:30:00', '$2y$12$fDPoGa8V.lq7DBojL6aAZ.2MEUZOorerDFiwT45ZBJa19tFDEeeGq', 'LECTOR', 'lector', true),
    ('f4d9353c-be37-4272-b631-7262e2e2d28b', 'Podgorica', 'Montenegro', false, 'reader@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Andrey', 'Grim', '2020-11-12 21:58:58.508-07', '2021-01-28 17:00:00', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader', true),
    ('16805d59-3133-4ee5-9a42-6fc2578a627a', 'Niksic', 'Montenegro', false, 'reader1@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'John', 'Grim', '2020-11-12 21:58:58.508-07', '2021-02-02 12:40:00', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader1', true),
    ('fb70ab80-925d-4b4a-8eab-97728e46fac1', 'Bijelo Polje', 'Montenegro', false, 'reader2@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Jack', 'Grim', '2020-11-12 21:58:58.508-07', '2021-01-05 08:30:00', '$2y$12$cgOgmH.rBRoBTFde.YLEuOJbZgRykdSivxMXo/UkpE7u0Z4VXFBdK', 'READER', 'reader2', true),
    ('70249358-0f4d-4010-9361-26c5e533a979', 'Novi Sad', 'Serbia', false, 'writer@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Marc', 'Paliestri', '2020-11-12 21:58:58.508-07', '2021-02-03 05:00:00', '$2y$12$bAOFqaGgFr4CKERAoscGDu9SzjHG29MM8c9zEPqVX.AeQKo6hpGBe', 'WRITER', 'writer', true),
    ('06743d0d-4376-483d-bb36-334584458345', 'Pljevlja', 'Montenegro', false, 'secondWriter@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Michel', 'Donald', '2020-11-12 21:58:58.508-07', '2021-02-02 14:25:00', '$2y$12$bAOFqaGgFr4CKERAoscGDu9SzjHG29MM8c9zEPqVX.AeQKo6hpGBe', 'WRITER', 'writer2', true),
    ('0896abd9-932d-4998-8a83-2a00d9bf77e5', 'Subotica', 'Serbia', false, 'majorCommittee@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Garet', 'Potson', '2020-11-12 21:58:58.508-07', '2021-01-28 12:30:00', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'm_committee', true),
    ('3166f4d2-cb44-401a-bca4-32f9e871ebf2', 'Leskovac', 'Serbia', false, 'thirdCommittee@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Mika', 'Mikic', '2020-11-12 21:58:58.508-07', '2021-01-24 12:20:00', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'committee', true),
    ('ca4e550a-b66f-46b0-a27e-52551a6fb012', 'Kikinda', 'Serbia', false, 'oneCommittee@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Zika', 'Zikic', '2020-11-12 21:58:58.508-07', '2021-01-21 17:00:00', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'committee1', true),
    ('d0f20303-468b-4ebe-bb23-48003fc8a38e', 'Valjevo', 'Serbia', false, 'secondCommittee@gmail.com', 'd0c213f7-1e95-4ce4-8a65-334071e31ce8', 'Jova', 'Jovic', '2020-11-12 21:58:58.508-07', '2021-01-12 12:41:00', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 'COMMITTE_MEMBER', 'committee2', true);

insert into user_authority (user_id, authority_id) values
    ('f219fc63-be00-42c2-86d0-6c2d9e969709', 1),
    ('2b052fd4-fbc0-462f-8db3-650b3c89e20a', 6),
    ('be18edb8-2bd1-4d77-a0b9-9edf9e66a4d2', 6),
    ('cfd4b4a6-4d0b-4b33-bc25-3e87607b4f24', 7),
    ('f4d9353c-be37-4272-b631-7262e2e2d28b', 2),
    ('16805d59-3133-4ee5-9a42-6fc2578a627a', 2),
    ('fb70ab80-925d-4b4a-8eab-97728e46fac1', 2),
    ('70249358-0f4d-4010-9361-26c5e533a979', 3),
    ('06743d0d-4376-483d-bb36-334584458345', 3),
    ('0896abd9-932d-4998-8a83-2a00d9bf77e5', 5),
    ('3166f4d2-cb44-401a-bca4-32f9e871ebf2', 5),
    ('ca4e550a-b66f-46b0-a27e-52551a6fb012', 5),
    ('d0f20303-468b-4ebe-bb23-48003fc8a38e', 5);

insert into user_membership (id, payment_date, membership_id, user_id) values
    ('f1caff85-d27b-4afa-82fc-6b4bbe55aea4', '2021-01-31 12:00:00', '53d377d7-506f-4723-89e3-3c51debb182d', 'f4d9353c-be37-4272-b631-7262e2e2d28b'),
    ('37da47aa-ee75-4b8a-8c46-ce79752c238f', '2020-12-31 12:25:00', '53d377d7-506f-4723-89e3-3c51debb182d', 'f4d9353c-be37-4272-b631-7262e2e2d28b'),
    ('0115664d-302e-4ef2-8183-bb64c1da1afd', '2020-11-30 12:25:00', '53d377d7-506f-4723-89e3-3c51debb182d', 'fb70ab80-925d-4b4a-8eab-97728e46fac1');

insert into literary_user(id, username, password) values
    ('f538aab6-31b1-11eb-adc1-0242ac120002', 'admin', '$2y$12$pX2eXOUilJhK7ywx3qhcoe8fE6knGB7Mbp.H04DtaJYlmKXcueoEm'),
    ('a351022a-cc7a-4a17-a79b-8e0cf258db08', 'admin1', '$2y$12$pX2eXOUilJhK7ywx3qhcoe8fE6knGB7Mbp.H04DtaJYlmKXcueoEm');

insert into writer_membership_status (id, try_counter) values
    ('334567b2-645d-4102-9688-a6d30563b5aa', 1),
    ('ad201f39-d662-445b-829d-e15e5e035a2e', 1);

insert into editor (major, id) values
    (false, '2b052fd4-fbc0-462f-8db3-650b3c89e20a'),
    (true, 'be18edb8-2bd1-4d77-a0b9-9edf9e66a4d2');

insert into lector (id) values
    ('cfd4b4a6-4d0b-4b33-bc25-3e87607b4f24');

insert into reader (id) values
    ('16805d59-3133-4ee5-9a42-6fc2578a627a'),
    ('f4d9353c-be37-4272-b631-7262e2e2d28b'),
    ('fb70ab80-925d-4b4a-8eab-97728e46fac1');

insert into committee_member (id, major) values
    ('ca4e550a-b66f-46b0-a27e-52551a6fb012', false),
    ('d0f20303-468b-4ebe-bb23-48003fc8a38e', false),
    ('0896abd9-932d-4998-8a83-2a00d9bf77e5', true);

insert into writer (id, writer_membership_status_id, membership_id) values
    ('70249358-0f4d-4010-9361-26c5e533a979', '334567b2-645d-4102-9688-a6d30563b5aa', 'ff5c1778-c150-4ea9-9100-a4c78d9b81a6'),
    ('06743d0d-4376-483d-bb36-334584458345', 'ad201f39-d662-445b-829d-e15e5e035a2e', '53d377d7-506f-4723-89e3-3c51debb182d');

insert into beta_reader (id, penalty_point, reader_id) values
    ('51871e79-679f-4b57-9bce-f53f5fcb7e36', 0, 'f4d9353c-be37-4272-b631-7262e2e2d28b'),
    ('96e0c136-6f86-45c6-af54-46dd36ed6c5c', 0, '16805d59-3133-4ee5-9a42-6fc2578a627a'),
    ('d87974f4-ff25-4e8f-bbe2-98062bf733b6', 0, 'fb70ab80-925d-4b4a-8eab-97728e46fac1');

insert into genre(id, code, genre_name, deleted) values
    ('b10c65ec-3a63-47c5-a87c-500aa71d9427', '565654533', 'thriller', false),
    ('10b2d90b-0cbc-4ec2-b165-6c5c959737c6', '546484978', 'fiction', false),
    ('fb5443bd-3b1a-40cf-a979-d7ad9c45e315', '156895462', 'poetry', false);

insert into beta_reader_genres (beta_reader_id, genres_id) values
    ('51871e79-679f-4b57-9bce-f53f5fcb7e36', 'b10c65ec-3a63-47c5-a87c-500aa71d9427'),
    ('51871e79-679f-4b57-9bce-f53f5fcb7e36', 'fb5443bd-3b1a-40cf-a979-d7ad9c45e315'),
    ('96e0c136-6f86-45c6-af54-46dd36ed6c5c', 'fb5443bd-3b1a-40cf-a979-d7ad9c45e315'),
    ('d87974f4-ff25-4e8f-bbe2-98062bf733b6', '10b2d90b-0cbc-4ec2-b165-6c5c959737c6');

insert into editor_comment (id, comment_text, editor_id) values
    ('cd484a86-fd3e-4bc3-80c8-f81182787769', 'This is awesome!', '2b052fd4-fbc0-462f-8db3-650b3c89e20a'),
    ('72a585bf-c167-4b34-ac5d-b04c7e3e4f59', 'This is great!', 'be18edb8-2bd1-4d77-a0b9-9edf9e66a4d2'),
    ('0b5ccaee-093c-4c4d-a7a6-2be229b65128', 'This is super!', '2b052fd4-fbc0-462f-8db3-650b3c89e20a'),
    ('97ce8657-89ef-45c8-bec7-0d14d82ca8a2', 'This is handsome!', '2b052fd4-fbc0-462f-8db3-650b3c89e20a');

insert into book_request (id, synopsis, title, editor_comment_id, approved) values
    ('e38d8dad-0327-4b6a-ac04-b74a509946a0', 'This is great synopsis', 'The Great Idea', 'cd484a86-fd3e-4bc3-80c8-f81182787769', true),
    ('a166f9bf-7a14-4a29-8ef6-1a995357fd71', 'This is our synopsis', 'Bear & Star', '72a585bf-c167-4b34-ac5d-b04c7e3e4f59', true),
    ('cb5d2ed2-f3fa-45ce-884e-d8cf92008012', 'Synopsis for book', 'Book 12', '0b5ccaee-093c-4c4d-a7a6-2be229b65128', true),
    ('1fbb57f8-8857-4967-a319-de3e4b1b39b0', 'This is our synopsis', 'Us', '97ce8657-89ef-45c8-bec7-0d14d82ca8a2', true);

insert into book_request_genre (book_request_id, genre_id) values
    ('e38d8dad-0327-4b6a-ac04-b74a509946a0', 'b10c65ec-3a63-47c5-a87c-500aa71d9427'),
    ('e38d8dad-0327-4b6a-ac04-b74a509946a0', '10b2d90b-0cbc-4ec2-b165-6c5c959737c6'),
    ('a166f9bf-7a14-4a29-8ef6-1a995357fd71', 'b10c65ec-3a63-47c5-a87c-500aa71d9427'),
    ('cb5d2ed2-f3fa-45ce-884e-d8cf92008012', 'fb5443bd-3b1a-40cf-a979-d7ad9c45e315'),
    ('cb5d2ed2-f3fa-45ce-884e-d8cf92008012', '10b2d90b-0cbc-4ec2-b165-6c5c959737c6'),
    ('1fbb57f8-8857-4967-a319-de3e4b1b39b0', 'fb5443bd-3b1a-40cf-a979-d7ad9c45e315');

insert into book_request_writers (writer_id, book_request_id) values
    ('70249358-0f4d-4010-9361-26c5e533a979', 'e38d8dad-0327-4b6a-ac04-b74a509946a0'),
    ('06743d0d-4376-483d-bb36-334584458345', 'a166f9bf-7a14-4a29-8ef6-1a995357fd71'),
    ('70249358-0f4d-4010-9361-26c5e533a979', 'cb5d2ed2-f3fa-45ce-884e-d8cf92008012'),
    ('06743d0d-4376-483d-bb36-334584458345', '1fbb57f8-8857-4967-a319-de3e4b1b39b0');

insert into handwrite (id, handwrite_file_name, book_request_id) values
    ('e95aaf6e-2ac6-49ac-a486-d9d7923c8e4e', 'C:\Program Files\Handwrites', 'e38d8dad-0327-4b6a-ac04-b74a509946a0'),
    ('92967aac-7bb6-4c44-b722-4592d38ba11f', 'C:\Program Files\Handwrites1', 'a166f9bf-7a14-4a29-8ef6-1a995357fd71'),
    ('223f7bde-19c4-41f4-b6cd-0d8b77cae0e5', 'C:\Program Files\Handwrites2', 'cb5d2ed2-f3fa-45ce-884e-d8cf92008012'),
    ('2a5eab75-04b0-4600-a490-e65ac820ef8d', 'C:\Program Files\Handwrites3', '1fbb57f8-8857-4967-a319-de3e4b1b39b0');

insert into book (id, isbn, publish_year, publish_place, number_of_pages, deleted, book_request_id, recommended_price) values
    ('fc8eeb62-9f4f-42bf-b344-90102672a554', '978-3-16-148410-0', '2012-11-11', 'Futog', 317, false, 'e38d8dad-0327-4b6a-ac04-b74a509946a0', 21.00),
    ('e5cc17c1-a935-4c73-bdce-29300add8645', '475-8-19-456984-1', '2017-12-21', 'Novi Sad', 874, false, 'a166f9bf-7a14-4a29-8ef6-1a995357fd71', 9.00),
    ('01b95e2e-34d2-47ee-8433-0e9e2c989282', '978-3-16-123785-0', '2019-02-11', 'Cerevic', 200, false, 'cb5d2ed2-f3fa-45ce-884e-d8cf92008012', 18.00),
    ('8d14b7a6-d580-4108-a5fc-943e1999377a', '458-9-44-548962-2', '2020-02-12', 'Bajina Basta', 120, false, '1fbb57f8-8857-4967-a319-de3e4b1b39b0', 4.00);