/******      User        ******/
INSERT INTO mjr_user 
(id,content_id, name, 			email, 					password, 															nrc, 					phone, 			status, age, 	gender, dob, 			address ,				record_reg_id ,record_upd_id ,record_reg_date ,record_upd_date ) 
VALUES 
(1, null, 		'Super User', 	'superuser@sample.com', '$2a$10$MBnTBtMggKze8KbDcgtmSuXHc6Ujq7bD9k3hr2RQ39lSa8dnjnwue', 	'12/abc(N)12345678', 	'09-1111111', 	1, 		30, 	0, 		'1988-08-15', 	'Yangon/Myanmar' ,		10009 ,10009 ,'2018-08-15 14:32:38.331' ,'2018-08-15 14:32:38.331' ),
(2, null, 		'Mg Mg', 		'mgmg@sample.com', 		'$2a$10$B/htzvJZ3Yu91kC9nmsrMOhvgIyDZ1aqUesy/n5efuAZY1nNHI92e', 	'12/def(N)87654321', 	'09-2222222', 	1, 		25, 	0, 		'1992-08-15', 	'Mandalay/Myanmar' ,	10009 ,10009 ,'2018-08-15 14:32:38.331' ,'2018-08-15 14:32:38.331' );

/******      Role        ******/
INSERT INTO mjr_role 
(id,	  name, 			description, 							record_reg_id,record_upd_id,record_reg_date,record_upd_date)
VALUES 
(1,		 'Administrator',	'Default Administrator role',			1,1,'2018-01-01','2018-01-01'),
(2,		 'User',			'Default User role',					1,1,'2018-01-01','2018-01-01');

/******     User Role       ******/
INSERT INTO mjr_user_role
(user_id, 	role_id, 	record_reg_id,record_upd_id,record_reg_date,record_upd_date)  
VALUES 
/******      Super User     ******/
(1,			1,			1,1,'2018-01-01','2018-01-01'),
/******      Mg Mg     ******/
(2,			2,			1,1,'2018-01-01','2018-01-01');

/******      action        ******/ 
INSERT INTO mjr_action 
(id,	  	page,			action_name,			display_name,			action_type,	url,						description												,record_reg_id,record_upd_id,record_reg_date,record_upd_date) 
VALUES 
(1001,		'Dashboard',	'dashboard',		   	'Dashboard Page',		0,				'^/dashboard$',				'Control panel page for Sign-in user'					,1,1,'2018-01-01','2018-01-01'),
/******      User Management        ******/ 
(1002,		'User',			'userList',				'User List Page',		0,				'^/user$',					'Home page to view the all user informations.'			,1,1,'2018-01-01','2018-01-01');

/******      Role Action        ******/ 
INSERT INTO mjr_role_action 
(role_id,  action_id,	record_reg_id,record_upd_id,record_reg_date,record_upd_date) 
VALUES 
/******      Administrator       ******/
(1,			1001,		1,1,'2018-01-01','2018-01-01'),	
(1,			1002,		1,1,'2018-01-01','2018-01-01'),	
/******      User       ******/
(1,			1001,		1,1,'2018-01-01','2018-01-01');