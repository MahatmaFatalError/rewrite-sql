/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.sql;

import static org.openrewrite.java.Assertions.java;
import static org.openrewrite.java.Assertions.javaVersion;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

public class FormatSqlTest implements RewriteTest {

  @Override
  public void defaults(RecipeSpec spec) {
    spec.recipe(new FormatSql()).allSources(s -> s.markers(javaVersion(17)));
  }

  @DocumentExample
  @Test
  void regular() {
    rewriteRun(
        // language=java
        java(
            """
                class Test {
                    String query = \"""
                            SELECT * FROM my_table
                            WHERE
                              something = 1;\\
                            \""";
                }
                """,
            """
                class Test {
                    String query = \"""
                            SELECT * FROM
                            my_table
                            WHERE something = 1;\\
                            \""";
                }
                """
          )
      );
  }
}
