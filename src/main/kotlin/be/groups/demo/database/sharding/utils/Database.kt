package be.groups.demo.database.sharding.utils

enum class Database(val id: Int, val code: String) {
  PREPROD(99, "SOPRE1"),
  SODEV4(199, "SODEV4"),
  CENTRAL(6, "SOEXP6")
}