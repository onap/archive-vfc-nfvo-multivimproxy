/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.onap.vfc.nfvo.multivimproxy.service.adapter.inf.IMultivimProxyAdapterMgrService;
import org.onap.vfc.nfvo.multivimproxy.common.constant.Constant;
import org.onap.vfc.nfvo.multivimproxy.common.constant.HttpConstant;
import org.onap.vfc.nfvo.multivimproxy.common.constant.ParamConstant;
import org.onap.vfc.nfvo.multivimproxy.common.constant.UrlConstant;
import org.onap.vfc.nfvo.multivimproxy.common.util.restclient.SystemEnvVariablesFactory;
import org.onap.vfc.nfvo.multivimproxy.service.adapter.inf.IMultivimProxyAdapter2MSBManager;
import org.onap.vfc.nfvo.multivimproxy.service.adapter.inf.IMultivimProxyAdapterMgrService;
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
public class MultivimProxyAdapterMgrService implements IMultivimProxyAdapterMgrService {

    private static final Logger LOG = LoggerFactory.getLogger(MultivimProxyAdapterMgrService.class);

    public static final String RESMGRADAPTERINFO = "resmgradapterinfo.json";

    @Override
    public void register() {
        // set BUS URL and mothedtype
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("url", UrlConstant.REST_MSB_REGISTER);
        paramsMap.put("methodType", ParamConstant.PARAM_POST);

        // get multivimproxy info and raise registration
        try {
            String resmgrInfo = readVimAdapterInfoFromJson();
            if(!"".equals(resmgrInfo)) {
                JSONObject adapterObject = JSONObject.fromObject(resmgrInfo);
                RegisterMultivimProxyThread resmgrThread = new RegisterMultivimProxyThread(paramsMap, adapterObject);
                Executors.newSingleThreadExecutor().submit(resmgrThread);
            } else {
                LOG.error("Resmgr info is null,please check!");
            }

        } catch(IOException e) {
            LOG.error("Failed to read Resmgr info! " + e.getMessage(), e);
        }

    }

    /**
     * Retrieve VIM driver information.
     * 
     * @return
     * @throws IOException
     */
    public static String readVimAdapterInfoFromJson() throws IOException {
        String fileContent = "";

        String fileName = SystemEnvVariablesFactory.getInstance().getAppRoot() + System.getProperty("file.separator")
                + "etc" + System.getProperty("file.separator") + "adapterInfo" + System.getProperty("file.separator")
                + RESMGRADAPTERINFO;

        try (
            InputStream ins = new FileInputStream(fileName);
            BufferedInputStream bins = new BufferedInputStream(ins)){

            byte[] contentByte = new byte[ins.available()];
            int num = bins.read(contentByte);

            if(num > 0) {
                fileContent = new String(contentByte);
            }
        } catch(FileNotFoundException e) {
            LOG.error(fileName + "is not found!", e);
        } 

        return fileContent;
    }

    private static class RegisterMultivimProxyThread implements Runnable {

        // Thread lock Object
        private final Object lockObject = new Object();

        private IMultivimProxyAdapter2MSBManager adapter2MSBMgr = new MultivimProxyAdapter2MSBManager();

        // url and mothedtype
        private Map<String, String> paramsMap;

        // driver body
        private JSONObject adapterInfo;

        public RegisterMultivimProxyThread(Map<String, String> paramsMap, JSONObject adapterInfo) {
            this.paramsMap = paramsMap;
            this.adapterInfo = adapterInfo;
        }

        @Override
        public void run() {
            LOG.info("start register resmgr", RegisterMultivimProxyThread.class);

            if(paramsMap == null || adapterInfo == null) {
                LOG.error("parameter is null,please check!", RegisterMultivimProxyThread.class);
                return;
            }

            // catch Runtime Exception
            try {
                sendRequest(paramsMap, adapterInfo);
            } catch(Exception e) {
                LOG.error(e.getMessage(), e);
            }

        }

        private void sendRequest(Map<String, String> paramsMap, JSONObject driverInfo)throws InterruptedException {
            JSONObject resultObj = adapter2MSBMgr.registerProxy(paramsMap, driverInfo);

            if(Integer.valueOf(resultObj.get("retCode").toString()) == HttpConstant.HTTP_CREATED) {
                LOG.info("Resmgr has now Successfully Registered to the Microservice BUS!");
            } else {
                LOG.error("Resmgr failed to  Register to the Microservice BUS! Reason:"
                        + resultObj.get("reason").toString() + " retCode:" + resultObj.get("retCode").toString());

                // if registration fails,wait one minute and try again
                synchronized(lockObject) {
			while(Integer.valueOf(resultObj.get("retCode").toString()) != Constant.HTTP_CREATED){
                        lockObject.wait(Constant.REPEAT_REG_TIME);
			resultObj = adapter2MSBMgr.registerProxy(this.paramsMap, this.adapterInfo);
			}
                    }
                LOG.info("Resmgr has now Successfully Registered to the Microservice BUS!");
	    }

        }

    }

    @Override
    public void unregister() {
        // unregister
    }

}
