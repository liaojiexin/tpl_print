package com.example.base.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * https://www.cnblogs.com/xuwujing/p/10933082.html
 * @ClassName: DefaultExceptionHandler.java
 * @Description: 自定义异常处理器
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:04:13
 */
@RestControllerAdvice
public class DefaultExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	/**
	 * Bad Request
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public ResultBody handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return new ResultBody.Builder(ResultCode.BAD_REQUEST).message(e.getMessage()).build();
	}

	/**
	 * sql异常
	 */
	@ExceptionHandler(SQLException.class)  
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
    public ResultBody handleSQLException(SQLException e) {
		logger.error("sql:",e);
        return new ResultBody.Builder(ResultCode.INTERNAL_SERVER_ERROR).message(e.getMessage()).build();
    }  

//	/**
//	 * token验证
//	 */
//	@ExceptionHandler(TokenException.class)
//	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//	public ResultBody handleTokenException(TokenException e) {
//		return new ResultBody.Builder(ResultCode.UNAUTHORIZED).message(e.getMessage()).build();
//	}
//
//	/**
//	 * permission验证
//	 */
//	@ExceptionHandler(PermissionException.class)
//	@ResponseStatus(value = HttpStatus.FORBIDDEN)
//	public ResultBody handlePermissionException(PermissionException e) {
//		return Message.error(403, e.getMessage());
//	}
	
	/**
	 * Media Type
	 */
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ResultBody handleHttpMediaTypeNotSupportedException(Exception e) {
		return new ResultBody.Builder(ResultCode.UNSUPPORTED_MEDIA_TYPE).message(e.getMessage()).build();
	}
	
//	/**
//	 * httpclient请求异常
//	 */
//	@ExceptionHandler(HttpClientException.class)
//	public ResultBody handleHttpClientException(HttpClientException e,HttpServletResponse response) {
//		response.setStatus(506);
//		return Message.error(506, e.getMessage());
//	}
//
//
//	/**
//	 * 业务异常
//	 */
//	@ExceptionHandler(BussinessException.class)
//	public ResultBody handleBussinessException(BussinessException e,HttpServletResponse response){
//		logger.info("{}:{}:{}",e.getCode(),e.getUri(),e.getMessage());
//		response.setStatus(e.getCode());
//		return Message.error(e.getCode(), e.getMessage());
//	}
	
	/**
	 * 拦截未知的运行时异常
	 */
	@ExceptionHandler(RuntimeException.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultBody handleRuntime(Exception e) {
		return new ResultBody.Builder(ResultCode.INTERNAL_SERVER_ERROR).message(e.getMessage()).build();
	}

	/**
	 * 系统异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	public ResultBody handleException(Exception e) {
		logger.error("error:",e);
		return new ResultBody.Builder(ResultCode.ERROR).message(e.getMessage()).build();
	}

}
