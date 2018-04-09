package be.groups.demo.database.sharding.configuration.properties

import be.groups.demo.database.sharding.configuration.properties.extension.TNSNames
import be.groups.demo.database.sharding.configuration.properties.extension.isDefault
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileNotFoundException

@Configuration
open class TNSNamesConfig {

  @Value("\${oracle.net.tns_admin:nothing}")
  private val tnsNamePath: TNSNames? = null

  private val envKey = "oracle.net.tns_admin"

  private val cannotReachDatabase =
      """The application won't be able to reach database.
        |       1. You should check if the system/environment variable '$envKey' exists
        |       2. You should check if the system/environment variable '$envKey' point at an existing directory
        |       3. You should check if the '$envKey' directory contains a file name tnsnames.ora
      """.trimMargin()

  @Bean
  open fun checkTnsNames(): Boolean = when {
    tnsNamePath == null || tnsNamePath.isEmpty() || tnsNamePath.isDefault() ->
      throw IllegalStateException("The environment variable '$envKey' is missing. $cannotReachDatabase")
    !File(tnsNamePath).exists() ->
      throw IllegalStateException("The TNS Name directory is not available. $cannotReachDatabase")
    !File("$tnsNamePath/tnsnames.ora").isFile ->
      throw FileNotFoundException("The file tnsnames.ora is missing. $cannotReachDatabase")
    else -> {
      // If property comes from OS environment variables, set it in the current context
      if (System.getProperty(envKey) == null) System.setProperty(envKey, tnsNamePath)
      true
    }
  }
}