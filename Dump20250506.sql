-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: controlmas
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `arrowchat_config`
--

DROP TABLE IF EXISTS `arrowchat_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `arrowchat_config` (
  `config_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `is_dynamic` tinyint unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `config_name` (`config_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arrowchat_config`
--

LOCK TABLES `arrowchat_config` WRITE;
/*!40000 ALTER TABLE `arrowchat_config` DISABLE KEYS */;
INSERT INTO `arrowchat_config` VALUES ('admin_background_color','',0),('admin_chat_all','0',0),('admin_text_color','',0),('admin_view_maintenance','0',0),('announcement','<p>Se notifica que a partir de las <strong>17:00</strong> del dia de hoy (sabado) hasta las <strong>03:00</strong> del dia lunes no se podra acceder a Control +.</p>\r\n<p>Esto debido a tareas de mantenimiento y cambios en su codigo fuente, Lamentamos las molestias causadas.</p>\r\n<p style=\"text-align: center;\"><img src=\"https://welcome.wf.com/construircredito/images/light_bulb_icon.png\" alt=\"\" width=\"230\" height=\"232\" /></p>',0),('applications_guests','1',0),('applications_on','1',0),('auto_popup_chatbox','1',0),('bar_fixed','0',0),('bar_fixed_alignment','center',0),('bar_fixed_width','900',0),('bar_padding','15',0),('base_url','/control/arrowchat/',0),('blocked_words','culo,mierda,marica,perra,hp',0),('buddy_list_heart_beat','60',0),('chat_maintenance','0',0),('chatroom_auto_join','0',0),('chatroom_default_names','0',0),('chatroom_history_length','60',0),('chatroom_message_length','150',0),('chatroom_transfer_on','0',0),('chatrooms_on','1',0),('desktop_notifications','1',0),('disable_arrowchat','0',0),('disable_avatars','0',0),('disable_buddy_list','1',0),('disable_smilies','0',0),('enable_chat_animations','1',0),('enable_mobile','0',0),('enable_moderation','0',0),('facebook_app_id','',0),('file_transfer_on','1',0),('giphy_chatroom_off','0',0),('giphy_off','1',0),('group_disable_apps','',0),('group_disable_arrowchat','',0),('group_disable_rooms','',0),('group_disable_sending_private','',0),('group_disable_sending_rooms','',0),('group_disable_uploads','',0),('group_disable_video','',0),('group_enable_mode','0',0),('guest_name_bad_words','culo,mierda,marica,perra,hp',0),('guest_name_change','1',0),('guest_name_duplicates','0',0),('guests_can_chat','0',0),('guests_can_view','0',0),('guests_chat_with','1',0),('heart_beat','3',0),('hide_admins_buddylist','0',0),('hide_applications_menu','0',0),('hide_bar_on','1',0),('idle_time','3',0),('install_time','1533967431',0),('language','en',0),('login_url','',0),('max_upload_size','5',0),('notifications_on','1',0),('online_list_on','1',0),('online_timeout','220',0),('popout_chat_on','1',0),('push_on','0',0),('push_publish','',0),('push_ssl','0',0),('push_subscribe','',0),('search_number','0',0),('show_bar_links_right','0',0),('show_full_username','0',0),('theme','new_facebook_full',0),('theme_change_on','0',0),('tokbox_api','',0),('tokbox_secret','',0),('us_time','1',0),('user_chatrooms','0',0),('user_chatrooms_flood','10',0),('user_chatrooms_length','30',0),('users_chat_with','3',0),('video_chat','1',0),('video_chat_height','600',0),('video_chat_selection','1',0),('video_chat_width','900',0),('width_applications','16',0),('width_buddy_list','189',0),('width_chatrooms','16',0),('window_top_padding','70',0);
/*!40000 ALTER TABLE `arrowchat_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_CARTERA'),('ROLE_REDES'),('ROLE_SECRETARIA'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (53,'ROLE_SECRETARIA'),(61,'ROLE_REDES'),(64,'ROLE_SECRETARIA'),(98,'ROLE_SECRETARIA'),(202,'ROLE_ADMIN'),(245,'ROLE_REDES'),(258,'ROLE_REDES'),(276,'ROLE_REDES'),(277,'ROLE_CARTERA'),(299,'ROLE_SECRETARIA'),(319,'ROLE_REDES'),(323,'ROLE_CARTERA'),(342,'ROLE_CARTERA'),(366,'ROLE_ADMIN'),(368,'ROLE_SECRETARIA'),(393,'ROLE_SECRETARIA'),(399,'ROLE_SECRETARIA'),(417,'ROLE_ADMIN'),(418,'ROLE_REDES'),(439,'ROLE_SECRETARIA'),(440,'ROLE_CARTERA'),(456,'ROLE_REDES'),(490,'ROLE_SECRETARIA'),(491,'ROLE_SECRETARIA'),(494,'ROLE_ADMIN'),(502,'ROLE_ADMIN');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhipster_menu`
--

DROP TABLE IF EXISTS `jhipster_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhipster_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL,
  `icon` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL,
  `url` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL,
  `parent_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhipster_menu`
--

LOCK TABLES `jhipster_menu` WRITE;
/*!40000 ALTER TABLE `jhipster_menu` DISABLE KEYS */;
INSERT INTO `jhipster_menu` VALUES (1,'Clientes','ri-shield-user-line','',0),(2,'Facturación','ri-bill-line','',0),(3,'Inventario','ri-stack-line','',0),(4,'Estaciones','ri-base-station-line','',0),(5,'Winbox','ri-stack-line','',0),(6,'Reportes','ri-file-text-fill','',0),(7,'Administración','ri-mac-line','',0),(8,'Buscar','ri-search-eye-line','clientes/buscar',1),(9,'Campaña correo','ri-mail-send-line','facturacion/mailsend',2),(10,'Articulo','ri-device-line','articulo',3),(11,'Ajustes','ri-tools-line','articulo/ajustes',3),(12,'Compras','ri-store-line','inventarioCompra',3),(13,'Lista','ri-list-ordered','estacion/list',4),(14,'Instalación','ri-tools-line','winbox/list',5),(15,'Mikrotik','ri-tools-line','mikrotik/inicio',5),(16,'reconexiones','ri-loader-4-line','reconexion/list',5),(17,'cortes','ri-scissors-cut-line','cortes/list',5),(18,'Reusos','ri-pantone-line','winbox/padrehijos',5),(19,'Ajustes','ri-settings-2-fill','',0),(20,'Authority','ri-admin-line','configuracion/roles',19),(21,'Cartera Activa','ri-file-text-fill','reportes/cartera-activa',6),(22,'Cambios Plan','ri-cpu-line','admin/cambios-plan',7),(23,'Ordenes con visitas','ri-file-text-fill','reportes/ordenes-visita-fallidas',6),(24,'Tickets','ri-file-text-fill','reportes/soporte-tickets',6),(25,'Seguimiento retiros','ri-file-text-fill','reportes/seguimiento-retiros',6),(26,'Usuarios','pi pi-users','configuracion/usuarios',19),(27,'Portal Web','pi pi-server','portalweb/find',7),(28,'Medio de pagos','ri-file-text-fill','reportes/medios-pago',6),(29,'Pagos Online','ri-file-text-fill','reportes/paymentOnline',6),(30,'SIUST','ri-file-text-fill','reportes/siust',6);
/*!40000 ALTER TABLE `jhipster_menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-06  9:50:37
