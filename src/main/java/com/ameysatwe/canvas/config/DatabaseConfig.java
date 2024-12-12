package com.ameysatwe.canvas.config;

import com.ameysatwe.canvas.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    @Bean
    @Scope("singleton")
    public SessionFactory getSessionFactory(){
        Map<String,Object> settings = new HashMap<>();

        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/canvas");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "ameysatwe12");

        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//            settings.put("hibernate.dialect.storage_engine", "");
        settings.put("hibernate.show-sql", "true");
//            settings.put("hibernate.hbm2ddl.auto","create");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();
        MetadataSources metaDataSources = new MetadataSources(serviceRegistry);
        metaDataSources.addPackage("com.ameysatwe.canvas.models");

        metaDataSources.addAnnotatedClass(User.class);
        metaDataSources.addAnnotatedClass(Assignment.class);
        metaDataSources.addAnnotatedClass(Course.class);
        metaDataSources.addAnnotatedClass(Enrollment.class);
        metaDataSources.addAnnotatedClass(Submission.class);
        metaDataSources.addAnnotatedClass(Instructor.class);
        metaDataSources.addAnnotatedClass(TA.class);
        metaDataSources.addAnnotatedClass(Student.class);
        metaDataSources.addAnnotatedClass(Role.class);

        Metadata metaData = metaDataSources.buildMetadata();

        return metaData.getSessionFactoryBuilder().build();
    }
}
