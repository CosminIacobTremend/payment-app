insert into client("id", "cnp", "email", "first_name", "last_name")
    VALUES (1, 'test_cnp_1', 'test_email_1', 'test_fn_1', 'test_ln_1');
insert into client("id", "cnp", "email", "first_name", "last_name")
    VALUES (2, 'test_cnp_2', 'test_email_2', 'test_fn_2', 'test_ln_2');
insert into client("id", "cnp", "email", "first_name", "last_name")
    VALUES (3, 'test_cnp_3', 'test_email_3', 'test_fn_3', 'test_ln_3');
insert into client("id", "cnp", "email", "first_name", "last_name")
    VALUES (4, 'test_cnp_4', 'test_email_4', 'test_fn_4', 'test_ln_4');
insert into client("id", "cnp", "email", "first_name", "last_name")
    VALUES (5, 'test_cnp_5', 'test_email_5', 'test_fn_5', 'test_ln_5');

SELECT  setval(pg_get_serial_sequence('client', 'id'),
           (SELECT MAX(id) FROM client));

insert into account(iban, client_id) VALUES ('test_iban_1', 1);
insert into account(iban, client_id) VALUES ('test_iban_2', 2);
insert into account(iban, client_id) VALUES ('test_iban_3', 3);
insert into account(iban, client_id) VALUES ('test_iban_4', 4);
insert into account(iban, client_id) VALUES ('test_iban_5', 5);
insert into account(iban, client_id) VALUES ('test_iban_6', 1);
insert into account(iban, client_id) VALUES ('test_iban_7', 2);
insert into account(iban, client_id) VALUES ('test_iban_8', 3);
