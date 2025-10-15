package dev.nonentity.stapi;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"test"})
class STAPITest {

  @Karate.Test
  Karate runAll() {
    return Karate.run().relativeTo(getClass()).configDir("classpath:");
  }

}
