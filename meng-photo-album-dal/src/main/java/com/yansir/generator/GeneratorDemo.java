package com.yansir.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
* @author YANSIR
* @Description:自动生成mybatis-Mapper工具
* @date 2019/6/14 11:32
*/

public class GeneratorDemo {

    public static void main(String[] args) throws Exception {


        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(GeneratorDemo.class.getClassLoader().getResourceAsStream("generatorConfig_mengphotoalbum.xml"));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(null);
        } catch (InvalidConfigurationException e) {
//              assertEquals(2, e.getErrors().size());
            throw e;
        }
        for (String warning : warnings) {
            System.out.println(warning);
        }
        System.out.println("generator execute success!");

    }

}
