insert into author (birthday, email, fio) values
(timestamp '1859-05-22', 'conandoyle@mail.com', 'Arthur Conan Doyle'),
(timestamp '1947-09-21', 'stephenking@mail.com', 'Stephen Edwin King');

insert into book  (number_of_pages, title, author_id) values
(211, 'The Hound of the Baskervilles', 1),
(20, 'The Five Orange Pips', 1)