package be.groups.demo.database.sharding

import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(value = [(DemoConfiguration::class)])
class SpringMvcDemoDatabaseShardingApplication

fun main(args: Array<String>) {
  runApplication<SpringMvcDemoDatabaseShardingApplication>(*args)
}
