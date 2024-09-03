package com.zzy;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.Scanner;

public class CodeGenerator {

    // Database configuration variables
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "your_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        new AutoGenerator()
                .setGlobalConfig(globalConfig())
                .setDataSource(dataSourceConfig())
                .setPackageInfo(packageConfig())
                .setStrategy(strategyConfig())
                .execute();
    }

    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
                .setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                .setFileOverride(true)
                .setOpen(false)
                .setAuthor("zzy")
                .setIdType(IdType.AUTO)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setServiceName("%sService")
                .setDateType(DateType.ONLY_DATE);
    }

    private static DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", DB_HOST, DB_PORT, DB_NAME))
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername(DB_USER)
                .setPassword(DB_PASSWORD);
    }

    private static PackageConfig packageConfig() {
        return new PackageConfig()
                .setParent("com.zzy")
                .setMapper("mapper")
                .setXml("mapper.xml")
                .setEntity("entity")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller");
    }

    private static StrategyConfig strategyConfig() {
        StrategyConfig strategy = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setLogicDeleteFieldName("deleted")
                .setVersionFieldName("version")
                .setInclude(scannerTableNames().split(","))
                .setTableFillList(Arrays.asList(
                        new TableFill("create_time", FieldFill.INSERT),
                        new TableFill("update_time", FieldFill.INSERT_UPDATE)
                ));

        promptToRemoveEntityPrefix(strategy);

        return strategy;
    }

    // asking for removing the entity prefix
    private static void promptToRemoveEntityPrefix(StrategyConfig strategy) {
        System.out.println("Do you want to remove the entity prefix? (yes/no):");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if ("yes".equalsIgnoreCase(answer)) {
            System.out.println("Enter the prefix to remove:");
            String prefix = scanner.nextLine();
            strategy.setEntityTableFieldAnnotationEnable(true);
            strategy.setTablePrefix(prefix);
        }
    }

    // input table names
    private static String scannerTableNames() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + "table names (comma-separated)" + ":");

        String input = scanner.nextLine();
        if (input == null || input.trim().isEmpty()) {
            throw new MybatisPlusException("Enter valid " + "Table names (comma-separated)" + "!");
        }
        return input;
    }
}
