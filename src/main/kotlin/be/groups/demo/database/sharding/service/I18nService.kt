package be.groups.demo.database.sharding.service

import be.groups.common.database.configuration.DataSourceConfigBuilder
import be.groups.common.database.sharding.DataSourceContextHolder
import be.groups.demo.database.sharding.model.i18n.I18n
import be.groups.demo.database.sharding.model.i18n.I18nItem
import be.groups.demo.database.sharding.model.i18n.I18nLanguage
import be.groups.demo.database.sharding.repository.I18nRepository
import be.groups.demo.database.sharding.repository.OracleRepository
import be.groups.demo.database.sharding.utils.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.sql.DataSource

@Component
class I18nService @Autowired constructor(
  private val sourceHandler: DataSourceConfigBuilder,
  private val i18nRepository: I18nRepository,
  private val oracleRepository: OracleRepository,
  private val entityManager: EntityManager,
  private val dataSource: DataSource
) {

  fun createOnMultipleDb(name: String) {
    val i18n = I18n.builder()
      .item(I18nItem.builder().text(I18nLanguage.FR, "Test Transaction FR").build())
      .item(I18nItem.builder().text(I18nLanguage.EN, "Test Transaction EN").build())
      .build()

    DataSourceContextHolder.oracleDb = sourceHandler.dataSources.find { it.id == Database.PREPROD.id }
    i18nRepository.count()


  }

  fun countForMultipleDb(): Map<Database, Long> {
    val countPrint: (String, Any) -> String = { first, second -> "💡 There is $second I18n rows in $first !" }
    val dbName: (String) -> String = { "Your connected on $it" }
    DataSourceContextHolder.oracleDb = sourceHandler.dataSources.find { it.id == Database.PREPROD.id }

    // CENTRAL
    val centralCount = i18nRepository.count()
    println(countPrint(Database.CENTRAL.code, centralCount))
    println(dbName(oracleRepository.checkDbName()))

    // PREPROD
    val preprodCount = i18nRepository.count()
    println(countPrint(Database.PREPROD.code, preprodCount))
    println(dbName(oracleRepository.checkDbName()))

    // SODEV4
    DataSourceContextHolder.oracleDb = sourceHandler.dataSources.find { it.id == Database.SODEV4.id }
    val sodev4Count = i18nRepository.count()
    println(countPrint(Database.SODEV4.code, sodev4Count))
    println(dbName(oracleRepository.checkDbName()))

    // Reset to CENTRAL
    DataSourceContextHolder.clearOracleDb()
    val centralCount2 = i18nRepository.count()
    println(countPrint(Database.CENTRAL.code, centralCount2))
    println(dbName(oracleRepository.checkDbName()))

    return mapOf(
      Database.CENTRAL to centralCount,
      Database.PREPROD to preprodCount,
      Database.SODEV4 to sodev4Count
    )

  }
}