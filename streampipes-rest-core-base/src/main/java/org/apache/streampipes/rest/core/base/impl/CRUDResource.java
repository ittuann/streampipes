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

package org.apache.streampipes.rest.core.base.impl;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CRUDResource<T, ReT> {

  @GetMapping(produces = {
      MediaType.APPLICATION_JSON_VALUE,
      "application/yaml",
      "application/yml"})
  List<T> findAll();

  @GetMapping(path = "/{id}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      "application/yaml",
      "application/yml"})
  T findById(@PathVariable("id") String id);

  @PostMapping(
      produces = {
          MediaType.APPLICATION_JSON_VALUE,
          "application/yaml",
          "application/yml"},
      consumes = {
          MediaType.APPLICATION_JSON_VALUE,
          "application/yaml",
          "application/yml"}
  )
  void create(@RequestBody T entity);

  @PutMapping(path = "/{id}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      "application/yaml",
      "application/yml"
  }, consumes = {
      MediaType.APPLICATION_JSON_VALUE,
      "application/yaml",
      "application/yml"})
  ReT update(@RequestBody T entity);

  @DeleteMapping(path = "/{id}")
  void delete(@PathVariable("id") String id);
}
