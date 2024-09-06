
# MyBatis-Plus 代码生成器配置文件
## 项目概述
本配置文件是基于 MyBatis-Plus 的代码生成器，旨在快速生成 Java 项目的以下模块代码：Entity、Mapper、Mapper XML、Service、ServiceImpl 和 Controller。
## 版本控制
以下为本项目所依赖的版本信息：
- **MyBatis-Plus Spring Boot 3 Starter**
  - 版本：3.5.7
- **MyBatis-Plus Generator**
  - 版本：3.3.0 or 3.5.7
- **Apache Velocity Engine Core**
  - 版本：2.3
> 注意：由于最新版的 MyBatis-Plus Generator 语法进行了大改，这里放了两个代码用来参考。另外，自 MyBatis-Plus 3.0.3 版本起，代码生成器与模板引擎的默认依赖已被移除，需要手动添加相关依赖。在此，我们选择了 Velocity 作为模板引擎。

## 时序图

![image-20240906150444859](https://gitee.com/zzy2401/picbed/raw/master/images/image-20240906150444859.png)

## 效果展示
以下是代码生成器运行后的效果展示：

![20240906150817](https://gitee.com/zzy2401/picbed/raw/master/images/20240906150817.png)

![image-20240903151646193](https://gitee.com/zzy2401/picbed/raw/master/images/image-20240903151646193.png)


## 代码展示

```java
@Component
public class CodeGenerator {

    private static final String DB_HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "my_blog";
    private static final String username = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : "root";
    private static final String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "YOUR_PASSWORD";

    private static final String url = String.format(
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
            DB_HOST,
            DB_PORT,
            DB_NAME
    );
    private static final String projectPath = System.getProperty("user.dir");
    private static final String parentPackageName = "com.zzy";
    private static final String author = "zzy";
    private static final String outPath = projectPath + "/src/main/java/";
    private static final String mapperPath = projectPath + "/src/main/java/com/zzy/mapper/xml/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the database table names (separated by commas for multiple table names):");
        String tablesInput = scanner.nextLine();
        System.out.println("Please fill in the prefix to remove (if none, leave it blank):");
        String prefix = scanner.nextLine();
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
```


