package com.flash.sale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@MapperScan(basePackages = "com.flash.sale.dao", annotationClass = Repository.class)
public class FlashSaleApp {
  public static void main(String[] args) {
    SpringApplication.run(FlashSaleApp.class, args);
  }
}
