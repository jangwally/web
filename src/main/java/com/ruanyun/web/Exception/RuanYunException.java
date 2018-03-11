package com.ruanyun.web.Exception;

public class RuanYunException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RuanYunException()  {
		
	}                
    public RuanYunException(String message) {      
        super(message);                           
    }
}
