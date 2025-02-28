package generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Scanner;

// 代码生成器
@SpringBootTest
public class CodeGenerator {
    public static void main(String[] args) {
        String modular = "daily";
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://192.168.1.220:3306/daily?useUnicode=true&useSSL=false&characterEncoding=utf8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("pcc") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()
//                            .fileOverride() // 是否开启文件覆盖
                            .outputDir(projectPath+"/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.bc.finance.modular."+modular) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath+"/src/main/resources/mapper/" + modular)); // 设置mapperXml生成路径
                })
                // 正式使用
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(scanner.apply("输入要生成的表, 使用\"分隔").split(",")) // 设置需要生成的表名
                            .controllerBuilder()
                            .enableRestStyle()
                            .entityBuilder()
                            .enableLombok(); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
