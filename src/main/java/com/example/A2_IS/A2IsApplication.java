package com.example.A2_IS;



import com.example.A2_IS.models.Student;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@EnableR2dbcAuditing
@SpringBootApplication
public class A2IsApplication {

	public static void main(String[] args) {
		SpringApplication.run(A2IsApplication.class, args);

//		Connection c = null;
//		Statement stmt = null;
//		try {
//			Class.forName("org.postgresql.Driver");
//			c = DriverManager.
//					getConnection("jdbc:postgresql://localhost:5432/postgres",
//							"postgres", "My01pass");
//			stmt = c.createStatement();
//			stmt.execute( "drop table if exists student;");
//			stmt.execute( "create table student (id integer , name varchar); ");
//
//
////			while ( rs.next() ) {
////				String nome = rs.getString("nome");
////				System.out.println("Nome->" + nome );
////			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println(e.getClass().getName()+": "+e.getMessage());
//			System.exit(0);
//		}
//		System.out.println("Opened database successfully");

	}
	@Bean
	ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		ResourceDatabasePopulator resource =
				new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
		initializer.setDatabasePopulator(resource);
		return initializer;
	}
}
