package be.groups.demo.database.sharding.configuration.properties.extension

import java.util.*

/**
 * Extend {@link List<E>} to retrieve first item through an {@link Optional<E>}
 */
fun <E> List<E>.findOptional(predicate: (E) -> Boolean): Optional<E> {
  return Optional.ofNullable(this.find { predicate(it) })
}

typealias TNSNames = String
/**
 * Extend {@link TNSNames} to check if the environment variable is set as default value 'nothing'
 */
fun TNSNames.isDefault() = this == "nothing"