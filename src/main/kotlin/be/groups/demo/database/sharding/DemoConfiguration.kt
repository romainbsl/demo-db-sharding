package be.groups.demo.database.sharding

import be.groups.common.database.DatabaseShardingComponent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@SpringBootApplication
@EntityScan("be.groups.demo.database.sharding.model.i18n")
@ComponentScan("be.groups.demo.database.sharding")
@Import(value = [(DatabaseShardingComponent::class)])
class DemoConfiguration