package org.example.config;
import org.springframework.context.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    @Bean
    public Connection connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "password";
        return DriverManager.getConnection(url, username, password);
    }
}