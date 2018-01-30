INSERT INTO user(user_id, password, name, email) VALUES ('boobby', '1234', 'BEH', 'boo@woowahan.com');
INSERT INTO user(user_id, password, name, email) VALUES ('ikkiluk', '1234', 'LSE', 'lse@woowahan.com');
INSERT INTO user(user_id, password, name, email) VALUES ('yejun', '1234', 'PYJ', 'pyj@woowahan.com');
INSERT INTO user(user_id, password, name, email) VALUES ('sehwan', '1234', 'KSH', 'ksh@woowahan.com');
INSERT INTO user(user_id, password, name, email) VALUES ('hanna', '1234', 'JHN', 'jhn@woowahan.com');

INSERT INTO qna(writer_id, title, contents, create_date) VALUES
(1, '이것이 스프링인가요?', '너무 어렵네요', '2017-12-22 13:12:00'),
(2, '끼룩끼룩???', '끼루룩', '2018-01-10 22:00:00'),
(3, '자바스크립트가 쉽나요 자바가 쉽나요?', '하...', '2018-01-25 15:41:00');

INSERT INTO answer(question_id, writer_id, contents, create_date) VALUES
(1, 4, '이것이 스프링이지 무엇이 스프링이겠습니까', '2017-12-24 14:00:11'),
(1, 1, '그렇죠???', '2017-12-24 17:00:22'),
(1, 2, '고럼고럼', '2017-12-28 13:00:11'),
(2, 5, '우끼끼!!!', '2018-01-11 14:00:14'),
(2, 1, '끼이끼이~~!!!', '2018-01-24 16:00:40');