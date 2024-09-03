package com.zzy;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;
import java.util.Scanner;

public class CodeGenerator {
    private static final String projectPath = System.getProperty("user.dir");
    private static final String url = "jdbc:mysql://localhost:3306/your_database?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "your_password";
    private static final String parentPackageName = "com.zzy";
    private static final String author = "zzy";
    private static final String outPath = projectPath + "/src/main/java/";
    private static final String mapperPath = projectPath + "/src/main/java/com/zzy/mapper/xml/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the database table names (separated by commas for multiple table names):");
        String tablesInput = scanner.nextLine();
        System.out.println("Do you want to remove the table name prefix? (yes/no)");
        String removePrefixInput = scanner.nextLine();
        String prefix = "";
        if ("yes".equalsIgnoreCase(removePrefixInput)) {
            System.out.println("Please enter the prefix to remove:");
            prefix = scanner.nextLine();
        }
        scanner.close();
        String[] tableNames = tablesInput.split(",");
        execute(tableNames, prefix);
    }


    public static void execute(String[] tableNames, String prefix) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder.author(author).outputDir(outPath).disableOpenDir())
                .packageConfig(builder -> builder.parent(parentPackageName).pathInfo(Collections.singletonMap(OutputFile.xml, mapperPath)))
                .strategyConfig(builder -> {
                    builder.addInclude(tableNames)
                            .addTablePrefix(prefix) 
                            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableChainModel()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .logicDeleteColumnName("deleted").logicDeletePropertyName("deleted")
                            .versionColumnName("version").versionPropertyName("version")
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .controllerBuilder().enableRestStyle()
                            .mapperBuilder();
                })
                .execute();
    }

}
