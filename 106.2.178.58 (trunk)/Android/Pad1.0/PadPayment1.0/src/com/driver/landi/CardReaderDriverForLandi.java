package com.driver.landi;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.device.RFCardReader.OnActiveListener;
import com.landicorp.android.eptapi.device.RFCardReader.OnSearchListener;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.ReqeustException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;
import com.mt.app.padpayment.application.PadPaymentApplication;
import com.mt.app.padpayment.driver.CardReaderDriver;

public class CardReaderDriverForLandi extends CardReaderDriver {
	protected static final String TAG_LOG = CardReaderDriverForLandi.class.getSimpleName();
	private Handler mHandler;
	private Context ctx;
	private RFCpuCardDriver rfCpuDriver;
	private boolean mFlag = false;
	private ReadCardThread mCardThread;
	public static final String LOCK = "TMD";

	private OnSearchListener searchListener = new OnSearchListener() {

		// 寻卡成功
		@Override
		public void onCardPass(int cardType) {
			String cardTypeStr;
			switch (cardType) {
			case CPU_CARD:
			case PRO_CARD:
				cardTypeStr = "PRO";
				break;
			case S50_CARD:
				cardTypeStr = "S50";
				break;
			case S70_CARD:
				cardTypeStr = "S70";
				break;
			case S50_PRO_CARD:
				cardTypeStr = "PRO";
				// cardTypeStr = "S50"; //二选一
				break;
			case S70_PRO_CARD:
				cardTypeStr = "PRO";
				// cardTypeStr = "S70"; //二选一
				break;
			default:
				cardTypeStr = "UNKNOWN";
				Log.i(TAG_LOG, "未知卡!!!寻非接触式IC卡失败");
				return;
			}

//			Log.i(TAG_LOG, "寻卡成功:::" + (rfCpuDriver == null) + ",cardType == " + cardType + ", cardTypeStr" + cardTypeStr);
			startRFCardOperation(cardTypeStr);
//			Log.i(TAG_LOG, "2222-------:" + Thread.currentThread().getId()) ;
		}

		// 服务崩溃
		@Override
		public void onCrash() {
			Log.i(TAG_LOG, "服务崩溃");
			onServiceException();
		}

		// 寻卡失败
		@Override
		public void onFail(int code) {
			Log.i(TAG_LOG, "寻卡失败.......");
			Log.i(TAG_LOG, "寻非接触式IC卡失败,错误代码 : " + code + " [" + getErrorDescription(code) + "]");
//			searchCard();
		}

		String getErrorDescription(int code) {
			switch (code) {
			case ERROR_CARDNOACT:
				return "Pro卡或者TypeB卡未激活 ";
			case ERROR_CARDTIMEOUT:
				return "超时无响应 ";
			case ERROR_PROTERR:
				return "卡片返回数据不符合规范要求";
			case ERROR_TRANSERR:
				return "通信错误 ";
			}
			return "未知错误";
		}
	};

	@Override
	public boolean openDevice() {
		ctx = PadPaymentApplication.getContext() ;
		return false;
	}

	@Override
	public boolean startRead(Handler handler) {
		mHandler = handler;
		mFlag = true;
		login();
		mCardThread = new ReadCardThread();
		mCardThread.start();
		return false;
	}

	@Override
	public boolean stopRead() {
		mFlag = false;
		mCardThread.interrupt();
		return true;
	}

	@Override
	public boolean closeDevice() {
		// 登出设备服务，此时所有正在执行的设备操作都会被打断，所有未得到通知的listener也不会被通知
		DeviceService.logout();
		return true;
	}

	@Override
	public void reinit() {

	}

	/**
	 * 服务异常处理
	 */
	public void onServiceException() {
		Log.i(TAG_LOG, "设备服务异常，正在重启，请稍后重试!");
		login();
	}

	/**
	 * 登录到设备服务
	 */
	public void login() {
		try {
			DeviceService.login(ctx);
		} catch (ServiceOccupiedException e) {
			e.printStackTrace();
		} catch (ReloginException e) {
			e.printStackTrace();
		} catch (UnsupportMultiProcess e) {
			e.printStackTrace();
		} catch (ReqeustException e) {
			e.printStackTrace();
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					login();
				}
			}, 100);
		}
	}

	/**
	 * 开始非接卡操作
	 * 
	 * @param driverName
	 */
	private void startRFCardOperation(String driverName) {

		try {
			RFCardReader.getInstance().activate(driverName, new OnActiveListener() {

				@Override
				public void onCrash() {
					Log.i(TAG_LOG, "onCrash()");
					onServiceException();
				}

				@Override
				public void onUnsupport(String driverName) {
					// 驱动名写对了就不会发生
					Log.i(TAG_LOG, "操作失败!非接卡激活失败");
				}

				@Override
				public void onCardActivate(RFDriver cardDriver) {
					Log.i(TAG_LOG, "卡激活成功，正在进行卡交互...");

					if (cardDriver instanceof RFCpuCardDriver) {
						Log.i(TAG_LOG, "3333-------:" + Thread.currentThread().getId()) ;
						rfIcCardExchange((RFCpuCardDriver) cardDriver); // 非接cpu卡交互
					} else if (cardDriver instanceof MifareDriver) {
						// doMifareOperation((MifareDriver) cardDriver); //
						// Mifare卡操作
					}
				}

				@Override
				public void onActivateError(int code) {
					Log.i(TAG_LOG, "非接卡激活失败！错误代码 : " + code + " [" + getErrorDescription(code) + "]");
				}

				String getErrorDescription(int code) {
					Log.i(TAG_LOG, "getErrorDescription()");
					switch (code) {
					case ERROR_TRANSERR:
						return "通信错误";
					case ERROR_PROTERR:
						return "卡片返回数据不符合规范要求";
					case ERROR_CARDTIMEOUT:
						return "超时无响应";
					}
					return "未知错误";
				}
			});
		} catch (ReqeustException e) {
			Log.i(TAG_LOG, "ReqeustException");
			e.printStackTrace();
			onServiceException();
		}
	}

	/**
	 * 非接cpu卡交互
	 * 
	 * @param cpuCardDriver
	 */
	private void rfIcCardExchange(final RFCpuCardDriver cpuCardDriver) {
		try {
			rfCpuDriver = cpuCardDriver;
			// 选择MF
			cpuCardDriver.exchangeApdu(new byte[] { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3F, 0x00, 0x00 }, new RFCpuCardDriver.OnExchangeListener() {
				private int cmdId = 0;

				byte[] b1 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x10, (byte) 0x01 };
				byte[] b2 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04 };
				byte[] b3 = new byte[] { (byte) 0x00, (byte) 0xb0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

				@Override
				public void onSuccess(byte[] responseData) {
					try {
						// 同一个listener可使用多次
						cmdId++;
						switch (cmdId) {
						case 1:
							// 选择DF
							cpuCardDriver.exchangeApdu(b1, this);
							break;
						case 2:
							cpuCardDriver.exchangeApdu(b2, this);
							break;
						case 3:
							cpuCardDriver.exchangeApdu(b3, this);

							break;

						case 4:
							String strDisplay = new String();
							for (int i = 0; i < responseData.length; i++) {
								strDisplay += String.format("%02X", responseData[i]);
							}
							Log.i("XXXX", "strDisplay = " + strDisplay);
							Log.i("XXXX", "responseData = " + new String(responseData));

							Message msg = new Message();
							msg.what = 0;
							msg.obj = strDisplay.substring(0, 16);
							mHandler.sendMessage(msg);

							Log.i(TAG_LOG, "非接卡交互成功！    select DF成功！卡号是：" + strDisplay.substring(0, 16));
							
							rfCpuDriver.halt() ;	
							
//							Log.i(TAG_LOG, "4444-------:" + Thread.currentThread().getId()) ;
							break;
						}
					} catch (ReqeustException e) {
						Log.i(TAG_LOG, "ReqeustException 111");
						e.printStackTrace();
						onServiceException();
					}
				}

				@Override
				public void onFail(int code) {
//					Log.i(TAG_LOG, "非接CPU卡交互失败！错误代码 : " + code + " [" + getErrorDescription(code) + "]");
					try {
						cpuCardDriver.halt();
					} catch (ReqeustException e) {
						Log.i(TAG_LOG, "ReqeustException 222");
						e.printStackTrace();
						onServiceException();
					}
				}

				@Override
				public void onCrash() {
					Log.i(TAG_LOG, "onCrash()");
					onServiceException();
				}

				public String getErrorDescription(int code) {
					switch (code) {
					case ERROR_ERRPARAM:
						return "参数错误";
					case ERROR_FAILED:
						return "其他错误（系统错误等）";
					case ERROR_NOTAGERR:
						return "操作范围内无卡或者卡片未响应";
					case ERROR_CRCERR:
						return "数据CRC校验错误";
					case ERROR_AUTHERR:
						return "卡片认证失败";
					case ERROR_PARITYERR:
						return "数据奇偶校验错误";
					case ERROR_CODEERR:
						return "卡片回送数据内容错误";
					case ERROR_SERNRERR:
						return "防冲突过程中数据错误";
					case ERROR_NOTAUTHERR:
						return "卡片未认证";
					case ERROR_BITCOUNTERR:
						return "卡片回送数据位数错误";
					case ERROR_BYTECOUNTERR:
						return "卡片回送数据字节数错误";
					case ERROR_OVFLERR:
						return "卡片回送数据溢出";
					case ERROR_FRAMINGERR:
						return "数据帧错误";
					case ERROR_UNKNOWN_COMMAND:
						return "设备发送非法命令";
					case ERROR_COLLERR:
						return "多张卡片冲突";
					case ERROR_RESETERR:
						return "射频卡模块复位失败";
					case ERROR_INTERFACEERR:
						return "射频卡模块接口错误";
					case ERROR_RECBUF_OVERFLOW:
						return "存放回送数据的接收缓冲区溢出";
					case ERROR_VALERR:
						return "对Mifare卡进行数值块操作时，数值块值错误";
					case ERROR_ERRTYPE:
						return "错误的卡类型";
					case ERROR_SWDIFF:
						return "与MifarePro或者TypeB进行数据交换时，卡片回送状态字节SW1!= 0x90，SW2!=0x00。";
					case ERROR_TRANSERR:
						return "通信错误";
					case ERROR_PROTERR:
						return "卡片返回数据不符合规范要求";
					case ERROR_MULTIERR:
						return "感应区内多卡存在";
					case ERROR_NOCARD:
						return "感应区内无卡";
					case ERROR_CARDEXIST:
						return "卡在仍在感应区";
					case ERROR_CARDTIMEOUT:
						return "超时无响应";
					case ERROR_CARDNOACT:
						return "Pro卡或者TypeB卡未激活";
					}
					return "未知错误";
				}
			});
		} catch (ReqeustException e) {
			Log.i(TAG_LOG, "ReqeustException 333");
			e.printStackTrace();
			onServiceException();
		}
	}

	class ReadCardThread extends Thread {
		@Override
		public void run() {
			while (mFlag) {
				searchCard();	
				try {
					sleep(1000) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void searchCard() {
		try {
//			Log.i(TAG_LOG, "1111-------:" + Thread.currentThread().getId()) ;
			RFCardReader.getInstance().searchCard(searchListener); // 寻卡，此时用户可挥卡	
		} catch (ReqeustException e) {
//			Log.i(TAG_LOG, "ReqeustException 4444");
			e.printStackTrace();
			onServiceException();
		}
	}

}
