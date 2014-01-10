DROP DATABASE IF EXISTS mobile;
create DATABASE mobile;
ALTER DATABASE `mobile` CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `google_map_place` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(1024) DEFAULT NULL,
  `ResidenceID` int(11) DEFAULT NULL,
  `Lng` double DEFAULT NULL,
  `Lat` double DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `google_map_place_base` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ResidenceID` int(11) DEFAULT NULL,
  `Address` varchar(512) DEFAULT NULL,
  `Name` varchar(128) DEFAULT NULL,
  `Lat` varchar(128) DEFAULT NULL,
  `Lng` varchar(128) DEFAULT NULL,
  `Type` varchar(128) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `Search` varchar(128) DEFAULT NULL,
  `IsMain` bit(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ResidenceID` (`ResidenceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `house_follow_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `ClientUID` varchar(1024) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL COMMENT '1:售卖 2：租房',
  `Stauts` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `residence_follow_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ResidenceID` int(11) DEFAULT NULL,
  `ClientUId` varchar(1024) DEFAULT NULL,
  `Stauts` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_chat_message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BrokerID` int(11) DEFAULT NULL,
  `RealBrokerID` int(11) DEFAULT NULL,
  `HouseID` int(11) DEFAULT NULL,
  `ClientUID` varchar(128) DEFAULT NULL,
  `Type` tinyint(4) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Format` tinyint(3) NOT NULL DEFAULT '0',
  `Content` varchar(1024) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `FromTo` tinyint(1) DEFAULT NULL,
  `ClientDelete` tinyint(1) NOT NULL DEFAULT '0',
  `BrokerDelete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `area_position` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CityID` int(11) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL COMMENT '1.城市 2.行政区 3.板块',
  `PositionID` int(11) DEFAULT NULL,
  `Lat` varchar(128) DEFAULT NULL,
  `Lng` varchar(128) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `client_register` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ClientID` varchar(1024) DEFAULT NULL,
  `ClientToken` varchar(1024) DEFAULT NULL,
  `BrokerID` int(11) NOT NULL DEFAULT '-1',
  `BrokerLogin` tinyint(1) NOT NULL DEFAULT '0',
  `LastAPNSSign` varchar(32) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `Version` varchar(16) DEFAULT NULL,
  `Device` varchar(16) DEFAULT NULL,
  `SystemInfo` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_leads` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ContactName` varchar(64) DEFAULT NULL,
  `Mobile` varchar(64) DEFAULT NULL,
  `ResidenceName` varchar(64) DEFAULT NULL,
  `ClientUID` varchar(128) DEFAULT NULL,
  `Address` varchar(128) DEFAULT NULL,
  `Area` varchar(64) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `housemart_feedback` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Content` varchar(1024) DEFAULT NULL,
  `Name` varchar(64) DEFAULT NULL,
  `ClientUID` varchar(128) DEFAULT NULL,
  `Mobile` varchar(64) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `notification` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ResidenceID` int(11) NOT NULL,
  `HouseID` int(11) NOT NULL,
  `ClientUID` varchar(128) NOT NULL,
  `Type` tinyint(3) NOT NULL COMMENT '1: house sale, 2: house rent, 3: residence update',
  `Action` tinyint(3) NOT NULL,
  `ActionTime` datetime NOT NULL,
  `Status` tinyint(3) NOT NULL DEFAULT '0',
  `SendTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `CountStatus` tinyint(1) NOT NULL DEFAULT '0',
  `NotifyTime` datetime DEFAULT NULL,
  `HouseFollow` int(11) NOT NULL,
  `ResidenceFollow` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `client_notification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Content` varchar(1024) NOT NULL,
  `Target` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0: All, 1: Client, 2: Broker, 3: Custom',
  `AddTime` datetime DEFAULT NULL,
  `AccountID` int(11) DEFAULT NULL,
  `SendTime` datetime DEFAULT NULL,
  `ClientUIDs` varchar(4096) DEFAULT NULL,
  `Status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0: unprocessed, 1: processed',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `client_notification_send` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NotificationID` int(11) NOT NULL,
  `ClientUIDs` varchar(4096) DEFAULT NULL,
  `Status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0: unprocessed, 1: processed',
  `SendType` varchar(45) NOT NULL DEFAULT '0' COMMENT '0: APNSClient, 1:APNSBroker, 2: BaiduYun, 3: BaiduYunClient, 4: BaiduYunBroker',
  `ProcessTime` datetime DEFAULT NULL,
  `Note` varchar(4096) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

