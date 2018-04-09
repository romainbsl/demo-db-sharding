package be.groups.demo.database.sharding.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OracleRepository @Autowired constructor(private val em: EntityManager) {
  fun checkDbName() =
    em.createNativeQuery("SELECT SYS_CONTEXT('USERENV','CURRENT_SCHEMA') FROM DUAL").singleResult as String
}