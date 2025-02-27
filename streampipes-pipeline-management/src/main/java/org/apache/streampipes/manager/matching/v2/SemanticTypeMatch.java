/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.streampipes.manager.matching.v2;

import org.apache.streampipes.manager.matching.v2.utils.MatchingUtils;
import org.apache.streampipes.model.client.matching.MatchingResultMessage;
import org.apache.streampipes.model.client.matching.MatchingResultType;

import java.util.List;
import java.util.Objects;

public class SemanticTypeMatch extends AbstractMatcher<String, String> {

  public SemanticTypeMatch() {
    super(MatchingResultType.SEMANTIC_TYPE_MATCH);
  }

  @Override
  public boolean match(String offer, String requirement, List<MatchingResultMessage> errorLog) {
    if (offer == null && (requirement != null)) {
      return false;
    }
    boolean match = MatchingUtils.nullCheck(offer, requirement) || requirement.equalsIgnoreCase(offer);

    if (!match) {
      buildErrorMessage(errorLog, buildText(requirement));
    }
    return match;
  }

  private String buildText(String requirement) {
    return Objects.requireNonNullElse(requirement, "-");
  }

}
