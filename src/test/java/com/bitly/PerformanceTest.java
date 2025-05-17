package com.bitly;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import java.io.IOException;
import java.time.Duration;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

public class PerformanceTest {

  @Test
  public void testPerformance() throws IOException {
    TestPlanStats stats = testPlan(
        threadGroup(20, 1000,
            httpSampler("http://localhost:8080/urls")
                .post("{\"original_url\":\"https://google.com\"}", ContentType.APPLICATION_JSON)
                .header("Content-Type", "application/json")),
        // this is just to log details of each request stats
        jtlWriter("target/jtls")).run();
    assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(5));
  }

}
