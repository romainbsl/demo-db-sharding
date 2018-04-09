package be.groups.demo.database.sharding.controller

import be.groups.demo.database.sharding.service.I18nService
import be.groups.demo.database.sharding.utils.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/i18n")
class I18nController @Autowired constructor(private val i18nService: I18nService) {

  @PutMapping
  fun create(@RequestParam(name = "name") name: String) {
    i18nService.createOnMultipleDb(name)
  }

  @GetMapping("/count")
  fun count(): Map<Database, Long> {
    return i18nService.countForMultipleDb()
  }


}