package com.ameysatwe.canvas;

import com.ameysatwe.canvas.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CanvasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanvasApplication.class, args);
    }

    @Bean
    public SessionFactory getSessionFactory(){
            Map<String,Object> settings = new HashMap<>();

            settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/canvas");
            settings.put("hibernate.connection.username", "root");
            settings.put("hibernate.connection.password", "ameysatwe12");

            settings.put("hibernate.hbm2ddl.auto", "update");
            settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            settings.put("hibernate.dialect.storage_engine", "innodb");
            settings.put("hibernate.show-sql", "true");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();
            MetadataSources metaDataSources = new MetadataSources(serviceRegistry);
            metaDataSources.addPackage("com.example.demo.models");
            metaDataSources.addAnnotatedClass(User.class);
            Metadata metaData = metaDataSources.buildMetadata();

            return metaData.getSessionFactoryBuilder().build();
    }
}