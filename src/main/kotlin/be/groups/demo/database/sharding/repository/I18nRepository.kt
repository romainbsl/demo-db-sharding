package be.groups.demo.database.sharding.repository

import be.groups.domains.utils.i18n.I18n
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface I18nRepository : JpaRepository<I18n, Long>
