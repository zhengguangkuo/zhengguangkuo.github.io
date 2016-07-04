package com.wizarpos.mt;

import com.wizarpos.apidemo.jniinterface.ContactlessEvent;
import com.wizarpos.apidemo.jniinterface.ContactlessInterface;

import android.os.Handler;
import android.os.Message;

public class CardReaderHandle {
   private boolean bFlg = false;
   protected Handler handler = null;
   private PollThread thread= null;
   /**
    * ¥Úø™∂¡ø®∆˜…Ë±∏
    * @return
    */
   public boolean openDevice(){
	   
	   if(ContactlessInterface.Open() >= 0){
		    byte arryData[] = new byte[1];
			arryData[0] = 0x00;				//close RF(radio frequency)
			ContactlessInterface.SendControlCommand(ContactlessInterface.RC500_COMMON_CMD_RF_CONTROL, arryData, 1);
			
			arryData[0] = 0x01;				//open RF(radio frequency)
			ContactlessInterface.SendControlCommand(ContactlessInterface.RC500_COMMON_CMD_RF_CONTROL, arryData, 1);
			
			int result = ContactlessInterface.SearchTargetBegin(ContactlessInterface.CONTACTLESS_CARD_MODE_AUTO,	1, -1);

			if(result > 0){
			  return true;
			}
		   
	   }
	   
	   return false;
   }
   /**
    * ø™ º—∞ø®,»Áπ˚∂¡µΩÀ¢ø®,“Ï≤Ω∑Ω ΩÕ®÷™handler
    * @param handler
    * @return
    */
   public boolean startRead(Handler handler){
	   this.handler = handler;
	   bFlg = true;
	   thread = new PollThread();
	   thread.start();
	   return true;
   }
   
   /**
    * Õ£÷π∂¡ø®
    * @return
    */
   public boolean stopRead(){
	   bFlg = false;
	   thread.interrupt();
	   return true;
   }
   /**
    * πÿ±’∂¡ø®∆˜
    * @return
    */
   public boolean closeDevice(){
	   if(ContactlessInterface.Close() >=0 ){
		   return true;
	   }
	   
	   return false;
   }
   
   public void reinit(){
	    byte arryData[] = new byte[1];
		arryData[0] = 0x00;				//close RF(radio frequency)
		ContactlessInterface.SendControlCommand(ContactlessInterface.RC500_COMMON_CMD_RF_CONTROL, arryData, 1);
		
		arryData[0] = 0x01;				//open RF(radio frequency)
		ContactlessInterface.SendControlCommand(ContactlessInterface.RC500_COMMON_CMD_RF_CONTROL, arryData, 1);
		
		int result = ContactlessInterface.SearchTargetBegin(ContactlessInterface.CONTACTLESS_CARD_MODE_AUTO,	1, -1);

   }
   class PollThread extends Thread{
	    public void run(){
	    	try{
	    		while(bFlg){
		    		int result = -1;
					ContactlessEvent event = new ContactlessEvent();
					//Poll
					result = ContactlessInterface.PollEvent(-1, event);
					
					if(result >= 0){
						
						//Attach
						byte arryATR[] = new byte[255];
						result = ContactlessInterface.AttachTarget(arryATR);
						
						//Tranmit
						byte[] apduCommand = new byte[] { (byte) 0x00,
								(byte) 0xB0, (byte) 0x84, (byte) 0x00,
								(byte) 0x00 };//∂¡»°ø®∫≈√¸¡Ó
						byte[] apduResponse = new byte[255];
						result = ContactlessInterface.Transmit(
								apduCommand, apduCommand.length,
								apduResponse);
						
						/*byte[] apduCommand1 = new byte[] { (byte) 0x00,
								(byte) 0xA4, (byte) 0x00, (byte) 0x00,
								(byte) 0x02 , (byte) 0x10 , (byte) 0x01 };//∂¡»°ø®∫≈√¸¡Ó
						byte[] apduResponse1 = new byte[255];
						result = ContactlessInterface.Transmit(
								apduCommand1, apduCommand1.length,
								apduResponse1);
						
						byte[] apduCommand2 = new byte[] { (byte) 0x00,
								(byte) 0xA4, (byte) 0x00, (byte) 0x00,
								(byte) 0x02 , (byte) 0x00 , (byte) 0x04};//∂¡»°ø®∫≈√¸¡Ó
						byte[] apduResponse2 = new byte[255];
						result = ContactlessInterface.Transmit(
								apduCommand2, apduCommand2.length,
								apduResponse2);
						
						byte[] apduCommand = new byte[] { (byte) 0x00,
								(byte) 0xB0, (byte) 0x00, (byte) 0x00,
								(byte) 0x00 };//∂¡»°ø®∫≈√¸¡Ó
						byte[] apduResponse = new byte[255];
						result = ContactlessInterface.Transmit(
								apduCommand, apduCommand.length,
								apduResponse);*/
						
						if(result < 0){
							;
						}else{
							String strDisplay = new String();
							for (int i = 0; i < result; i++)
								strDisplay += String.format("%02X",
										apduResponse[i]);
							
							String cardno = strDisplay;
							cardno = cardno.substring(0, 16);
							Message msg = new Message();
							msg.what = 0;
							msg.obj = cardno;
							
							handler.sendMessage(msg);
						}
						ContactlessInterface.DetachTarget();
						ContactlessInterface.SearchTargetEnd();

						reinit();
					}
					Thread.sleep(300);
		    	}
	    	}catch (InterruptedException iex) {
				
			}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    	
	    }
   }
   
}
