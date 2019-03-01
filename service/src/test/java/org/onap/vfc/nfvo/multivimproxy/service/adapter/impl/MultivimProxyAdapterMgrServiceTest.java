/*
 * Copyright 2018 Huawei Technologies Co., Ltd.
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
package org.onap.vfc.nfvo.multivimproxy.service.adapter.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MultivimProxyAdapterMgrServiceTest {

    MultivimProxyAdapterMgrService mgrService;

    @Before
    public void setUp() throws Exception {
        mgrService = new MultivimProxyAdapterMgrService();
    }

    @Test
    public void testRegister() {
        mgrService.register();

    }

    @Test
    public void testReadVimAdapterInfoFromJson() throws Exception{
        MultivimProxyAdapterMgrService.readVimAdapterInfoFromJson();
    }


}

