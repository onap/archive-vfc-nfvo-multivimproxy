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
package org.onap.vfc.nfvo.multivimproxy.service.rest;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxrs.impl.HttpServletRequestFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.onap.vfc.nfvo.multivimproxy.common.util.request.RequestUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ProxyRoaTest {
    ProxyRoa proxyRoa;

    HttpServletRequest httpServletRequest= mock(HttpServletRequest.class);

    HttpServletResponse httpServletResponse= mock(HttpServletResponse.class);

    @Before
    public void setUp() throws Exception {
        proxyRoa = new ProxyRoa();
    }

    @Test
    public void testApidoc() throws Exception {
        proxyRoa.apidoc();
    }

    /*@Test
    public void testCreateTokens() {

       proxyRoa.createTokens(httpServletRequest, httpServletResponse);
    }
    @Test
    public void testProxyPost() {

        proxyRoa.proxyPost(httpServletRequest, httpServletResponse);
    }
    @Test
    public void testProxyPut() {

        proxyRoa.porxyPut(httpServletRequest, httpServletResponse);
    }
*/
}