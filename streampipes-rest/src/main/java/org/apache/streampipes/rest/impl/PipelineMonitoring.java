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
package org.apache.streampipes.rest.impl;

import org.apache.streampipes.manager.monitoring.pipeline.ExtensionsLogProvider;
import org.apache.streampipes.manager.monitoring.pipeline.ExtensionsServiceLogExecutor;
import org.apache.streampipes.model.monitoring.SpLogEntry;
import org.apache.streampipes.model.monitoring.SpMetricsEntry;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/pipeline-monitoring")
public class PipelineMonitoring extends AbstractMonitoringResource {

  @GetMapping(value = "/pipeline/{pipelineId}/logs", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, List<SpLogEntry>>> getLogInfoForPipeline(
      @PathVariable("pipelineId") String pipelineId) {
    return ok(ExtensionsLogProvider.INSTANCE.getLogInfosForPipeline(pipelineId));
  }

  @GetMapping(value = "/pipeline/{pipelineId}/metrics", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, SpMetricsEntry>> getMetricsInfoForPipeline(
      @PathVariable("pipelineId") String pipelineId,
      @RequestParam(value = "forceUpdate", required = false, defaultValue = "false") boolean forceUpdate) {
    if (forceUpdate) {
      new ExtensionsServiceLogExecutor().triggerUpdate();
    }
    return ok(ExtensionsLogProvider.INSTANCE.getMetricInfosForPipeline(pipelineId));
  }

}
