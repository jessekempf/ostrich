/*
 * Copyright 2009 Twitter, Inc.
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

package com.twitter.stats

/**
 * Singleton StatsCollector that collects performance data for the application.
 */
object Stats extends StatsCollection {
  includeJvmStats = true

  // helper function for computing deltas over counters
  final def delta(oldValue: Long, newValue: Long): Long = {
    if (oldValue <= newValue) {
      newValue - oldValue
    } else {
      (Long.MaxValue - oldValue) + (newValue - Long.MinValue) + 1
    }
  }

  /**
   * Create a function that returns the delta of a counter each time it's called.
   */
  def makeDeltaFunction(counter: Counter): () => Double = {
    var lastValue: Long = 0

    () => {
      val newValue = counter()
      val rv = delta(lastValue, newValue)
      lastValue = newValue
      rv.toDouble
    }
  }
}