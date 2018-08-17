/******      User        ******/
INSERT INTO mjr_user 
(id,content_id, name, 			email, 					password, 															nrc, 					phone, 			status, age, 	gender, dob, 			address ,				record_reg_id ,record_upd_id ,record_reg_date ,record_upd_date ) 
VALUES 
(2, null, 		'Mg Mg', 		'mgmg@sample.com', 		'$2a$10$B/htzvJZ3Yu91kC9nmsrMOhvgIyDZ1aqUesy/n5efuAZY1nNHI92e', 	'12/def(N)87654321', 	'09-2222222', 	1, 		25, 	0, 		'1992-08-15', 	'Mandalay/Myanmar' ,	10009 ,10009 ,'2018-08-15 14:32:38.331' ,'2018-08-15 14:32:38.331' );

/******     User Role       ******/
INSERT INTO mjr_user_role
(user_id, 	role_id, 	record_reg_id,record_upd_id,record_reg_date,record_upd_date)  
VALUES 
/******      Mg Mg     ******/
(2,			2,			1,1,'2018-01-01','2018-01-01');
