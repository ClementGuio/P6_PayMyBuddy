INSERT INTO account(id,email,pword,firstname,lastname) VALUES(1000001,"one@email.com","motdepasse","firstname","lastname");
INSERT INTO account(id,email,pword,firstname,lastname) VALUES(1000002,"two@email.com","motdepasse","firstname","lastname");

INSERT INTO link_user(id,id_account,id_friend) VALUES(1000001,1000001,1000002);
INSERT INTO link_user(id,id_account,id_friend) VALUES(1000002,1000002,1000001);

INSERT INTO transaction(id,id_debitor,id_creditor,tr_datetime,amount) VALUES(1000001,1000001,1000002,'20200101',5.0);
INSERT INTO transaction(id,id_debitor,id_creditor,tr_datetime,amount) VALUES(1000002,1000002,1000001,'20200101',5.0);
