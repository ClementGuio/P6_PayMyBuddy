ALTER TABLE account AUTO_INCREMENT=1000001;

INSERT INTO account(email,pword,balance,firstname,lastname) VALUES("testone@email.com","motdepasse",100.0,"firstname","lastname");
INSERT INTO account(email,pword,balance,firstname,lastname) VALUES("testtwo@email.com","motdepasse",100.0,"firstname","lastname");
INSERT INTO account(email,pword,balance,firstname,lastname) VALUES("testthree@email.com","motdepasse",100.0,"firstname","lastname");

ALTER TABLE link_user AUTO_INCREMENT=1000001;

INSERT INTO link_user(id_account,id_friend) VALUES(1000001,1000002);
INSERT INTO link_user(id_account,id_friend) VALUES(1000002,1000001);
INSERT INTO link_user(id_account,id_friend) VALUES(1000003,1000001);
INSERT INTO link_user(id_account,id_friend) VALUES(1000003,1000002);

ALTER TABLE payment AUTO_INCREMENT=1000001;

INSERT INTO payment(id_debitor,id_creditor,pay_datetime,amount,company_fee) VALUES(1000001,1000002,'20200101',5.0,1.0);
INSERT INTO payment(id_debitor,id_creditor,pay_datetime,amount,company_fee) VALUES(1000002,1000001,'20200101',5.0,1.0);
INSERT INTO payment(id_debitor,id_creditor,pay_datetime,amount,company_fee) VALUES(1000003,1000001,'20200101',5.0,1.0);
INSERT INTO payment(id_debitor,id_creditor,pay_datetime,amount,company_fee) VALUES(1000003,1000002,'20200101',5.0,1.0);

