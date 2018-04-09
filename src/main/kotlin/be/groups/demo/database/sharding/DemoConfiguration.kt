package be.groups.demo.database.sharding

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EntityScan("be.groups.demo.database.sharding.model.i18n")
@ComponentScan("be.groups.demo.database.sharding")
class DemoConfiguration