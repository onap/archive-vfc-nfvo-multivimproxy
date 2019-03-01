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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionArgsTest {


    ExceptionArgs exceptionArgs= new ExceptionArgs();
    ExceptionArgs exceptionArgs2= new ExceptionArgs(null,null,null,null);
    String[] descArgs={"a","b"};
    String[] reasonArgs={"a","b"};
    String[] detailArgs={"a","b"};
    String[] adviceArgs={"a","b"};
    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void testGetterSetter()
    {
        exceptionArgs.setAdviceArgs(adviceArgs);
        exceptionArgs.setDescArgs(descArgs);
        exceptionArgs.setDetailArgs(detailArgs);
        exceptionArgs.setReasonArgs(reasonArgs);
        exceptionArgs.getAdviceArgs();
        exceptionArgs.getDescArgs();
        exceptionArgs.getDetailArgs();
        exceptionArgs.getReasonArgs();
    }

}