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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.StringJoiner;

public class DefaultAssetResolver implements AssetResolver {

  private final String appId;

  public DefaultAssetResolver(String appId) {
    this.appId = appId;
  }

  @Override
  public byte[] getAsset(ClassLoader classLoader,
                         String assetName) throws IOException {
    return getResourceFile(classLoader, assetName).readAllBytes();
  }

  @Override
  public Properties getLocale(ClassLoader classLoader,
                              String localeName) throws IOException {
    Properties props = new Properties();
    props.load(new InputStreamReader(getResourceFile(classLoader, localeName), StandardCharsets.UTF_8));
    return props;
  }

  protected InputStream getResourceFile(ClassLoader classLoader,
                                        String filename) throws IOException {
    if (existsFile(filename)) {
      var path = makePath(filename);
      return classLoader.getResourceAsStream(path);
    } else {
      throw new IOException(String.format("Could not read file %s", filename));
    }
  }

  protected String makePath(String filename) {
    return new StringJoiner("/")
        .add(appId)
        .add(filename)
        .toString();
  }

  protected boolean existsFile(String filename) {
    return this.getClass()
        .getClassLoader()
        .getResource(
            makePath(filename)
        ) != null;
  }
}
