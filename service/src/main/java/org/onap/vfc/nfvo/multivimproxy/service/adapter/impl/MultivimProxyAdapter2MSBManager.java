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

package org.onap.vfc.nfvo.multivimproxy.service.adapter.impl;

import java.util.Map;

import org.onap.vfc.nfvo.multivimproxy.common.constant.Constant;
import org.onap.vfc.nfvo.multivimproxy.common.constant.HttpConstant;
import org.onap.vfc.nfvo.multivimproxy.common.util.RestfulUtil;
import org.onap.vfc.nfvo.multivimproxy.service.adapter.inf.IMultivimProxyAdapter2MSBManager;
import org.onap.vfc.nfvo.multivimproxy.common.util.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version VFC 1.0 Sep 22, 2016
 */
public class MultivimProxyAdapter2MSBManager implements IMultivimProxyAdapter2MSBManager {

    private static final Logger LOG = LoggerFactory.getLogger(MultivimProxyAdapter2MSBManager.class);

    @Override
    public JSONObject registerProxy(Map<String, String> paramsMap, JSONObject driverInfo) {
        JSONObject resultObj = new JSONObject();

        RestfulResponse rsp = RestfulUtil.getRemoteResponse(paramsMap, driverInfo.toString());
        if(null == rsp) {
            LOG.error("function=registerResmgr,  RestfulResponse is null");
            resultObj.put("reason", "RestfulResponse is null.");
            resultObj.put("retCode", Constant.ERROR_CODE);
            return resultObj;
        }
        LOG.warn("function=multivimproxy, status={}, content={}.", rsp.getStatus(), rsp.getResponseContent());
        String resultCreate = rsp.getResponseContent();

        if(rsp.getStatus() == HttpConstant.HTTP_CREATED) {
            LOG.warn("function=registerProxy, msg= status={}, result={}.", rsp.getStatus(), resultCreate);
            resultObj = JSONObject.fromObject(resultCreate);
            resultObj.put("retCode", HttpConstant.HTTP_CREATED);
            return resultObj;
        } else if(rsp.getStatus() == HttpConstant.HTTP_INVALID_PARAMETERS) {
            LOG.error("function=registerProxy, msg=MSB return fail,invalid parameters,status={}, result={}.",
                    rsp.getStatus(), resultCreate);
            resultObj.put("reason", "MSB return fail,invalid parameters.");
        } else if(rsp.getStatus() == HttpConstant.HTTP_INNERERROR_CODE) {
            LOG.error("function=registerProxy, msg=MSB return fail,internal system error,status={}, result={}.",
                    rsp.getStatus(), resultCreate);
            resultObj.put("reason", "MSB return fail,internal system error.");
        }
        resultObj.put("retCode", Constant.ERROR_CODE);
        return resultObj;
    }

    @Override
    public JSONObject unregisterProxy(Map<String, String> paramsMap) {
        JSONObject resultObj = new JSONObject();

        RestfulResponse rsp = RestfulUtil.getRemoteResponse(paramsMap, "");
        if(null == rsp) {
            LOG.error("function=unregisterProxy,  RestfulResponse is null");
            resultObj.put("reason", "RestfulResponse is null.");
            resultObj.put("retCode", Constant.ERROR_CODE);
            return resultObj;
        }
        String resultCreate = rsp.getResponseContent();

        if(rsp.getStatus() == HttpConstant.HTTP_NOCONTENT) {
            LOG.warn("function=unregisterProxy, msg= status={}, result={}.", rsp.getStatus(), resultCreate);
            resultObj = JSONObject.fromObject(resultCreate);
            resultObj.put("retCode", HttpConstant.HTTP_NOCONTENT);
            return resultObj;
        } else if(rsp.getStatus() == HttpConstant.HTTP_NOTFOUND_CODE) {
            LOG.error(
                    "function=unregisterProxy, msg=MSB return fail,can't find the service instance.status={}, result={}.",
                    rsp.getStatus(), resultCreate);
            resultObj.put("reason", "MSB return fail,can't find the service instance.");
        } else if(rsp.getStatus() == HttpConstant.HTTP_INVALID_PARAMETERS) {
            LOG.error("function=unregisterProxy, msg=MSB return fail,invalid parameters,status={}, result={}.",
                    rsp.getStatus(), resultCreate);
            resultObj.put("reason", "MSB return fail,invalid parameters.");
        } else if(rsp.getStatus() == HttpConstant.HTTP_INNERERROR_CODE) {
            LOG.error("function=unregisterProxy, msg=MSB return fail,internal system error,status={}, result={}.",
                    rsp.getStatus(), resultCreate);
            resultObj.put("reason", "MSB return fail,internal system error.");
        }
        resultObj.put("retCode", Constant.ERROR_CODE);
        return resultObj;
    }

}
