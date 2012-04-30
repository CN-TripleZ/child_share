drop database if exists timeshare;
create database timeshare;
create table t_user (
	id varchar(20) primary key,
	password varchar(256),
	name varchar(256),
	email varchar(256)
);
/*
create table t_user_focus(
  id int primary key auto_increment,
  t_user_id varchar(256),
  t_user_focus_id varchar(256),
  isReaded boolean
);
*/
/*
// 后续
t_user_setting(

);
*/
create table t_image(
  id int primary key auto_increment,
  upload_time Date,
  t_user_id varchar(20),
  description varchar(2000),
  img_path varchar(256)
);

create table t_comment(
  id int primary key auto_increment,
  t_image_id int,
  content varchar(256),
  comment_time Date,
  isReaded boolean
);

alter table t_image add constraint fk_user_image foreign key (t_user_id) references t_user(id);
alter table t_comment add constraint fk_image_comment foreign key (t_image_id) references t_comment(id);

/*
// 列表页
//
// PhoneGap拍照，图片上传，文字，时间
*/