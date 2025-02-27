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

package org.apache.streampipes.connect.management.compact.generator;

import org.apache.streampipes.model.connect.adapter.AdapterDescription;
import org.apache.streampipes.model.connect.adapter.compact.CompactAdapter;
import org.apache.streampipes.model.util.ElementIdGenerator;

public class AdapterBasicsGenerator implements AdapterModelGenerator {

  @Override
  public void apply(AdapterDescription adapterDescription,
                    CompactAdapter compactAdapter) {
    adapterDescription.setName(compactAdapter.name());
    adapterDescription.setDescription(compactAdapter.description());
    if (compactAdapter.id() != null) {
      adapterDescription.setElementId(compactAdapter.id());
    } else {
      adapterDescription.setElementId(ElementIdGenerator.makeElementId(adapterDescription));
    }
  }
}
