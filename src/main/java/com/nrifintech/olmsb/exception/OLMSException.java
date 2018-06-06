/*
 * +=============================================================================================+
 * |                                                                                             |
 * |                                Copyright (C) 2013-2018                                      |
 * |            Nomura Research Institute Financial Technologies India Pvt. Ltd.                 |
 * |                                  All Rights Reserved                                        |
 * |                                                                                             |
 * |  This document is the sole property of Nomura Research Institute Financial Technologies     |
 * |  India Pvt. Ltd. No part of this document may be reproduced in any form or by any           |
 * |  means - electronic, mechanical, photocopying, recording or otherwise - without the prior   |
 * |  written permission of Nomura Research Institute Financial Technologies India Pvt. Ltd.     |
 * |                                                                                             |
 * |  Unless required by applicable law or agreed to in writing, software distributed under      |
 * |  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF        |
 * |  ANY KIND, either express or implied.                                                       |
 * |                                                                                             |
 * +=============================================================================================+
 */
package com.nrifintech.olmsb.exception;

/**
 * Root of the checked exception hierarchy in OLMS. All the checked
 * exceptions of the application shall extend from this class.
 */
public class OLMSException extends Exception {

	
	//~ Class Attributes -------------------------------------------------------
	
    /**
	 * The <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = -2666265946231745520L;
	
	//~ Constructors -----------------------------------------------------------

	/**
     * Construct a default instance.
     */
    public OLMSException() {
        super();
    }

    /**
     * Construct an instance with specified message.
     *
     * @param   message the given message.
     */
    public OLMSException(String message) {
        super(message);
    }
    
    /**
     * Construct an instance with specified cause.
     *
     * @param   cause   the cause of this exception.
     */
    public OLMSException(Throwable cause) {
        super(cause);
    }


    /**
     * Construct an instance with specified message and cause.
     *
     * @param   message the given message.
     * @param   cause   the cause of this exception.
     */
    public OLMSException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Construct an instance with specified message, error code and cause.
     *
     * @param   message the given message.
     * @param	code	the error code
     * @param   cause   the cause of this exception.
     */
    public OLMSException(String message, String code, Throwable cause) {
        super(message, cause);
    }
    

    /*
     * Default implementation of toString() appends the class name with the message irrespective of whether there is value
     * returned by getLocalizedMessage(). However when getLocalizedMessage() has value then class name will not be appended 
     * with the exception message. 
     */
    @Override
    public String toString() {
        String s = "Unknown Error";
        String message = getLocalizedMessage();
        return (message != null) ? (message) : s;
    }


}
