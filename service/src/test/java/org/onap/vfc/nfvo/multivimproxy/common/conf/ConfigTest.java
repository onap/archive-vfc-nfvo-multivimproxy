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
package org.onap.vfc.nfvo.multivimproxy.common.conf;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.onap.vfc.nfvo.multivimproxy.common.constant.Constant;

import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ConfigTest {


    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void testGetter()
    {
        Config.getHost();
        Config.getCloudRegionId();
        Config.getOpenstackPrefix();
        Config.getCloudOwner();
        Config.getGlobalCustomerId();
        Config.getPort();
        Config.getServiceType();
        Config.getTenantId();
    }
 /*    @Test(expected = RuntimeException.class)
    public void testException() throws Exception
    {
       Properties prps =mock(Properties.class);
        prps.load(Config.class.getClassLoader().getResourceAsStream(Constant.CONF));
    }
*/
}
