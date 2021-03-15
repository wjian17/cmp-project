package com.analizy.cmp;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * Created on 2020-03-20
 * @author macro
 */
public class CodeGenerator {

    @Test
     public void generator(){
        String tableName = "flyway_schema_history";
        String tablePrefix = "flyway";
        String model = "workflow";
        generator(tableName,tablePrefix,model);
    }

    private void generator(String tableName,String tablePrefix,String model) {
        String path = "/src/main/java/";
        String parent = "com.analizy.cmp";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        //user.dir：代表用户的当前工作目录
        String projectPath = System.getProperty("user.dir");
        //设置生成文件的输出目录
        gc.setOutputDir(projectPath + path);
        //添加开发人员
        gc.setAuthor("wangjian030");
        //是否打开输出目录
        gc.setOpen(false);
        //开启覆盖
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);

        // 2.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/cmp_rest?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 3.包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        pc.setModuleName(model);
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 4.策略配置
        StrategyConfig strategy = new StrategyConfig();
        //设置数据库表映射到实体的命名策略，下划线转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略：下划线转驼峰命名（非必要，如果不配置则按照Naming执行）
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.macro.mybatisplus.entity.base.BaseEntity.java");
        //开启lombok注解
        strategy.setEntityLombokModel(true);
        //生成RestController注解
        strategy.setRestControllerStyle(true);
        // 公共父类
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //乐观锁
        strategy.setVersionFieldName("version");
        // 表名
        strategy.setInclude(tableName);
        // tablePrefix
        strategy.setTablePrefix(tablePrefix+"_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}