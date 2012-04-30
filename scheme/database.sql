t_user(
id varchar primary key,
password varchar 256,
name varchar(256),
email varchar(256)
);

t_user_focus(
  id int primary key,
  t_user_id varchar(256),
  t_user_focus_id varchar(256),
  isReaded boolean
);

// 后续
t_user_setting(

);

t_image(
  id int primary key,
  start_time Date,
  description varchar(2000),
  path varchar(256)
);

t_comment(
  id varchar primary key,
  t_user_id int,
  t_image_id int,
  content varchar 256,
  start_time Date,
  isReaded boolean
);


// 列表页
//
// PhoneGap拍照，图片上传，文字，时间
