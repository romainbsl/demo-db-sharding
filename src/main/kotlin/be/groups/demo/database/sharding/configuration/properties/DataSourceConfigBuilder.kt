package be.groups.demo.database.sharding.configuration.properties

import be.groups.demo.database.sharding.configuration.properties.extension.findOptional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.IllegalStateException

/**
 * Public accessor to {@link DataSource} configurations
 */
@Component
class DataSourceConfigBuilder {

  @Autowired
  private lateinit var configReader: DataSourceConfigReader

  /**
   * Property to get {@link DataSource} base configuration
   */
  val dataSourceBase by lazy { configReader.base }
  /**
   * Property to get {@link DataSource} override configuration
   */
  val dataSourceOverride by lazy { configReader.override }

  /**
   * Retrieve the isDefault {@link DataSource}
   * If there is no default, take the first {@link DataSource} available
   */
  val default: OracleDataSourceProperties by lazy {
    dataSources.firstOrNull { it.isDefault } ?: dataSources.first()
  }

  /**
   * Get all the available {@link DataSource}
   * - base
   * - override
   */
  val dataSources by lazy {
    (dataSourceOverride union dataSourceBase).apply {
      if (isEmpty())
        throw IllegalStateException("There is no base configuration. The app " +
            "won't be able to reach database !")
    }.distinctBy { it.id }.distinctBy { it.name }
        .toList()
  }

  /**
   * Get the {@link DataSource} corresponding to the @id
   */
  fun findById(id: Int) = dataSources.findOptional { id == it.id }

  /**
   * Get the {@link DataSource} corresponding to the @dbName
   */
  fun findByName(dbName: String) = dataSources.findOptional { dbName == it.name }
}