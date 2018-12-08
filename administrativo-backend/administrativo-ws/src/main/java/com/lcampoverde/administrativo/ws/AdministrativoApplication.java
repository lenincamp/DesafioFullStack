package com.lcampoverde.administrativo.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@EntityScan(basePackageClasses = {
        AdministrativoApplication.class,
        Jsr310JpaConverters.class
    }, basePackages = {"com.lcampoverde.administrativo.cliente"})
@ComponentScan({"com.lcampoverde.administrativo.*"})
@PropertySource(value = {"classpath:application.yml"})
@EnableJpaRepositories(basePackages = {"com.lcampoverde.administrativo.cliente"})
public class AdministrativoApplication {
  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  public static void main(String[] args) {
    SpringApplication.run(AdministrativoApplication.class, args);
  }
}
