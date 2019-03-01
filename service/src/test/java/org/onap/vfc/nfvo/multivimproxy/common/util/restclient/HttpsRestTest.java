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
package org.onap.vfc.nfvo.multivimproxy.common.util.restclient;


import org.eclipse.jetty.client.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class HttpsRestTest {


    @InjectMocks
    HttpsRest httpsRest;
    @Mock
    HttpClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

   /* @Test
    public void testInitHttpsRest() throws Exception {
       // httpsRest.initHttpsRest();

    }
    @Test(expected = Exception.class)
    public void testInitHttpsRestIsNull() throws Exception {
        //doThrow(Exception.class).when(client).start();
        //verify(client).start();


    }*/

    @Test
    public void testGet() throws Exception {
        httpsRest.get("huawei", null);
    }

    @Test
    public void testHead() throws Exception {
        httpsRest.head("huawei", null);
    }

    @Test
    public void testHead2() throws Exception {
        httpsRest.head("huawei", null, null);
    }

    @Test
    public void testAsyncGet() throws Exception {
        httpsRest.asyncGet("huawei", null, null);
    }

    @Test
    public void testAsyncGet2() throws Exception {
        httpsRest.asyncGet("huawei", null, null, null);
    }

    @Test
    public void testPut() throws Exception {
        httpsRest.put("huawei", null);
    }

    @Test
    public void testDelete() throws Exception {
        httpsRest.delete("huawei", null);

    }

    @Test
    public void testPatch() throws Exception {
        httpsRest.patch("huawei", null);
    }

    @Test
    public void testPatch2() throws Exception {
        httpsRest.patch("huawei", null, null);
    }

    @Test
    public void testPost() throws Exception {
        httpsRest.post("huawei", null);
    }


}