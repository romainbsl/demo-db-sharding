package be.groups.demo.database.sharding.service

import be.groups.demo.database.sharding.DemoConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [(DemoConfiguration::class)])
class I18nServiceTest {

  @Autowired
  private lateinit var i18nService: I18nService

  @Before fun space() = println()

  @Test
  fun `count from i18n multiple datasources`() {
    println(" 🌟 NON transactional test")
    assertThat(i18nService.countForMultipleDb()).isNotEmpty
  }

  @Test
  @Transactional
  fun `transactional count from i18n multiple datasources`() {
    println(" 🌟 TRANSACTIONAL test")
    assertThat(i18nService.countForMultipleDb()).isNotEmpty
  }
}