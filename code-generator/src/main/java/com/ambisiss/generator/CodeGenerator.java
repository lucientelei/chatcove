package com.ambisiss.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 13:27:09
 */
public class CodeGenerator {

    /**
     * 项目路径
     */
    private static final String PARENT_DIR = System.getProperty("user.dir");
    /**
     * 基本路径
     */
    private static final String SRC_MAIN_JAVA = "/src/main/java/";
    /**
     * xml路径
     */
    private static final String XML_PATH = PARENT_DIR + "/chatcove-system/src/main/resources/mappers";
    /**
     * entity路径
     */
    private static final String ENTITY_PATH = PARENT_DIR + "/chatcove-system/src/main/java/com/ambisiss/system/entity";
    /**
     * mapper路径
     */
    private static final String MAPPER_PATH = PARENT_DIR + "/chatcove-system/src/main/java/com/ambisiss/system/mapper";
    /**
     * service路径
     */
    private static final String SERVICE_PATH = PARENT_DIR + "/chatcove-system/src/main/java/com/ambisiss/system/service";
    /**
     * serviceImpl路径
     */
    private static final String SERVICE_IMPL_PATH = PARENT_DIR + "/chatcove-system/src/main/java/com/ambisiss/system/service/impl/";
    /**
     * controller路径
     */
    private static final String CONTROLLER_PATH = PARENT_DIR + "/chatcove-chat-api/src/main/java/com/ambisiss/api/controller";
    /**
     * 数据库url
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatcove?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    /**
     * 数据库用户名
     */
    private static final String USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        // 要构建代码的表名
//        String[] tableNames = {"ch_chat_records", "ch_friend_relationship", "ch_group_members", "ch_group_message",
//                "ch_groups", "ch_message_status", "ch_message_type", "ch_relation_status", "ch_user", "ch_user_friend"};
        String[] tableNames ={"ch_group_members"};
        FastAutoGenerator.create(DB_URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(builder -> builder
                        .author("chenxiaoye")
                        .enableSwagger()
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent("")
                        .xml("mappers")
                        .entity("com.ambisiss.system.entity")
                        .mapper("com.ambisiss.system.mapper")
                        .service("com.ambisiss.system.service")
                        .serviceImpl("com.ambisiss.system.service.impl")
                        .controller("com.ambisiss.api.controller")
                        .pathInfo(getPathInfo())
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(tableNames)
                        // entity
                        .entityBuilder()
                        .fileOverride()
                        .enableChainModel()
                        .fileOverride()
                        .enableLombok()
                        .enableRemoveIsPrefix()
                        .logicDeleteColumnName("is_delete")
                        .idType(IdType.ASSIGN_ID)
                        .addTableFills(new Column("create_time", FieldFill.INSERT))
                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        // controller
                        .controllerBuilder()
                        .fileOverride()
                        .enableRestStyle()
                        .formatFileName("%sController")
                        // service
                        .serviceBuilder()
                        .fileOverride()
                        .superServiceClass(IService.class)
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImp")
                        // mapper
                        .mapperBuilder()
                        .fileOverride()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .superClass(BaseMapper.class)
                        .formatMapperFileName("%sDao")
                        .formatXmlFileName("%sXml")
                        .enableMapperAnnotation()
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 获取路径信息map
     *
     * @return {@link Map< OutputFile, String> }
     * @author MK
     * @date 2022/4/21 21:21
     */
    private static Map<OutputFile, String> getPathInfo() {
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        pathInfo.put(OutputFile.entity, ENTITY_PATH);
        pathInfo.put(OutputFile.mapper, MAPPER_PATH);
//        pathInfo.put(OutputFile.service, SERVICE_PATH);
//        pathInfo.put(OutputFile.serviceImpl, SERVICE_IMPL_PATH);
//        pathInfo.put(OutputFile.controller, CONTROLLER_PATH);
        pathInfo.put(OutputFile.xml, XML_PATH);
        return pathInfo;
    }

}
