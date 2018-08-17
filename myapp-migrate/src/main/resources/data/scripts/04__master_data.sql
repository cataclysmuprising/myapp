/******      User        ******/
INSERT INTO mjr_user 
(id,content_id, name, 			email, 					password, 															nrc, 					phone, 			status, age, 	gender, dob, 			address ,				record_reg_id ,record_upd_id ,record_reg_date ,record_upd_date ) 
VALUES 
(1, null, 		'Super User', 	'superuser@sample.com', '$2a$10$MBnTBtMggKze8KbDcgtmSuXHc6Ujq7bD9k3hr2RQ39lSa8dnjnwue', 	'12/abc(N)12345678', 	'09-1111111', 	1, 		30, 	0, 		'1988-08-15', 	'Yangon/Myanmar' ,		10009 ,10009 ,'2018-08-15 14:32:38.331' ,'2018-08-15 14:32:38.331' );

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
(1,			1,			1,1,'2018-01-01','2018-01-01');

/******      action        ******/ 
INSERT INTO mjr_action 
(id,	  	page,			action_name,			display_name,			action_type,	url,						description																										,record_reg_id,record_upd_id,record_reg_date,record_upd_date) 
VALUES 
(1001,		'Dashboard',	'dashboard',		   	'Dashboard Page',		0,				'^/dashboard$',				'Control panel page for Sign-in User.'																			,1,1,'2018-01-01','2018-01-01'),
(1002,		'Profile',		'profile',		   		'User Profile Page',	0,				'^/user/profile$',			'User Profile page for current Sign-in User.'																	,1,1,'2018-01-01','2018-01-01'),
/******      User Management        ******/ 
(1003,		'User',			'userList',				'User List Page',		0,				'^/user$',					'Home page to view the all User informations.'																	,1,1,'2018-01-01','2018-01-01'),
(1004,		'User',			'userDetail',			'User detail',			1,				'^/user/[0-9]{1,}',			'To view the detail informations of each users.'																,1,1,'2018-01-01','2018-01-01'),
(1005,		'User',			'userAdd',				'User registeration',	1,				'^/user/add$',				'This action is for to create new User.'																		,1,1,'2018-01-01','2018-01-01'),
(1006,		'User',			'userEdit',				'Edit users',			1,				'^/user/[0-9]{1,}/edit$',	'This action can edit personal informations of specific user (can also edit informations other users).'			,1,1,'2018-01-01','2018-01-01'),
(1007,		'User',			'userRemove',			'Remove users',			1,				'^/user/[0-9]{1,}/delete$',	'To remove a specific user from application.'																	,1,1,'2018-01-01','2018-01-01');

/******      Role Action        ******/ 
INSERT INTO mjr_role_action 
(role_id,  action_id,	record_reg_id,record_upd_id,record_reg_date,record_upd_date) 
VALUES 
/******      Administrator       ******/
(1,			1001,		1,1,'2018-01-01','2018-01-01'),	
(1,			1002,		1,1,'2018-01-01','2018-01-01'),	
(1,			1003,		1,1,'2018-01-01','2018-01-01'),	
(1,			1004,		1,1,'2018-01-01','2018-01-01'),	
(1,			1005,		1,1,'2018-01-01','2018-01-01'),	
(1,			1006,		1,1,'2018-01-01','2018-01-01'),
(1,			1007,		1,1,'2018-01-01','2018-01-01'),	
/******      User       ******/
(2,			1001,		1,1,'2018-01-01','2018-01-01'),
(2,			1002,		1,1,'2018-01-01','2018-01-01');

