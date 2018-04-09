package be.groups.demo.database.sharding.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

/**
 * {@link PropertySources} loader that loads the files
 *  - resources/dbconfig/datasource.properties
 *  - resources/dbconfig/datasource-override.properties
 * and populate datasource and override lists of {@link OracleDataSourceProperties}
 */
@Component
@PropertySources(value = [
  (PropertySource("classpath:/dbconfig/datasource.properties")),
  (PropertySource("classpath:/dbconfig/datasource-override.properties", ignoreResourceNotFound = true))
])
@ConfigurationProperties("datasource")
internal class DataSourceConfigReader(
  val base: ArrayList<OracleDataSourceProperties> = ArrayList(),
  val override: ArrayList<OracleDataSourceProperties> = ArrayList()
)