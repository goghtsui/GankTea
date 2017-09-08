package com.hiveview.cloudscreen.vipvideo.service.exception;

/**
 * 接口异常
 * 
 * @author 陈丽晓
 * 
 */
public class HiveviewException extends BaseException {

	/**
	 * @Fields serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 9051625835652534061L;

	public HiveviewException() {
		super();
	}

	public HiveviewException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public HiveviewException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
