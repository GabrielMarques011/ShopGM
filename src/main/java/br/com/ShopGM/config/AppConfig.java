package br.com.ShopGM.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class AppConfig {

	// Não iremos executar essa aplicação, o proprio sistema irá buscar esse metodo
	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");// direcionamento para o banco
		ds.setUrl("jdbc:mysql://localhost:3307/shopgm");// colocando o nome do banco e localizando
		ds.setUsername("root");// login do banco
		ds.setPassword("root");// senha do banco

		return ds;

	}

	// metodo para adaptar o banco
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
		adapter.setPrepareConnection(true);// preparar a conexao
		adapter.setGenerateDdl(true);// gerando o DDL (Definição de Dados) // cirando as tabelas para gente
		adapter.setShowSql(true);// ele coloca tudo que está fazendo no console

		return adapter;

	}

}