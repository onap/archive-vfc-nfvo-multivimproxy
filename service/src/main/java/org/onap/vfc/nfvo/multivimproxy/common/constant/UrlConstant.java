/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.vfc.nfvo.multivimproxy.common.constant;

/**
 * <br/>
 * <p>
 * Constant for REST URL.
 * </p>
 *
 * @author
 * @version VFC 1.0 2016-3-17
 */
public class UrlConstant {

    /**
     * networks target.
     */
    public static final String LOCATION_TARGET = "location";

    /**
     * MSB register url.
     */
    public static final String REST_MSB_REGISTER = "/api/microservices/v1/services";

    // /api/extsys/v1/vims/%s
    public static final String ESR_GET_VIM_URL =
            "/aai/v11/cloud-infrastructure/cloud-regions/cloud-region/%s/%s/esr-system-info-list/esr-system-info/%s";

    // /api/extsys/v1/vims
    public static final String ESR_GET_VIMS_URL =
            "/aai/v11/cloud-infrastructure/cloud-regions/cloud-region/%s/%s/esr-system-info-list";

    
    public static final String MULTI_VIM_PREFIX =
            "/api/multicloud/v0";
    
    private UrlConstant() {
        // private constructor
    }

}
