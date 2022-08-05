INSERT INTO account(id,email,pword,firstname,lastname) VALUES(1000001,"one@email.com","motdepasse","firstname","lastname");
INSERT INTO account(id,email,pword,firstname,lastname) VALUES(1000002,"two@email.com","motdepasse","firstname","lastname");

INSERT INTO link_user(id,id_account,id_friend) VALUES(1000001,1000001,1000002);
INSERT INTO link_user(id,id_account,id_friend) VALUES(1000002,1000002,1000001);

INSERT INTO payment(id,id_debitor,id_creditor,pay_datetime,amount,company_fee) VALUES(1000001,1000001,1000002,'20200101',5.0,1.0);
INSERT INTO payment(id,id_debitor,id_creditor,pay_datetime,amount,company_fee) VALUES(1000002,1000002,1000001,'20200101',5.0,1.0);
