package com.jia.shiro;

import com.alibaba.fastjson.JSONObject;
import com.jia.model.entity.UserDO;
import com.jia.model.result.CodeMsg;
import com.jia.model.result.Result;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
public class ShiroLoginFilter extends AccessControlFilter {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroLoginFilter.class);
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		
		// 检查用户是否已经处于登录态
		UserDO user = ShiroUtil.getUser();
		if(null != user || isLoginRequest(request, response)){
            return Boolean.TRUE;
        }
		
		if (ShiroUtil.isAjax(request)) {
			Result<String> result = Result.fail(CodeMsg.NO_LOGIN);
			writeResult(response, result);
		}
		
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE ;
		
	}
	
	protected void writeResult(ServletResponse response, Result<String> result){
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(JSONObject.toJSONString(result));
		} catch (Exception e) {
			logger.error("Unknown exception", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}

}
