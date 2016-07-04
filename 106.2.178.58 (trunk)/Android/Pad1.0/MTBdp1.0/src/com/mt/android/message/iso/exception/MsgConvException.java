package com.mt.android.message.iso.exception;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MsgConvException extends Exception{
    public MsgConvException() {
    }

    public MsgConvException( String message ) {
        super( message );
    }

    public MsgConvException( String message, Throwable cause ) {
        super( message, cause );
    }

    public MsgConvException( Throwable cause ) {
        super( cause );
    }
}
