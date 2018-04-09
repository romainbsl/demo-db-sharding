package be.groups.demo.database.sharding.configuration.datasource

import be.groups.demo.database.sharding.configuration.properties.OracleDataSourceProperties

/**
 * {@link DataSourceContextHolder} has the responsibility to keep the (ThreadLocal's) current {@link DataSource}
 */
object DataSourceContextHolder {
  private val contextHolder = ThreadLocal<OracleDataSourceProperties>()

  /**
   * Endpoint to the current {@link DataSource}
   */
  var oracleDb: OracleDataSourceProperties?
    get() = contextHolder.get()
    set(value) {
      value ?: throw NullPointerException("[GS] DataSourceContextHolder: value cannot be null")
      contextHolder.set(value)
    }

  /**
   * Reset the current {@link DataSource} to the default one
   */
  fun clearOracleDb() {
    contextHolder.remove()
  }
}
