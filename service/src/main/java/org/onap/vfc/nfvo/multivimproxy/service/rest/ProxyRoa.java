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

package org.onap.vfc.nfvo.multivimproxy.service.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.onap.vfc.nfvo.multivimproxy.common.conf.Config;
import org.onap.vfc.nfvo.multivimproxy.common.constant.Constant;
import org.onap.vfc.nfvo.multivimproxy.common.constant.ParamConstant;
import org.onap.vfc.nfvo.multivimproxy.common.constant.UrlConstant;
import org.onap.vfc.nfvo.multivimproxy.common.util.RestfulUtil;
import org.onap.vfc.nfvo.multivimproxy.common.util.request.RequestUtil;
import org.onap.vfc.nfvo.multivimproxy.common.util.restclient.RestfulParametes;
import org.onap.vfc.nfvo.multivimproxy.common.util.restclient.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProxyRoa {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyRoa.class);

    private static final String vimId = Config.getCloudRegionId() + "_" + Config.getCloudRegionId();

    /**
     * API doc.
     *
     * @return
     * @throws IOException
     */
    @GET
    @Path("/api/multivimproxy/v1/swagger.json")
    public String apidoc() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("swagger.json"));
    }

    @POST
    @Path("/v3/auth/tokens")
    public String createTokens(@Context HttpServletRequest context, @Context HttpServletResponse rsp) {
        JSONObject object = RequestUtil.getJsonRequestBody(context);
        if(null == object) {
            LOGGER.error("function=createTokens; msg=add error, because createTokens is null.");
            String resultStr = "Login params insufficient";
            rsp.setStatus(Constant.HTTP_BAD_REQUEST);

            return resultStr;
        }

        LOGGER.info("IdentityRoa::createTokens:{}", object.toString());

        // transfer to MultiVim
        RestfulParametes restfulParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(3);
        headerMap.put("Content-Type", "application/json");
        restfulParametes.setHeaderMap(headerMap);
        restfulParametes.setRawData(object.toString());
        String identityUrl = UrlConstant.MULTI_VIM_PREFIX + vimId + "/v3/auth/tokens";

        RestfulResponse rest = RestfulUtil.getResponse(identityUrl, restfulParametes, null, ParamConstant.PARAM_POST);

        String result = rest.getResponseContent();
        if(null != result) {
            JSONObject ret = JSONObject.fromObject(result);
            JSONObject token = ret.getJSONObject("token");
            JSONArray catalog = token.getJSONArray("catalog");

            for(int i = 0; i < catalog.size(); i++) {
                JSONArray endpoints = catalog.getJSONObject(i).getJSONArray("endpoints");
                for(int j = 0; j < endpoints.size(); j++) {
                    String newUrl = "";
                    JSONObject endpoint = endpoints.getJSONObject(j);
                    String url = endpoint.getString("url");
                    for(int k = 6; k < url.split("/").length; k++) {
                        newUrl += "/" + url.split("/")[k];
                    }
                    newUrl += Config.getOpenstackPrefix();
                    endpoint.replace("url", newUrl);
                }
            }

            rsp.setStatus(rest.getStatus());

        } else {
            LOGGER.error("function=createTokens; msg=add error, because multivim return null.");
        }

        return result;
    }

    @POST
    @Path("{var:.*}")
    public String proxyPost(@Context HttpServletRequest context, @Context HttpServletResponse rsp) {
        JSONObject object = RequestUtil.getJsonRequestBody(context);
        if(null == object) {
            LOGGER.error("function=proxyPost; msg=Post error, because proxyPost is null.");
            String resultStr = "POST params insufficient";
            rsp.setStatus(Constant.HTTP_BAD_REQUEST);

            return resultStr;
        }
        // transfer to MultiVim
        RestfulParametes restfulParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(3);
        headerMap.put("Content-Type", "application/json");
        restfulParametes.setHeaderMap(headerMap);
        restfulParametes.setRawData(object.toString());
        String Url = UrlConstant.MULTI_VIM_PREFIX + vimId + context.getRequestURI();

        RestfulResponse rest = RestfulUtil.getResponse(Url, restfulParametes, null, ParamConstant.PARAM_POST);
        String result = rest.getResponseContent();
        rsp.setStatus(rest.getStatus());
        return result;
    }

    @GET
    @Path("{var:.*}")
    public String proxyGet(@Context HttpServletRequest context, @Context HttpServletResponse rsp) {

        // transfer to MultiVim
        RestfulParametes restfulParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(3);
        headerMap.put("Content-Type", "application/json");
        restfulParametes.setHeaderMap(headerMap);
        String Url = UrlConstant.MULTI_VIM_PREFIX + vimId + context.getRequestURI();

        RestfulResponse rest = RestfulUtil.getResponse(Url, restfulParametes, null, ParamConstant.PARAM_GET);
        String result = rest.getResponseContent();
        rsp.setStatus(rest.getStatus());
        return result;
    }

    @PUT
    @Path("{var:.*}")
    public String proxyPut(@Context HttpServletRequest context, @Context HttpServletResponse rsp) {
        JSONObject object = RequestUtil.getJsonRequestBody(context);
        if(null == object) {
            LOGGER.error("function=proxyPost; msg=PUT error, because porxyPut is null.");
            String resultStr = "update params insufficient";
            rsp.setStatus(Constant.HTTP_BAD_REQUEST);

            return resultStr;
        }
        // transfer to MultiVim
        RestfulParametes restfulParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(3);
        headerMap.put("Content-Type", "application/json");
        restfulParametes.setHeaderMap(headerMap);
        restfulParametes.setRawData(object.toString());
        String Url = UrlConstant.MULTI_VIM_PREFIX + vimId + context.getRequestURI();
        RestfulResponse rest = RestfulUtil.getResponse(Url, restfulParametes, null, RestfulUtil.TYPE_PUT);
        String result = rest.getResponseContent();
        rsp.setStatus(rest.getStatus());
        return result;
    }

    @DELETE
    @Path("{var:.*}")
    public String proxyDelete(@Context HttpServletRequest context, @Context HttpServletResponse rsp) {

        // transfer to MultiVim
        RestfulParametes restfulParametes = new RestfulParametes();
        Map<String, String> headerMap = new HashMap<>(3);
        headerMap.put("Content-Type", "application/json");
        restfulParametes.setHeaderMap(headerMap);
        String Url = UrlConstant.MULTI_VIM_PREFIX + vimId + context.getRequestURI();

        RestfulResponse rest = RestfulUtil.getResponse(Url, restfulParametes, null, RestfulUtil.TYPE_DEL);
        String result = rest.getResponseContent();
        rsp.setStatus(rest.getStatus());
        return result;
    }

}
