/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80014
 Source Host           : localhost:3306
 Source Schema         : injian

 Target Server Type    : MySQL
 Target Server Version : 80014
 File Encoding         : 65001

 Date: 02/04/2019 21:16:33
*/
create database injian;
use injian;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id自增',
  `content` varchar(10000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `from_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '发布评论用户id',
  `to_user_id` int(255) NOT NULL DEFAULT 0 COMMENT '回复用户id',
  `create_time` datetime(0) NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '发表评论时间',
  `item_id` int(11) NOT NULL DEFAULT 0 COMMENT '评论商品id',
  `parent_id` int(11) NOT NULL COMMENT '评论根id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `from_user_id`(`from_user_id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '镜子很好看', 6, 0, '2019-05-04 00:00:00', 35, 0);
INSERT INTO `comment` VALUES (2, '我觉得不是很好看', 7, 6, '2019-05-05 00:00:00', 35, 1);
INSERT INTO `comment` VALUES (3, '你的眼光不太行', 6, 7, '2019-05-08 00:00:00', 35, 1);
INSERT INTO `comment` VALUES (4, '很白搭，很适合自己DIY家居', 7, 0, '2019-05-06 03:00:00', 35, 0);
INSERT INTO `comment` VALUES (5, '我很喜欢', 6, 7, '2019-05-13 00:00:00', 35, 4);
INSERT INTO `comment` VALUES (6, '性价比很高', 6, 0, '2019-05-17 21:38:33', 35, 0);
INSERT INTO `comment` VALUES (8, '不错，很划算', 6, 0, '2019-05-25 11:50:58', 5, 0);
INSERT INTO `comment` VALUES (10, '很好看的椅子', 6, 0, '2019-05-25 12:11:10', 4, 0);
INSERT INTO `comment` VALUES (11, '这个椅子很棒很时尚', 6, 0, '2019-05-25 12:11:40', 3, 0);
INSERT INTO `comment` VALUES (12, '很便宜性价比很高', 6, 0, '2019-05-25 12:12:54', 4, 0);
INSERT INTO `comment` VALUES (13, '不错，这个商品性价比挺高', 6, 0, '2019-05-26 01:54:01', 5, 0);
INSERT INTO `comment` VALUES (14, '哈哈哈', 6, 6, '2019-05-26 12:58:25', 2, 1);
INSERT INTO `comment` VALUES (15, '很舒服呀我觉得舒服呀', 6, 6, '2019-05-26 13:04:21', 2, 4);
INSERT INTO `comment` VALUES (16, '哈哈哈哈', 6, 7, '2019-05-26 13:04:52', 2, 4);
INSERT INTO `comment` VALUES (17, '你是不是故意黑这一家的人家明明是桌子', 6, 6, '2019-05-26 13:05:42', 2, 9);
INSERT INTO `comment` VALUES (18, '实惠美观很漂亮', 6, 0, '2019-05-26 13:06:44', 2, 0);
INSERT INTO `comment` VALUES (19, '很实用我很喜欢', 6, 0, '2019-05-26 13:07:30', 72, 0);
INSERT INTO `comment` VALUES (20, '亲这个容易生锈不？', 6, 6, '2019-05-26 13:08:06', 72, 19);
INSERT INTO `comment` VALUES (21, '请问你是用在什么地方了呢？', 18, 7, '2019-05-27 05:13:45', 35, 4);
INSERT INTO `comment` VALUES (22, '趁着搞活动的时候买的真的很不错！', 18, 0, '2019-05-27 05:21:41', 35, 0);
INSERT INTO `comment` VALUES (23, '请问卖的不是灯吗？', 19, 6, '2019-05-27 12:18:50', 35, 1);
INSERT INTO `comment` VALUES (24, '很棒，性价比很高', 19, 0, '2019-05-27 12:29:20', 92, 0);

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '商品名称',
  `price` double(10, 0) NOT NULL DEFAULT 0 COMMENT '商品价格',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '商品描述',
  `sales` int(11) NOT NULL DEFAULT 0 COMMENT '销量',
  `img_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '商品图片路径',
  `category_id` int(11) NOT NULL DEFAULT 0 COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 138 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, '全实木地中海移门衣橱', 3600, '推拉门组装简约衣橱两门儿童储物柜大空间', 600, 'img/upload/dingzhiyichu1.JPG', 21);
INSERT INTO `item` VALUES (2, '全实木衣柜移门美式推拉门衣橱', 4000, '胡桃木大空间储物柜简约大气卧衣橱', 209, 'img/upload/dingzhiyichu2.JPG', 21);
INSERT INTO `item` VALUES (3, '全实木地中海美式衣柜', 3800, '四门衣橱大空间平开储物柜结婚衣柜简约大气', 303, 'img/upload/dingzhiyichu3.JPG', 21);
INSERT INTO `item` VALUES (4, '定制现代整体橱柜', 2200, '开放式厨房柜定做木纹一字形组装设计全屋定制', 207, 'img/upload/dingzhichugui1.JPG', 22);
INSERT INTO `item` VALUES (5, '高级灰色整体橱柜', 2000, '定做现代简约L小户型厨房柜全屋定制', 223, 'img/upload/dingzhichugui2.JPG', 22);
INSERT INTO `item` VALUES (6, '现代超光钢化镜面整体橱柜', 2000, '定做彩晶开放式厨房柜全屋家具定制', 209, 'img/upload/dingzhichugui3.JPG', 22);
INSERT INTO `item` VALUES (7, '定制踏踏米', 1299, '现代简约小卧室榻榻米床整体定做', 560, 'img/upload/dingzhitatami1.JPG', 23);
INSERT INTO `item` VALUES (8, '日式榻榻米', 1000, '多功能小户型订做现代简约飘窗整体定制', 211, 'img/upload/dingzhitatami2.png', 23);
INSERT INTO `item` VALUES (9, '榻榻米整体定制', 1200, '北欧一体升降桌现代简约整体定制', 209, 'img/upload/dingzhitatami3.JPG', 23);
INSERT INTO `item` VALUES (10, '平开门整体衣帽间', 5000, '定做北欧开放式衣橱全屋家具定制', 678, 'img/upload/dingzhiyimaojian1.JPG', 24);
INSERT INTO `item` VALUES (11, '风木纹整体衣帽间', 5000, '定制走入式高端衣橱全屋装修设计定做', 532, 'img/upload/dingzhiyimaojian2.JPG', 24);
INSERT INTO `item` VALUES (12, '开放式整体衣帽间', 4500, '现代定制开放式平开玻璃门卧室衣橱衣帽间定做 ', 304, 'img/upload/dingzhiyimaojian3.JPG', 24);
INSERT INTO `item` VALUES (13, '北欧电视柜', 2200, '现代简约小户型客厅黑色地柜胡桃木质定制', 212, 'img/upload/dingzhidianshigui1.png', 25);
INSERT INTO `item` VALUES (14, '简约电视柜', 2010, '现代简约客厅家具组合茶几电视机柜', 667, 'img/upload/dingzhidianshigui2.png', 25);
INSERT INTO `item` VALUES (15, '现代电视柜', 2350, '小户型轻奢工业风主卧电视机柜储物柜地柜', 765, 'img/upload/dingzhidianshigui13.png', 25);
INSERT INTO `item` VALUES (16, '欧式酒柜', 3200, '多功能餐边柜吊柜橱柜餐厅北欧柜子收纳定制', 210, 'img/upload/dingzhijiugui1.png', 26);
INSERT INTO `item` VALUES (17, '定制玄关欧式酒柜', 2686, '定制客厅玄关柜欧式酒柜餐厅隔断餐边柜碗橱柜展示柜', 602, 'img/upload/dingzhijiugui2.png', 26);
INSERT INTO `item` VALUES (18, '现代简约酒柜', 2636, '欧式酒柜餐厅隔断餐边柜碗橱柜展示柜', 455, 'img/upload/dingzhijiugui3.png', 26);
INSERT INTO `item` VALUES (19, '客厅吊灯', 1738, '现代简约led环形复式楼客厅吊灯', 208, 'img/upload/diaodeng1.png', 31);
INSERT INTO `item` VALUES (20, '家用客厅吊灯', 1568, '简约现代大气北欧后现代浪漫艺术led吊灯', 212, 'img/upload/diaodeng2.png', 31);
INSERT INTO `item` VALUES (21, '北欧餐厅吊灯', 228, '简约现代创意个性艺术吊灯', 305, 'img/upload/diaodeng3.png', 31);
INSERT INTO `item` VALUES (22, '主卧室吸顶灯', 368, 'led简约现代房间灯饰创意个性温馨浪漫家用灯具', 208, 'img/upload/xidingdeng1.png', 32);
INSERT INTO `item` VALUES (23, '爱心儿童房灯', 528, '创意温馨浪漫led吸顶灯具', 344, 'img/upload/xidingdeng2.png', 32);
INSERT INTO `item` VALUES (24, '简约现代吸顶灯', 987, '北欧家用卧室客厅灯2019新款', 302, 'img/upload/xidingdeng3.png', 32);
INSERT INTO `item` VALUES (25, '小夜灯', 69, '北欧ins风少女心温馨灯饰网红台灯', 303, 'img/upload/taideng1.png', 33);
INSERT INTO `item` VALUES (26, '欧式台灯', 110, '时尚创意客厅书房灯艺术灯饰', 312, 'img/upload/taideng2.png', 33);
INSERT INTO `item` VALUES (27, '卧室台灯', 220, '家用简约创意个性复古全铜灯具', 305, 'img/upload/taideng3.png', 33);
INSERT INTO `item` VALUES (28, '实木落地灯', 345, '美式茶几简约现代立式台灯', 311, 'img/upload/luodideng1.png', 34);
INSERT INTO `item` VALUES (29, '网红落地灯', 247, '简约现代轻奢ins风床头立式台灯', 543, 'img/upload/luodideng2.png', 34);
INSERT INTO `item` VALUES (30, '北欧落地灯', 334, '创意个性卧室ins钓鱼灯', 308, 'img/upload/luodideng3.png', 34);
INSERT INTO `item` VALUES (31, '美式壁灯', 136, '户外大门口防水阳台室外欧式复古墙壁别墅庭院灯', 674, 'img/upload/bideng1.png', 35);
INSERT INTO `item` VALUES (32, '防水壁灯', 112, '美式壁灯led简约户外大门口别墅庭院灯', 310, 'img/upload/bideng2.png', 35);
INSERT INTO `item` VALUES (33, '户外壁灯', 89, '防水过道楼梯门口灯阳台室外庭院花园墙壁灯创意简约', 347, 'img/upload/bideng3.png', 35);
INSERT INTO `item` VALUES (34, '卫生间浴室镜柜灯', 109, '化妆灯现代简约家用北欧防水防雾镜灯', 246, 'img/upload/jingqiandeng1.png', 36);
INSERT INTO `item` VALUES (35, '北欧镜前灯', 128, '洗手间镜柜灯饰防水防雾灯简约现代卫生间灯具', 661, 'img/upload/jingqiandeng2.png', 36);
INSERT INTO `item` VALUES (36, 'led镜前灯', 158, '简约现代浴室洗手间镜柜灯饰', 314, 'img/upload/jingqiandeng3.png', 36);
INSERT INTO `item` VALUES (37, '全遮光窗帘', 99, '加厚客厅卧室飘窗亚麻落地窗帘布定制', 265, 'img/upload/chuanglianchuangman1.png', 81);
INSERT INTO `item` VALUES (38, '遮光窗帘', 84, '卧室免打孔窗帘成品简约现代简易魔术贴粘贴窗帘布', 546, 'img/upload/chuanglianchuangman2.png', 81);
INSERT INTO `item` VALUES (39, '纯色棉麻风窗帘', 106, '北欧风现代简约定制窗帘成品遮光', 506, 'img/upload/chuanglianchuangman3.png', 81);
INSERT INTO `item` VALUES (40, '羊毛手工编织地毯', 2999, '美式家居 客厅家用书房地毯', 765, 'img/upload/ditandidian1.png', 82);
INSERT INTO `item` VALUES (41, '美式羊毛枪刺地毯', 4990, '家用客厅卧室沙发茶几垫', 456, 'img/upload/ditandidian2.png', 82);
INSERT INTO `item` VALUES (42, '印度羊毛手编地毯', 3292, '家用客厅卧室', 478, 'img/upload/ditandidian3.png', 82);
INSERT INTO `item` VALUES (43, '懒人坐垫', 211, '日式蒲团坐垫地上榻榻米加厚坐墩飘窗垫子', 304, 'img/upload/zuodianbaozhen1.png', 83);
INSERT INTO `item` VALUES (44, '可爱抱枕', 43, '家居实用毛绒玩具椅子坐垫礼物居家', 323, 'img/upload/zuodianbaozhen2.png', 83);
INSERT INTO `item` VALUES (45, '摩洛哥坐垫', 443, '进口手工曼达拉绣花羊皮墩蒲团坐垫', 111, 'img/upload/zuodianbaozhen3.png', 83);
INSERT INTO `item` VALUES (46, '夏季床上四件套', 128, '全棉纯棉床品套件床单裸睡亲肤斜纹布被罩三件套', 231, 'img/upload/chuangpintaojian1.png', 84);
INSERT INTO `item` VALUES (47, '全棉四件套', 134, '北欧风纯棉网红款床单被套三件套床上用品', 142, 'img/upload/chuangpintaojian2.png', 84);
INSERT INTO `item` VALUES (48, '长绒棉床品套件', 345, '家用少女可爱四件套床上用品', 307, 'img/upload/chuangpintaojian3.png', 84);
INSERT INTO `item` VALUES (49, '凉席', 89, '1.8m床竹席竹凉席子夏季双面折叠', 308, 'img/upload/liangxi1.png', 85);
INSERT INTO `item` VALUES (50, '新中式客厅装饰画', 412, '沙发背景壁画卧室床头挂画古风李知弥', 309, 'img/upload/zhuangshizihua1.png', 86);
INSERT INTO `item` VALUES (51, '新中式玄关装饰画', 339, '椭圆禅意挂画仕女图壁画心经古风字画', 310, 'img/upload/zhuangshizihua2.png', 86);
INSERT INTO `item` VALUES (52, '墙上装饰壁挂挂件', 2088, '铁艺壁饰创意背景墙装饰品客厅大厅墙面', 311, 'img/upload/bishitieshi1.png', 87);
INSERT INTO `item` VALUES (53, '壁饰创意背景墙装饰品', 1280, '中式金属铁艺墙上装饰壁挂挂件客厅大厅墙面', 312, 'img/upload/bishitieshi2.png', 87);
INSERT INTO `item` VALUES (54, '装饰花墙饰立体挂件', 699, '新中式客厅墙上干花墙壁挂花', 313, 'img/upload/bishitieshi3.png', 87);
INSERT INTO `item` VALUES (55, '酒架摆件', 199, '创意奢华葡萄酒架家用酒柜样板间装饰品', 314, 'img/upload/hongjiujia1.png', 88);
INSERT INTO `item` VALUES (56, '胡桃木纸巾盒', 56, '多功能客厅茶几遥控器抽纸盒实木中式纸抽盒', 315, 'img/upload/zhijinhe1.png', 89);
INSERT INTO `item` VALUES (57, '木质纸巾盒', 23, '创意家用餐巾纸盒客厅茶几车简约纸抽盒', 316, 'img/upload/zhijinhe2.png', 89);
INSERT INTO `item` VALUES (58, '日式挂钟', 238, '现代简约时钟北欧宜家客厅家用挂表', 317, 'img/upload/zhongbiao1.png', 810);
INSERT INTO `item` VALUES (59, '日式石英钟', 213, '实木挂钟客厅静音时钟北欧时尚现代榉木钟表', 318, 'img/upload/zhongbiao2.png', 810);
INSERT INTO `item` VALUES (60, '北欧艺术装饰时钟表', 288, '美式复古静音挂钟', 319, 'img/upload/zhongbiao3.png', 810);
INSERT INTO `item` VALUES (61, '烛台摆件', 26, '烛光晚餐道具灯北欧铁艺蜡烛台家居装饰品', 320, 'img/upload/zhutailazhu1.png', 811);
INSERT INTO `item` VALUES (62, '铁艺烛台摆件', 34, '家用餐桌创意简约香薰蜡烛托台杯', 321, 'img/upload/zhutailazhu2.png', 811);
INSERT INTO `item` VALUES (63, '复古烛台', 27, '民族人偶小蜡烛台家用卧室浪漫蜡烛托台', 322, 'img/upload/zhutailazhu3.png', 811);
INSERT INTO `item` VALUES (64, '厨房置物架', 269, '免打孔菜板架砧板架案板用品刀架收纳架子锅盖架壁挂式', 323, 'img/upload/chuyongshouna1.png', 812);
INSERT INTO `item` VALUES (65, '水龙头沥水置物架', 30, '水池收纳家用厨房用品水槽神器抹布沥水架', 324, 'img/upload/chuyongshouna2.png', 812);
INSERT INTO `item` VALUES (66, '水池收纳', 344, '家用厨房用品水槽神器抹布沥水架', 325, 'img/upload/chuyongshouna3.png', 812);
INSERT INTO `item` VALUES (67, '家用玻璃杯', 21, '炫彩离子镀威士忌酒杯透明水杯饮料果汁杯', 945, 'img/upload/beiju1.png', 813);
INSERT INTO `item` VALUES (68, '六棱威士忌酒杯', 22, '描金家用洋酒杯鸡尾酒水晶玻璃杯', 327, 'img/upload/beiju2.png', 813);
INSERT INTO `item` VALUES (69, '高档玻璃杯', 21, '琥珀色锤目纹喝茶水杯果汁饮料牛奶透明杯', 328, 'img/upload/beiju3.png', 813);
INSERT INTO `item` VALUES (70, '中式筷子', 79, '家用餐具红木筷子环保无漆无蜡圆头筷伴手礼物2双装', 329, 'img/upload/kuaishaodaocha1.png', 814);
INSERT INTO `item` VALUES (71, '筷子套装', 34, '学生便携式304不锈钢餐具三件套家用', 330, 'img/upload/kuaishaodaocha2.png', 814);
INSERT INTO `item` VALUES (72, '不锈钢刀叉套装', 100, '西餐牛排刀叉家用网红叉子勺子筷子餐具三件套', 331, 'img/upload/kuaishaodaocha3.png', 814);
INSERT INTO `item` VALUES (73, '汤锅', 119, '锈钢火锅28cm家用电磁炉锅煲汤锅煮锅面条锅不锈钢锅', 332, 'img/upload/pengrenguoju1.png', 815);
INSERT INTO `item` VALUES (74, '汤蒸锅', 213, '28cm汤锅汤蒸两用火锅加厚单蓖蒸锅礼品锅', 333, 'img/upload/pengrenguoju2.png', 815);
INSERT INTO `item` VALUES (75, '家用加厚不锈钢锅', 178, '复底燃气电磁炉通用煲汤锅大容量', 334, 'img/upload/pengrenguoju3.png', 815);
INSERT INTO `item` VALUES (76, '实木切菜板', 51, '加厚整竹家用案板菜板擀面板切水果砧板', 335, 'img/upload/daojiananban1.png', 816);
INSERT INTO `item` VALUES (77, '实木浴室柜', 2099, '北欧组合现代简约卫生间洗漱台洗脸洗手台盆柜卫浴', 336, 'img/upload/yushigui1.png', 91);
INSERT INTO `item` VALUES (78, '浴室柜组合', 2899, '实木现代简约双盆智能镜卫生间洗漱台洗脸洗手盆池', 337, 'img/upload/yushigui2.png', 91);
INSERT INTO `item` VALUES (79, '实木浴室柜组合', 2499, '现代简约双盆智能镜卫生间洗漱台洗脸洗手盆池', 338, 'img/upload/yushigui3.png', 91);
INSERT INTO `item` VALUES (80, '智能马桶坐便器', 5464, '带水箱一体式液晶显示酒店度假村旗舰款', 339, 'img/upload/matong1.png', 92);
INSERT INTO `item` VALUES (81, '节水马桶', 2064, '家用普通成人坐便器洁具静音防溅水卫生坐厕', 340, 'img/upload/matong2.png', 92);
INSERT INTO `item` VALUES (82, '洗脸盆水龙头', 432, '冷热洗手盆面盆家用卫生间龙头', 341, 'img/upload/weiyulongtou1.png', 93);
INSERT INTO `item` VALUES (83, '毛巾架', 65, '黑色免打孔浴巾架太空铝卫生间置物架卫浴浴室挂件', 342, 'img/upload/weiyuguajian1.png', 94);
INSERT INTO `item` VALUES (84, '免打孔毛巾架', 238, '太空铝卫生间浴室置物架浴巾架卫浴五金挂件套装', 343, 'img/upload/weiyuguajian2.png', 94);
INSERT INTO `item` VALUES (85, '家用淋浴缸', 2000, '成人卫生间浴盆亚克力日式独立式浴缸小户型泡澡缸', 344, 'img/upload/yugang1.png', 95);
INSERT INTO `item` VALUES (86, '小户型浴盆', 2880, '家用浴缸成人简约卫生间黑红白色双枕', 345, 'img/upload/yugang2.png', 95);
INSERT INTO `item` VALUES (87, '浴缸金银黑五件套', 1987, '家用彩色薄边独立式小户型卫生间', 346, 'img/upload/yugang3.png', 95);
INSERT INTO `item` VALUES (88, '番龙眼实木地板', 157, '原木锁扣耐地暖地热 灰色家用耐磨', 347, 'img/upload/diban1.png', 96);
INSERT INTO `item` VALUES (89, '橡木纯实木地板', 275, '仿古18mm 锁扣地热地暖原木灰色家用环保', 348, 'img/upload/diban2.png', 96);
INSERT INTO `item` VALUES (90, '柚木纽顿豆纯实木地板', 211, '原木卧室地热地暖锁扣免龙骨', 349, 'img/upload/diban3.png', 96);
INSERT INTO `item` VALUES (91, '加厚防水墙纸', 27, '卧室温馨客厅背景墙贴3d立体大学生自贴壁纸10米', 350, 'img/upload/qiangzhi1.png', 97);
INSERT INTO `item` VALUES (92, '客厅装饰画', 156, '沙发背景墙现代简约墙面组合挂画北欧风格墙画黑白壁画', 1068, 'img/upload/bihua1.png', 98);
INSERT INTO `item` VALUES (93, '装饰壁画', 266, '现代简约沙发背景墙大气绿植壁画餐厅北欧风格组合挂画', 352, 'img/upload/bihua2.png', 98);
INSERT INTO `item` VALUES (94, '北欧装饰画', 265, '组合墙画客厅挂画沙发背景墙现代简约餐厅墙面装饰壁画', 353, 'img/upload/bihua3.png', 98);
INSERT INTO `item` VALUES (95, '瓷砖电视背景墙', 3332, '微晶石欧式石材造型护墙板仿大理石罗马柱云径', 945, 'img/upload/cizhuan1.png', 99);
INSERT INTO `item` VALUES (96, '背景墙瓷砖', 3444, '微晶石现代简约客厅影视墙装饰边框轻奢羽毛', 355, 'img/upload/cizhuan2.png', 99);
INSERT INTO `item` VALUES (97, '电视背景墙瓷砖', 2800, '现代简约护墙板大理石材边框造型影视墙爵白', 356, 'img/upload/cizhuan3.png', 99);
INSERT INTO `item` VALUES (98, '美式吊顶', 1534, '客厅餐厅卧室适用，欧式大气，现代简约', 357, 'img/upload/diaoding1.png', 910);
INSERT INTO `item` VALUES (99, '开关插座面板', 165, '五孔插座套餐二三插墙壁电源10A家用86型', 358, 'img/upload/kaiguanchazuo1.png', 911);
INSERT INTO `item` VALUES (100, '静音房门锁', 139, '室内卧室黑色卫生间实木门把手家用通用型锁具', 359, 'img/upload/suoju1.png', 912);
INSERT INTO `item` VALUES (101, '布艺沙发组合', 5110, '简约现代大小户型客厅整装乳胶多功能可拆洗皮布沙发', 360, 'img/upload/shafa1.png', 161);
INSERT INTO `item` VALUES (102, '头层真皮沙发', 8190, '客厅整装L型转角组合黄牛皮质沙发', 361, 'img/upload/shafa2.png', 161);
INSERT INTO `item` VALUES (103, '北欧真皮沙发', 4000, '小户型客厅现代简约整装三人位沙发组合', 362, 'img/upload/shafa3.png', 161);
INSERT INTO `item` VALUES (104, '钢化玻璃茶几', 899, '客厅大理石迷你创意圆形茶桌现代简约', 363, 'img/upload/chaji1.png', 162);
INSERT INTO `item` VALUES (105, '铁艺大理石茶几', 677, '钢化玻璃客厅迷你简易茶桌简约现代', 364, 'img/upload/chaji2.png', 162);
INSERT INTO `item` VALUES (106, '简约现代玻璃茶几', 713, '客厅茶几小户型创意茶几电视柜组合桌子', 365, 'img/upload/chaji3.png', 162);
INSERT INTO `item` VALUES (107, '休闲餐桌', 733, '家用北欧圆桌铁艺金色餐桌椅子', 366, 'img/upload/canzhuocanyi1.png', 163);
INSERT INTO `item` VALUES (108, '可伸缩折叠长饭桌', 3543, '现代简约小户型火烧石餐桌椅组合', 367, 'img/upload/canzhuocanyi2.png', 163);
INSERT INTO `item` VALUES (109, '可伸缩实木饭桌', 4278, '可折叠小户型火烧石桌椅', 368, 'img/upload/canzhuocanyi3.png', 163);
INSERT INTO `item` VALUES (110, '家用学生写字桌', 864, '简约台式电脑桌经济型简易写字台办公桌', 369, 'img/upload/shuzhuoshuyi1.png', 164);
INSERT INTO `item` VALUES (111, '实木书桌书架组合', 1299, '简约现代家用学生写字台卧室书柜一体台式电脑桌', 370, 'img/upload/shuzhuoshuyi2.png', 164);
INSERT INTO `item` VALUES (112, '电脑桌', 788, '家用简约现代钢化玻璃办公桌简易学习书桌写字台', 371, 'img/upload/shuzhuoshuyi3.png', 164);
INSERT INTO `item` VALUES (113, '书柜书架落地自由组合', 657, '书橱简约现代儿童储物柜置物架转角收纳柜子', 372, 'img/upload/shuguishujia1.png', 165);
INSERT INTO `item` VALUES (114, '纯实木原木色儿童书架', 1200, '卧室现代简约落地置物架书柜收纳省空间', 373, 'img/upload/shuguishujia2.png', 165);
INSERT INTO `item` VALUES (115, '组合书架', 565, '简约现代桌上办公落地简易收纳省空间儿童学生书柜置物架', 374, 'img/upload/shuguishujia3.png', 165);
INSERT INTO `item` VALUES (116, '实木床', 7289, '卧室家具，美国黑胡桃零甲醛', 375, 'img/upload/chuang1.png', 166);
INSERT INTO `item` VALUES (117, '双人宫廷床', 6577, '美式卧室家具实木脚真皮艺软包靠背1.8m', 376, 'img/upload/chuang2.png', 166);
INSERT INTO `item` VALUES (118, '黑胡桃木实木床', 12000, '全双人床1.8米主卧室家具简约真皮', 377, 'img/upload/chuang3.png', 166);
INSERT INTO `item` VALUES (119, '弹簧海绵床垫', 3542, '席梦思床褥天然乳胶椰棕软硬两用', 378, 'img/upload/chuangdian1.png', 167);
INSERT INTO `item` VALUES (120, '简易推拉门衣柜组合', 3266, '板式组装整体2门3门4门收纳大衣橱', 379, 'img/upload/yigui1.png', 168);
INSERT INTO `item` VALUES (121, '全实木衣柜', 4555, '移门美式推拉门衣橱胡桃木大空间储物柜简约大气卧室橱', 380, 'img/upload/yigui2.png', 168);
INSERT INTO `item` VALUES (122, '全实木衣柜2门', 7566, '推拉门大衣橱1.8m，白橡木家具', 381, 'img/upload/yigui3.png', 168);
INSERT INTO `item` VALUES (123, '新古典床头柜', 1635, '复古抽屉角柜储物收纳床边柜卧室家具', 382, 'img/upload/chuangtougui1.png', 169);
INSERT INTO `item` VALUES (124, '实木脚床头柜', 1200, '新古典客厅角几边几复古小户型实木脚床头床边柜', 383, 'img/upload/chuangtougui2.png', 169);
INSERT INTO `item` VALUES (125, '床头收纳柜', 877, '北欧床边小柜子多功能简易经济型', 384, 'img/upload/chuangtougui3.png', 169);
INSERT INTO `item` VALUES (126, '美式化妆公主梳妆台', 3224, '实木脚卧室组装家具复古储物', 385, 'img/upload/zhuangtaizhuangdeng1.png', 1610);
INSERT INTO `item` VALUES (127, '全实木梳妆台', 1300, '北美进口黑胡桃木化妆桌美式简约卧室化妆台', 386, 'img/upload/zhuangtaizhuangdeng2.png', 1610);
INSERT INTO `item` VALUES (128, '北欧梳妆台', 1254, '小户型迷你卧室现代简约化妆台60cm化妆桌', 387, 'img/upload/zhuangtaizhuangdeng3.png', 1610);
INSERT INTO `item` VALUES (129, '落地全身试衣镜', 1100, '美式卧室衣帽间玄关穿衣镜子家用', 388, 'img/upload/chuanyijing1.png', 1611);
INSERT INTO `item` VALUES (130, '北欧实木穿衣镜', 522, '卧室镜子全身镜壁挂家用简约现代落地试衣镜', 389, 'img/upload/chuanyijing2.png', 1611);
INSERT INTO `item` VALUES (131, '实木穿衣镜', 343, '卧室镜子全身镜壁挂家用简约客厅贴墙镜试衣镜', 390, 'img/upload/chuanyijing3.png', 1611);
INSERT INTO `item` VALUES (132, '阳台小桌椅', 534, '简约户外休闲单人桌椅室外庭院露台靠背藤椅三件套', 391, 'img/upload/huwaiyi1.png', 1612);
INSERT INTO `item` VALUES (133, '户外折叠椅', 121, '便携式靠背休闲椅沙滩椅钓鱼椅子午睡午休床椅', 392, 'img/upload/huwaiyi2.png', 1612);
INSERT INTO `item` VALUES (134, '秋千吊椅', 896, '家用鸟巢吊兰椅子网红摇篮椅室内阳台懒人吊椅', 393, 'img/upload/diaolandiaoyi1.png', 1613);
INSERT INTO `item` VALUES (135, '吊篮藤椅', 1021, '鸟巢吊椅阳台室内秋千家用单人摇篮椅欧式懒人吊椅', 394, 'img/upload/diaolandiaoyi2.png', 1613);
INSERT INTO `item` VALUES (136, '网红吊床', 744, '室内家用双人摇椅阳台吊兰鸟巢摇篮椅秋千吊椅', 395, 'img/upload/diaolandiaoyi3.png', 1613);
INSERT INTO `item` VALUES (137, '户外休闲小桌椅三件套', 532, '家用庭院露天藤椅防水防晒室外套装', 396, 'img/upload/huwaizhuoyitaozhuang1.png', 1614);
INSERT INTO `item` VALUES (138, '户外遮阳伞', 1200, '庭院室外大太阳伞3米沙滩露天花园方形保安罗马伞', 397, 'img/upload/huwaizheyangpeng1.png', 1615);

-- ----------------------------
-- Table structure for item_category
-- ----------------------------
DROP TABLE IF EXISTS `item_category`;
CREATE TABLE `item_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父节点id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_category
-- ----------------------------
INSERT INTO `item_category` VALUES (1, 'In产品', 0);
INSERT INTO `item_category` VALUES (2, 'In定制', 1);
INSERT INTO `item_category` VALUES (3, 'In建材', 1);
INSERT INTO `item_category` VALUES (4, '定制衣柜', 2);
INSERT INTO `item_category` VALUES (5, '定制橱柜', 2);
INSERT INTO `item_category` VALUES (6, '卫浴用品', 3);
INSERT INTO `item_category` VALUES (7, '浴霸', 6);
INSERT INTO `item_category` VALUES (8, '浴缸', 6);

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  CONSTRAINT `item_stock_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 138 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (1, 400, 1);
INSERT INTO `item_stock` VALUES (2, 399, 2);
INSERT INTO `item_stock` VALUES (3, 402, 3);
INSERT INTO `item_stock` VALUES (4, 403, 4);
INSERT INTO `item_stock` VALUES (5, 403, 5);
INSERT INTO `item_stock` VALUES (6, 405, 6);
INSERT INTO `item_stock` VALUES (7, 406, 7);
INSERT INTO `item_stock` VALUES (8, 407, 8);
INSERT INTO `item_stock` VALUES (9, 408, 9);
INSERT INTO `item_stock` VALUES (10, 409, 10);
INSERT INTO `item_stock` VALUES (11, 410, 11);
INSERT INTO `item_stock` VALUES (12, 411, 12);
INSERT INTO `item_stock` VALUES (13, 407, 13);
INSERT INTO `item_stock` VALUES (14, 411, 14);
INSERT INTO `item_stock` VALUES (15, 414, 15);
INSERT INTO `item_stock` VALUES (16, 415, 16);
INSERT INTO `item_stock` VALUES (17, 416, 17);
INSERT INTO `item_stock` VALUES (18, 417, 18);
INSERT INTO `item_stock` VALUES (19, 418, 19);
INSERT INTO `item_stock` VALUES (20, 419, 20);
INSERT INTO `item_stock` VALUES (21, 420, 21);
INSERT INTO `item_stock` VALUES (22, 421, 22);
INSERT INTO `item_stock` VALUES (23, 422, 23);
INSERT INTO `item_stock` VALUES (24, 423, 24);
INSERT INTO `item_stock` VALUES (25, 424, 25);
INSERT INTO `item_stock` VALUES (26, 425, 26);
INSERT INTO `item_stock` VALUES (27, 426, 27);
INSERT INTO `item_stock` VALUES (28, 427, 28);
INSERT INTO `item_stock` VALUES (29, 428, 29);
INSERT INTO `item_stock` VALUES (30, 429, 30);
INSERT INTO `item_stock` VALUES (31, 430, 31);
INSERT INTO `item_stock` VALUES (32, 431, 32);
INSERT INTO `item_stock` VALUES (33, 432, 33);
INSERT INTO `item_stock` VALUES (34, 433, 34);
INSERT INTO `item_stock` VALUES (35, 427, 35);
INSERT INTO `item_stock` VALUES (36, 435, 36);
INSERT INTO `item_stock` VALUES (37, 436, 37);
INSERT INTO `item_stock` VALUES (38, 437, 38);
INSERT INTO `item_stock` VALUES (39, 438, 39);
INSERT INTO `item_stock` VALUES (40, 439, 40);
INSERT INTO `item_stock` VALUES (41, 440, 41);
INSERT INTO `item_stock` VALUES (42, 439, 42);
INSERT INTO `item_stock` VALUES (43, 442, 43);
INSERT INTO `item_stock` VALUES (44, 443, 44);
INSERT INTO `item_stock` VALUES (45, 444, 45);
INSERT INTO `item_stock` VALUES (46, 445, 46);
INSERT INTO `item_stock` VALUES (47, 446, 47);
INSERT INTO `item_stock` VALUES (48, 447, 48);
INSERT INTO `item_stock` VALUES (49, 448, 49);
INSERT INTO `item_stock` VALUES (50, 449, 50);
INSERT INTO `item_stock` VALUES (51, 450, 51);
INSERT INTO `item_stock` VALUES (52, 451, 52);
INSERT INTO `item_stock` VALUES (53, 452, 53);
INSERT INTO `item_stock` VALUES (54, 453, 54);
INSERT INTO `item_stock` VALUES (55, 454, 55);
INSERT INTO `item_stock` VALUES (56, 455, 56);
INSERT INTO `item_stock` VALUES (57, 456, 57);
INSERT INTO `item_stock` VALUES (58, 457, 58);
INSERT INTO `item_stock` VALUES (59, 458, 59);
INSERT INTO `item_stock` VALUES (60, 459, 60);
INSERT INTO `item_stock` VALUES (61, 460, 61);
INSERT INTO `item_stock` VALUES (62, 461, 62);
INSERT INTO `item_stock` VALUES (63, 462, 63);
INSERT INTO `item_stock` VALUES (64, 463, 64);
INSERT INTO `item_stock` VALUES (65, 464, 65);
INSERT INTO `item_stock` VALUES (66, 465, 66);
INSERT INTO `item_stock` VALUES (67, 466, 67);
INSERT INTO `item_stock` VALUES (68, 467, 68);
INSERT INTO `item_stock` VALUES (69, 468, 69);
INSERT INTO `item_stock` VALUES (70, 469, 70);
INSERT INTO `item_stock` VALUES (71, 470, 71);
INSERT INTO `item_stock` VALUES (72, 471, 72);
INSERT INTO `item_stock` VALUES (73, 472, 73);
INSERT INTO `item_stock` VALUES (74, 473, 74);
INSERT INTO `item_stock` VALUES (75, 474, 75);
INSERT INTO `item_stock` VALUES (76, 475, 76);
INSERT INTO `item_stock` VALUES (77, 476, 77);
INSERT INTO `item_stock` VALUES (78, 477, 78);
INSERT INTO `item_stock` VALUES (79, 478, 79);
INSERT INTO `item_stock` VALUES (80, 479, 80);
INSERT INTO `item_stock` VALUES (81, 480, 81);
INSERT INTO `item_stock` VALUES (82, 481, 82);
INSERT INTO `item_stock` VALUES (83, 482, 83);
INSERT INTO `item_stock` VALUES (84, 483, 84);
INSERT INTO `item_stock` VALUES (85, 484, 85);
INSERT INTO `item_stock` VALUES (86, 485, 86);
INSERT INTO `item_stock` VALUES (87, 486, 87);
INSERT INTO `item_stock` VALUES (88, 487, 88);
INSERT INTO `item_stock` VALUES (89, 488, 89);
INSERT INTO `item_stock` VALUES (90, 489, 90);
INSERT INTO `item_stock` VALUES (91, 490, 91);
INSERT INTO `item_stock` VALUES (92, 487, 92);
INSERT INTO `item_stock` VALUES (93, 492, 93);
INSERT INTO `item_stock` VALUES (94, 493, 94);
INSERT INTO `item_stock` VALUES (95, 494, 95);
INSERT INTO `item_stock` VALUES (96, 495, 96);
INSERT INTO `item_stock` VALUES (97, 496, 97);
INSERT INTO `item_stock` VALUES (98, 497, 98);
INSERT INTO `item_stock` VALUES (99, 498, 99);
INSERT INTO `item_stock` VALUES (100, 499, 100);
INSERT INTO `item_stock` VALUES (101, 500, 101);
INSERT INTO `item_stock` VALUES (102, 501, 102);
INSERT INTO `item_stock` VALUES (103, 502, 103);
INSERT INTO `item_stock` VALUES (104, 503, 104);
INSERT INTO `item_stock` VALUES (105, 504, 105);
INSERT INTO `item_stock` VALUES (106, 505, 106);
INSERT INTO `item_stock` VALUES (107, 506, 107);
INSERT INTO `item_stock` VALUES (108, 507, 108);
INSERT INTO `item_stock` VALUES (109, 508, 109);
INSERT INTO `item_stock` VALUES (110, 509, 110);
INSERT INTO `item_stock` VALUES (111, 510, 111);
INSERT INTO `item_stock` VALUES (112, 511, 112);
INSERT INTO `item_stock` VALUES (113, 512, 113);
INSERT INTO `item_stock` VALUES (114, 513, 114);
INSERT INTO `item_stock` VALUES (115, 514, 115);
INSERT INTO `item_stock` VALUES (116, 515, 116);
INSERT INTO `item_stock` VALUES (117, 516, 117);
INSERT INTO `item_stock` VALUES (118, 517, 118);
INSERT INTO `item_stock` VALUES (119, 518, 119);
INSERT INTO `item_stock` VALUES (120, 519, 120);
INSERT INTO `item_stock` VALUES (121, 520, 121);
INSERT INTO `item_stock` VALUES (122, 521, 122);
INSERT INTO `item_stock` VALUES (123, 522, 123);
INSERT INTO `item_stock` VALUES (124, 523, 124);
INSERT INTO `item_stock` VALUES (125, 524, 125);
INSERT INTO `item_stock` VALUES (126, 525, 126);
INSERT INTO `item_stock` VALUES (127, 526, 127);
INSERT INTO `item_stock` VALUES (128, 527, 128);
INSERT INTO `item_stock` VALUES (129, 528, 129);
INSERT INTO `item_stock` VALUES (130, 529, 130);
INSERT INTO `item_stock` VALUES (131, 530, 131);
INSERT INTO `item_stock` VALUES (132, 531, 132);
INSERT INTO `item_stock` VALUES (133, 532, 133);
INSERT INTO `item_stock` VALUES (134, 533, 134);
INSERT INTO `item_stock` VALUES (135, 534, 135);
INSERT INTO `item_stock` VALUES (136, 535, 136);
INSERT INTO `item_stock` VALUES (137, 536, 137);
INSERT INTO `item_stock` VALUES (138, 537, 138);

-- ----------------------------
-- Table structure for order_address_status
-- ----------------------------
DROP TABLE IF EXISTS `order_address_status`;
CREATE TABLE `order_address_status`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT ' ',
  `order_address_id` int(11) NOT NULL DEFAULT 0,
  `order_status` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_address_id`(`order_address_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  CONSTRAINT `order_address_status_ibfk_1` FOREIGN KEY (`order_address_id`) REFERENCES `user_address` (`address_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_address_status_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_address_status
-- ----------------------------
INSERT INTO `order_address_status` VALUES (5, '2019050800000800', 3, 4);
INSERT INTO `order_address_status` VALUES (6, '2019050800000900', 3, 4);
INSERT INTO `order_address_status` VALUES (7, '2019050800001000', 2, 4);
INSERT INTO `order_address_status` VALUES (8, '2019051100001100', 2, 3);
INSERT INTO `order_address_status` VALUES (10, '2019052300003900', 3, 1);
INSERT INTO `order_address_status` VALUES (13, '2019052500004600', 6, 4);
INSERT INTO `order_address_status` VALUES (14, '2019052500004700', 6, 1);
INSERT INTO `order_address_status` VALUES (15, '2019052500004800', 6, 2);
INSERT INTO `order_address_status` VALUES (16, '2019052500004900', 6, 1);
INSERT INTO `order_address_status` VALUES (17, '2019052500005000', 6, 3);
INSERT INTO `order_address_status` VALUES (26, '2019052500005100', 6, 2);
INSERT INTO `order_address_status` VALUES (27, '2019052500005200', 6, 4);
INSERT INTO `order_address_status` VALUES (28, '2019052500005300', 6, 3);
INSERT INTO `order_address_status` VALUES (30, '2019052500005500', 6, 1);
INSERT INTO `order_address_status` VALUES (31, '2019052500005600', 6, 2);
INSERT INTO `order_address_status` VALUES (32, '2019052500005700', 3, 4);
INSERT INTO `order_address_status` VALUES (33, '2019052600005800', 3, 1);
INSERT INTO `order_address_status` VALUES (34, '2019052600005900', 6, 1);
INSERT INTO `order_address_status` VALUES (35, '2019052700006000', 2, 1);
INSERT INTO `order_address_status` VALUES (36, '2019052700006100', 6, 1);
INSERT INTO `order_address_status` VALUES (37, '2019052700006200', 7, 4);
INSERT INTO `order_address_status` VALUES (38, '2019052700006300', 3, 1);
INSERT INTO `order_address_status` VALUES (39, '2019052700006400', 3, 1);
INSERT INTO `order_address_status` VALUES (40, '2019052700006500', 3, 1);
INSERT INTO `order_address_status` VALUES (41, '2019052700006600', 8, 1);
INSERT INTO `order_address_status` VALUES (42, '2019052700006700', 8, 4);
INSERT INTO `order_address_status` VALUES (43, '2019052700006800', 8, 1);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT ' ',
  `user_id` int(11) NOT NULL DEFAULT 0,
  `order_time` datetime(0) NOT NULL DEFAULT '1980-01-01 00:00:00',
  `item_id` int(11) NOT NULL DEFAULT 0,
  `item_price` double NOT NULL DEFAULT 0,
  `amount` int(11) NOT NULL DEFAULT 0,
  `order_price` double NOT NULL DEFAULT 0,
  `promo_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019050800000800', 6, '2019-05-08 22:38:08', 5, 600, 1, 600, 0);
INSERT INTO `order_info` VALUES ('2019050800000900', 6, '2019-05-08 22:57:10', 2, 150, 1, 150, 1);
INSERT INTO `order_info` VALUES ('2019050800001000', 6, '2019-05-08 22:57:28', 25, 100, 1, 100, 0);
INSERT INTO `order_info` VALUES ('2019051100001100', 6, '2019-05-11 14:30:35', 68, 150, 1, 150, 1);
INSERT INTO `order_info` VALUES ('2019052300003700', 16, '2019-05-23 15:03:41', 21, 150, 1, 150, 1);
INSERT INTO `order_info` VALUES ('2019052300003900', 6, '2019-05-23 17:10:01', 43, 100, 7, 700, 0);
INSERT INTO `order_info` VALUES ('2019052500004600', 6, '2019-05-25 17:34:10', 7, 70, 1, 70, 0);
INSERT INTO `order_info` VALUES ('2019052500004700', 6, '2019-05-25 17:34:10', 56, 600, 8, 4800, 0);
INSERT INTO `order_info` VALUES ('2019052500004800', 6, '2019-05-25 18:57:57', 89, 70, 1, 70, 0);
INSERT INTO `order_info` VALUES ('2019052500004900', 6, '2019-05-25 18:57:57', 12, 600, 9, 5400, 0);
INSERT INTO `order_info` VALUES ('2019052500005000', 6, '2019-05-25 18:57:57', 78, 100, 7, 700, 0);
INSERT INTO `order_info` VALUES ('2019052500005100', 6, '2019-05-25 18:57:57', 54, 40, 4, 160, 2);
INSERT INTO `order_info` VALUES ('2019052500005200', 6, '2019-05-25 18:59:21', 4, 70, 1, 70, 0);
INSERT INTO `order_info` VALUES ('2019052500005300', 6, '2019-05-25 18:59:21', 56, 600, 9, 5400, 0);
INSERT INTO `order_info` VALUES ('2019052500005500', 6, '2019-05-25 19:49:34', 10, 600, 9, 5400, 0);
INSERT INTO `order_info` VALUES ('2019052500005600', 6, '2019-05-25 20:12:24', 21, 70, 1, 70, 0);
INSERT INTO `order_info` VALUES ('2019052500005700', 6, '2019-05-25 20:15:42', 72, 70, 5, 350, 0);
INSERT INTO `order_info` VALUES ('2019052600005800', 6, '2019-05-26 11:15:03', 26, 150, 1, 150, 1);
INSERT INTO `order_info` VALUES ('2019052600005900', 6, '2019-05-26 11:15:42', 28, 150, 1, 150, 1);
INSERT INTO `order_info` VALUES ('2019052700006000', 6, '2019-05-27 02:24:31', 42, 3292, 1, 3292, 0);
INSERT INTO `order_info` VALUES ('2019052700006100', 6, '2019-05-27 04:33:46', 13, 2200, 5, 11000, 0);
INSERT INTO `order_info` VALUES ('2019052700006200', 18, '2019-05-27 05:14:20', 35, 80, 2, 160, 4);
INSERT INTO `order_info` VALUES ('2019052700006300', 6, '2019-05-27 05:24:47', 42, 3292, 1, 3292, 0);
INSERT INTO `order_info` VALUES ('2019052700006400', 6, '2019-05-27 05:24:47', 14, 2010, 2, 4020, 0);
INSERT INTO `order_info` VALUES ('2019052700006500', 6, '2019-05-27 05:24:47', 5, 2000, 1, 2000, 0);
INSERT INTO `order_info` VALUES ('2019052700006600', 19, '2019-05-27 12:22:33', 92, 156, 1, 156, 0);
INSERT INTO `order_info` VALUES ('2019052700006700', 19, '2019-05-27 12:28:49', 92, 156, 3, 468, 0);
INSERT INTO `order_info` VALUES ('2019052700006800', 19, '2019-05-27 12:28:49', 35, 80, 5, 400, 4);

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ',
  `start_date` datetime(0) NOT NULL DEFAULT '1980-01-01 00:00:00',
  `end_date` datetime(0) NOT NULL DEFAULT '1980-01-01 00:00:00',
  `item_id` int(11) NOT NULL DEFAULT 0,
  `promo_item_price` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES (1, ' 椅子促销', '2019-05-11 14:50:00', '2019-09-01 00:00:00', 2, 150);
INSERT INTO `promo` VALUES (2, ' 大甩卖', '2019-05-06 20:05:30', '2019-07-28 19:51:26', 3, 40);
INSERT INTO `promo` VALUES (3, '促销', '2019-05-28 14:55:00', '2019-05-30 14:55:00', 4, 40);
INSERT INTO `promo` VALUES (4, ' 促销', '2019-05-27 18:00:00', '2019-05-26 00:00:00', 35, 80);

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ',
  `current_value` int(11) NOT NULL DEFAULT 0,
  `step` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 69, 1);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  `address_detail` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '详细地址',
  `zip_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '邮政编码',
  `rcvd_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '收件人姓名',
  `rcvd_tel` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT ' ' COMMENT '电话',
  PRIMARY KEY (`address_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES (2, 6, '山东省济南市历城区东方小区21号楼三单元602', '250100', '张心宇', '17806268100');
INSERT INTO `user_address` VALUES (3, 6, '山东省青岛市香港东路7号青岛大学东苑数据科学与软件工程学院Java01班睿思楼师范学院', '230600', 'Anastasia', '17806286620');
INSERT INTO `user_address` VALUES (6, 6, '山东省济南市', '250100', '张心宇', '17806268189');
INSERT INTO `user_address` VALUES (7, 18, '宁夏路308号青岛大学', '250100', 'Aimee', '17806268189');
INSERT INTO `user_address` VALUES (8, 19, '青岛大学数据科学与软件工程学院', '250100', 'Eveligine', '17806268189');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别0女1男',
  `telphone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '电话',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `img_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户头像url',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telphone_unique_index`(`telphone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (6, 'AnastasiaZhang', 0, '17806268189', '100381986@qq.com', 'img/upload/f66438f1-6f3d-4a6e-870e-0ffc19c8dcd9.jpg');
INSERT INTO `user_info` VALUES (7, 'Anastasia', 0, '17806268180', '111@qq.com', 'img/upload/defaultF.jpg');
INSERT INTO `user_info` VALUES (8, '张心宇', 0, '17806268179', '1003814986@qq.com', 'img/upload/defaultF.jpg');
INSERT INTO `user_info` VALUES (9, 'INJIAN', 1, '17809283456', '1003814986@qq.com', 'img/upload/defaultM.jpg');
INSERT INTO `user_info` VALUES (11, 'QWER', 0, '17806268579', '1003814986@qq.com', 'F:\\ProgramCoding\\IntellJJavaCode\\injian\\src\\main\\resources\\upload\\defaultF.jpg');
INSERT INTO `user_info` VALUES (12, '张心宇', 0, '17806268175', '1003814986@qq.com', 'img/upload/defaultF.jpg');
INSERT INTO `user_info` VALUES (13, '张心', 0, '13567932456', '1003814986@qq.com', 'img/upload/defaultF.jpg');
INSERT INTO `user_info` VALUES (16, '张心宇', 0, '13358910346', '1003814986@qq.com', 'img/upload/defaultF.jpg');
INSERT INTO `user_info` VALUES (17, 'Aimee鸭', 0, '16606382022', '1075215496@qq.com', 'img/upload/defaultF.jpg');
INSERT INTO `user_info` VALUES (18, 'Aimee', 0, '15095292018', '10038986@qq.com', 'img/upload/4b5baba9-1903-484c-86a8-c2b8beffc86d.jpg');
INSERT INTO `user_info` VALUES (19, 'Eveligine', 0, '15169156378', '1003814986@qq.com', 'img/upload/9e9d5f0c-032b-4f47-bb93-061eafa505c1.jpg');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `encrpt_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '加密密码',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_pwd`(`user_id`) USING BTREE,
  CONSTRAINT `user_pwd` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (5, 'gnzLDuqKcGxMNKFokfhOew==', 6);
INSERT INTO `user_password` VALUES (6, 'ICy5YqxZB1uWSwcVLSNLcA==', 7);
INSERT INTO `user_password` VALUES (7, 'ZK0/sWbdtBosok8YA7i3Ig==', 8);
INSERT INTO `user_password` VALUES (8, 'VBbXzW7xlaD3YiqcVrVehA==', 9);
INSERT INTO `user_password` VALUES (9, 'VBbXzW7xlaD3YiqcVrVehA==', 11);
INSERT INTO `user_password` VALUES (10, 'gaBHq0fWljkU7QbCe0iIGA==', 12);
INSERT INTO `user_password` VALUES (11, '3XEA7Lp7bRuBRUFh5EqmxA==', 13);
INSERT INTO `user_password` VALUES (12, 'efw0XCbRRCjgppW58NYPBg==', 16);
INSERT INTO `user_password` VALUES (13, 'mAcNnuQEGxDzlmwX9V3W5A==', 17);
INSERT INTO `user_password` VALUES (14, 'gnzLDuqKcGxMNKFokfhOew==', 18);
INSERT INTO `user_password` VALUES (15, 'gaBHq0fWljkU7QbCe0iIGA==', 19);

SET FOREIGN_KEY_CHECKS = 1;
