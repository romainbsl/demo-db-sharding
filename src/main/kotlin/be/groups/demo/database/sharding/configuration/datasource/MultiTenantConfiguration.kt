package be.groups.demo.database.sharding.configuration.datasource

import be.groups.demo.database.sharding.configuration.properties.DataSourceConfigBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import java.util.*
import javax.sql.DataSource

/**
 * Database configuration set-up
 * This will get the databases defines in resources
 * to build the Application context
 */
@Configuration
@DependsOn("checkTnsNames")
open class MultiTenantConfiguration {

  @Autowired
  private lateinit var dataSourceConfigBuilder: DataSourceConfigBuilder

  /**
   * Bean that retrieve {@link DataSource} from configuration files
   * then build a {@link MultiTenantDataSource} that handle
   * all possible database connections
   */
  @Bean
  open fun dataSource(): DataSource {
    val dataSource = MultiTenantDataSource()
    val resolvedDataSources = HashMap<Any, Any>()

    // Populate with DataSource(s)
    dataSourceConfigBuilder.dataSources
        .forEach { resolvedDataSources.put(it, it.build()) }

    // Set the default DataSource (isDefault() or first())
    dataSource.setDefaultTargetDataSource(dataSourceConfigBuilder.default.build())

    // Create the final multi-tenant DataSource(s).
    dataSource.setTargetDataSources(resolvedDataSources)

    // Finalize the initialization of the DataSource(s).
    dataSource.afterPropertiesSet()

    return dataSource
  }
}
