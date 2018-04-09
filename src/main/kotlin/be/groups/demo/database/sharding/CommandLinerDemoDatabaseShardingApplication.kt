package be.groups.demo.database.sharding

import be.groups.demo.database.sharding.service.I18nService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional

@Import(value = [(DemoConfiguration::class)])
open class CommandLinerDemoDatabaseShardingApplication {

  @Bean
  @Transactional
  open fun demo(@Autowired i18nService: I18nService) = CommandLineRunner {
    println(i18nService.countForMultipleDb())
  }
}


fun main(args: Array<String>) {
  SpringApplication.run(CommandLinerDemoDatabaseShardingApplication::class.java, *args)
}