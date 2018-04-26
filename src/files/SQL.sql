select * from mfprinters;
INSERT INTO mfprinters (device_name, dealer, location, date, state, toner_cartridge, drum_cartridge,
roller, waste_toner_container, notice) VALUES("Kyocera","Onlinetrade","Koridor","26.04.2018","turn off","108R457345",
"108D457345","108ROL457345","108BUN457345","In my otpusk period");
select * from mfprinters;


Построение базы данных

use erpdb;

CREATE TABLE `erpdb`.`mfprinters` (
  `device_id` INT NOT NULL AUTO_INCREMENT,
  `device_name` VARCHAR(45) NOT NULL,
  `dealer` VARCHAR(45) NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `condition` VARCHAR(20) NOT NULL,
  `toner_cartridge` VARCHAR(45) NOT NULL,
  `drum_cartridge` VARCHAR(45) NOT NULL,
  `roller` VARCHAR(45) NOT NULL,
  `waste_toner_container` VARCHAR(45) NOT NULL,
  `notice` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`device_id`));


CREATE TABLE `erpdb`.`mfprinters_log` (
  `action_id` INT(8) NOT NULL AUTO_INCREMENT,
  `action` VARCHAR(20) NOT NULL,
  `dealer` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `toner_cartridge` VARCHAR(45) NOT NULL,
  `drum_cartridge` VARCHAR(45) NOT NULL,
  `roller` VARCHAR(45) NOT NULL,
  `waste_toner_container` VARCHAR(45) NOT NULL,
  `count` INT(3) NOT NULL,
  `notice` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`action_id`));


  
CREATE TABLE `erpdb`.`mf_spare_parts` (
  `device_id` INT(11) NOT NULL AUTO_INCREMENT,
  `device_name` VARCHAR(45) NOT NULL,
  `dealer` VARCHAR(45) NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `date` DATE NOT NULL,
  `condition` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `notice` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`device_id`));
  


Тестовая таблица

CREATE TABLE `erpdb`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NULL,
  `price` FLOAT NULL,
  `add_date` VARCHAR(20) NULL,
  `image` LONGBLOB NULL,
  PRIMARY KEY (`id`))
COMMENT = 'products';
Изменения в таблице
Переименовать condition в state
ALTER TABLE `erpdb`.`mfprinters` 
CHANGE COLUMN `condition` `state` VARCHAR(20) NOT NULL ;

Убрать NotNull
ALTER TABLE `erpdb`.`mfprinters` 
CHANGE COLUMN `dealer` `dealer` VARCHAR(45) NULL ,
CHANGE COLUMN `location` `location` VARCHAR(45) NULL ,
CHANGE COLUMN `date` `date` DATE NULL ,
CHANGE COLUMN `state` `state` VARCHAR(20) NULL ,
CHANGE COLUMN `toner_cartridge` `toner_cartridge` VARCHAR(45) NULL ,
CHANGE COLUMN `drum_cartridge` `drum_cartridge` VARCHAR(45) NULL ,
CHANGE COLUMN `roller` `roller` VARCHAR(45) NULL ,
CHANGE COLUMN `waste_toner_container` `waste_toner_container` VARCHAR(45) NULL ,
CHANGE COLUMN `notice` `notice` VARCHAR(45) NULL ;

Изменить тип поля date на varchar
ALTER TABLE `erpdb`.`mfprinters` 
CHANGE COLUMN `date` `date` VARCHAR(20) NULL DEFAULT NULL ;


linux mysql Сделать дамп базы в архив -- в архивный файл. Создание дампа

mysqldump -u USER -pPASSWORD DATABASE | gzip > /path/to/outputfile.sql.gz

linux MySQL загрузить дамп базы из архива (.sql.gz файла)

gzip -dc имяфайла.gz | mysql -u <имяпользователя> -p <имябазыданных>
Например:
gzip -dc file.sql.gz | mysql -u root -p mybase

