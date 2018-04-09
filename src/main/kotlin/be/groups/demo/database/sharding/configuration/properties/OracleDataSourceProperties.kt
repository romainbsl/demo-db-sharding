package be.groups.demo.database.sharding.configuration.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import javax.sql.DataSource

/**
 * Subclass of {@link DataSourceProperties}
 * that extend properties with @id and @isDefault
 */
class OracleDataSourceProperties : DataSourceProperties() {
  var id: Int? = null
  @Value("default")
  var isDefault: Boolean = false

  fun build(): DataSource {
    url = "jdbc:oracle:thin:@$name"
    return initializeDataSourceBuilder().build()
  }

  override fun toString(): String {
    return "Database: $name"
  }
}