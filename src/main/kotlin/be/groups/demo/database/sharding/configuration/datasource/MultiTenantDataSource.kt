package be.groups.demo.database.sharding.configuration.datasource

import be.groups.demo.database.sharding.configuration.properties.OracleDataSourceProperties
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.stereotype.Component

/**
 * Routing {@link DataSource}
 *
 * Handle all the {@link DataSource} from the configuration files
 * then manage the routing of queries through the context {@link DataSource}
 * carried by {@link DataSourceContextHolder.oracleDb}
 */
class MultiTenantDataSource : AbstractRoutingDataSource() {
  override fun determineCurrentLookupKey(): OracleDataSourceProperties? {
    return DataSourceContextHolder.oracleDb
  }
}
