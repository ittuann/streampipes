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

package org.apache.streampipes.extensions.api.assets;

import java.io.IOException;
import java.util.Properties;

public interface AssetResolver {

  default byte[] getAsset(String assetName) throws IOException {
    return getAsset(this.getClass().getClassLoader(), assetName);
  }

  default Properties getLocale(String localeName) throws IOException {
    return getLocale(this.getClass().getClassLoader(), localeName);
  }

  byte[] getAsset(ClassLoader classLoader,
                  String assetName) throws IOException;

  Properties getLocale(ClassLoader classLoader,
                       String localeName) throws IOException;

}
