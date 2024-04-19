package cokr.xit.adds.core.spring.config;// package cokr.xit.adds.core.config;
//
// import java.sql.SQLException;
//
// import javax.sql.DataSource;
//
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
//
// @Configuration
// @Profile("local")
// public class H2ServerConfiguration {
//
//     @Bean
//     public Server h2TcpServer() throws SQLException {
//         return Server.createTcpServer()
//                 .start();
//     }
//
//     @Bean
//     @ConfigurationProperties("spring.datasource") // yml의 설정값을 Set한다.
//     public DataSource dataSource() throws SQLException {
//         Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
//         return new org.apache.tomcat.jdbc.pool.DataSource();
//     }
// }
