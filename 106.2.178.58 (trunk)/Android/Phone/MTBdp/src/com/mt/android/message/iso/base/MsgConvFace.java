package com.mt.android.message.iso.base;

import com.mt.android.message.iso.exception.MsgConvException;

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
public interface MsgConvFace {
    public void Clear( );
    public void MsgToIso( Object msgData ) throws MsgConvException;
    public Object IsoToMsg( ) throws MsgConvException;
    public String GetFld( int iFldNo ) throws MsgConvException;
    public void SetFld( int iFldNo, String Value ) throws MsgConvException;
    public Object GetOrigiMsgFld( int iFldNo );
}
