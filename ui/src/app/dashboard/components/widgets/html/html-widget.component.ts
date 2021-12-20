/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import {Component, OnDestroy, OnInit} from "@angular/core";
import {RxStompService} from "@stomp/ng2-stompjs";
import {BaseStreamPipesWidget} from "../base/base-widget";
import {StaticPropertyExtractor} from "../../../sdk/extractor/static-property-extractor";
import {ResizeService} from "../../../services/resize.service";
import {HtmlConfig} from "./html-config";
import {DashboardService} from "../../../services/dashboard.service";

@Component({
    selector: 'html-widget',
    templateUrl: './html-widget.component.html',
    styleUrls: ['./html-widget.component.css']
})
export class HtmlWidgetComponent extends BaseStreamPipesWidget implements OnInit, OnDestroy {

    item: string;
    width: number;
    height: number;

    selectedHtmlField: string;

    constructor(rxStompService: RxStompService, dashboardService: DashboardService, resizeService: ResizeService) {
        super(rxStompService, dashboardService, resizeService, false);
    }

    ngOnInit(): void {
        super.ngOnInit();
        this.width = this.computeCurrentWidth(this.itemWidth);
        this.height = this.computeCurrentHeight(this.itemHeight);
    }

    ngOnDestroy(): void {
        super.ngOnDestroy();
    }

    extractConfig(extractor: StaticPropertyExtractor) {
        this.selectedHtmlField = extractor.mappingPropertyValue(HtmlConfig.HTML_MAPPING_KEY)
    }

    protected onEvent(event: any) {
        this.item = event[this.selectedHtmlField];
    }

    protected onSizeChanged(width: number, height: number) {
        this.width = width;
        this.height = height;
    }

}
