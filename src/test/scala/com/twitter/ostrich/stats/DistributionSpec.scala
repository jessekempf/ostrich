/*
 * Copyright 2011 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twitter.ostrich.stats

import org.specs.Specification

object DistributionSpec extends Specification {
  "Distribution" should {
    val histogram0 = Histogram()
    val histogram1 = Histogram(10)
    val histogram2 = Histogram(10, 20)

    "equals" in {
      Distribution(histogram1.clone()) mustEqual Distribution(histogram1.clone())
      Distribution(histogram1) must not(beEqual(Distribution(histogram2)))
    }

    "toMap" in {
      Distribution(histogram2).toMap mustEqual
        Map("count" -> 2, "maximum" -> 23, "minimum" -> 10, "average" -> 15, "sum" -> 30,
            "p25" -> 10, "p50" -> 10, "p75" -> 23, "p90" -> 23, "p95" -> 23, "p99" -> 23,
            "p999" -> 23, "p9999" -> 23)
      Distribution(histogram0).toMap mustEqual Map("count" -> 0)
    }

    "toJson" in {
      Distribution(histogram2).toJson mustEqual
        "{\"average\":15,\"count\":2,\"maximum\":23,\"minimum\":10,\"p25\":10,\"p50\":10," +
          "\"p75\":23,\"p90\":23,\"p95\":23,\"p99\":23,\"p999\":23,\"p9999\":23,\"sum\":30}"
    }
  }
}
