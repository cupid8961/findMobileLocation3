
DROP TABLE mylocation;
CREATE TABLE mylocation (
  no INT(1) NOT NULL AUTO_INCREMENT,
  phone_num VARCHAR(32) NOT NULL,
  lat FLOAT(1) NOT NULL,
  lon FLOAT(1) NOT NULL,
  m_time VARCHAR(20) NOT NULL,
  b_per INT(1), /* Batter Percent */
  PRIMARY KEY (no)
);

insert into mylocation(phone_num, lat, lon, m_time, b_per) values('010-1111-1111',35.1646501,128.9317139, '12:30', 30);
insert into mylocation(phone_num, lat, lon, m_time, b_per) values('010-2222-2222',37.5652894,126.8494642, '13:40', 15);
insert into mylocation(phone_num, lat, lon, m_time, b_per) values('010-3333-3333',36.3732178,127.3187596, '14:50', 20);
insert into mylocation(phone_num, lat, lon, m_time, b_per) values('010-4444-4444',37.8688626,127.6973305, '16:00', 50);

select * from mylocation;