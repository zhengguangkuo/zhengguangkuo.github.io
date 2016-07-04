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

		// Ѱ���ɹ�
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
				// cardTypeStr = "S50"; //��ѡһ
				break;
			case S70_PRO_CARD:
				cardTypeStr = "PRO";
				// cardTypeStr = "S70"; //��ѡһ
				break;
			default:
				cardTypeStr = "UNKNOWN";
				Log.i(TAG_LOG, "δ֪��!!!Ѱ�ǽӴ�ʽIC��ʧ��");
				return;
			}

//			Log.i(TAG_LOG, "Ѱ���ɹ�:::" + (rfCpuDriver == null) + ",cardType == " + cardType + ", cardTypeStr" + cardTypeStr);
			startRFCardOperation(cardTypeStr);
//			Log.i(TAG_LOG, "2222-------:" + Thread.currentThread().getId()) ;
		}

		// �������
		@Override
		public void onCrash() {
			Log.i(TAG_LOG, "�������");
			onServiceException();
		}

		// Ѱ��ʧ��
		@Override
		public void onFail(int code) {
			Log.i(TAG_LOG, "Ѱ��ʧ��.......");
			Log.i(TAG_LOG, "Ѱ�ǽӴ�ʽIC��ʧ��,������� : " + code + " [" + getErrorDescription(code) + "]");
//			searchCard();
		}

		String getErrorDescription(int code) {
			switch (code) {
			case ERROR_CARDNOACT:
				return "Pro������TypeB��δ���� ";
			case ERROR_CARDTIMEOUT:
				return "��ʱ����Ӧ ";
			case ERROR_PROTERR:
				return "��Ƭ�������ݲ����Ϲ淶Ҫ��";
			case ERROR_TRANSERR:
				return "ͨ�Ŵ��� ";
			}
			return "δ֪����";
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
		// �ǳ��豸���񣬴�ʱ��������ִ�е��豸�������ᱻ��ϣ�����δ�õ�֪ͨ��listenerҲ���ᱻ֪ͨ
		DeviceService.logout();
		return true;
	}

	@Override
	public void reinit() {

	}

	/**
	 * �����쳣����
	 */
	public void onServiceException() {
		Log.i(TAG_LOG, "�豸�����쳣���������������Ժ�����!");
		login();
	}

	/**
	 * ��¼���豸����
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
	 * ��ʼ�ǽӿ�����
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
					// ������д���˾Ͳ��ᷢ��
					Log.i(TAG_LOG, "����ʧ��!�ǽӿ�����ʧ��");
				}

				@Override
				public void onCardActivate(RFDriver cardDriver) {
					Log.i(TAG_LOG, "������ɹ������ڽ��п�����...");

					if (cardDriver instanceof RFCpuCardDriver) {
						Log.i(TAG_LOG, "3333-------:" + Thread.currentThread().getId()) ;
						rfIcCardExchange((RFCpuCardDriver) cardDriver); // �ǽ�cpu������
					} else if (cardDriver instanceof MifareDriver) {
						// doMifareOperation((MifareDriver) cardDriver); //
						// Mifare������
					}
				}

				@Override
				public void onActivateError(int code) {
					Log.i(TAG_LOG, "�ǽӿ�����ʧ�ܣ�������� : " + code + " [" + getErrorDescription(code) + "]");
				}

				String getErrorDescription(int code) {
					Log.i(TAG_LOG, "getErrorDescription()");
					switch (code) {
					case ERROR_TRANSERR:
						return "ͨ�Ŵ���";
					case ERROR_PROTERR:
						return "��Ƭ�������ݲ����Ϲ淶Ҫ��";
					case ERROR_CARDTIMEOUT:
						return "��ʱ����Ӧ";
					}
					return "δ֪����";
				}
			});
		} catch (ReqeustException e) {
			Log.i(TAG_LOG, "ReqeustException");
			e.printStackTrace();
			onServiceException();
		}
	}

	/**
	 * �ǽ�cpu������
	 * 
	 * @param cpuCardDriver
	 */
	private void rfIcCardExchange(final RFCpuCardDriver cpuCardDriver) {
		try {
			rfCpuDriver = cpuCardDriver;
			// ѡ��MF
			cpuCardDriver.exchangeApdu(new byte[] { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3F, 0x00, 0x00 }, new RFCpuCardDriver.OnExchangeListener() {
				private int cmdId = 0;

				byte[] b1 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x10, (byte) 0x01 };
				byte[] b2 = new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04 };
				byte[] b3 = new byte[] { (byte) 0x00, (byte) 0xb0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

				@Override
				public void onSuccess(byte[] responseData) {
					try {
						// ͬһ��listener��ʹ�ö��
						cmdId++;
						switch (cmdId) {
						case 1:
							// ѡ��DF
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

							Log.i(TAG_LOG, "�ǽӿ������ɹ���    select DF�ɹ��������ǣ�" + strDisplay.substring(0, 16));
							
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
//					Log.i(TAG_LOG, "�ǽ�CPU������ʧ�ܣ�������� : " + code + " [" + getErrorDescription(code) + "]");
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
						return "��������";
					case ERROR_FAILED:
						return "��������ϵͳ����ȣ�";
					case ERROR_NOTAGERR:
						return "������Χ���޿����߿�Ƭδ��Ӧ";
					case ERROR_CRCERR:
						return "����CRCУ�����";
					case ERROR_AUTHERR:
						return "��Ƭ��֤ʧ��";
					case ERROR_PARITYERR:
						return "������żУ�����";
					case ERROR_CODEERR:
						return "��Ƭ�����������ݴ���";
					case ERROR_SERNRERR:
						return "����ͻ���������ݴ���";
					case ERROR_NOTAUTHERR:
						return "��Ƭδ��֤";
					case ERROR_BITCOUNTERR:
						return "��Ƭ��������λ������";
					case ERROR_BYTECOUNTERR:
						return "��Ƭ���������ֽ�������";
					case ERROR_OVFLERR:
						return "��Ƭ�����������";
					case ERROR_FRAMINGERR:
						return "����֡����";
					case ERROR_UNKNOWN_COMMAND:
						return "�豸���ͷǷ�����";
					case ERROR_COLLERR:
						return "���ſ�Ƭ��ͻ";
					case ERROR_RESETERR:
						return "��Ƶ��ģ�鸴λʧ��";
					case ERROR_INTERFACEERR:
						return "��Ƶ��ģ��ӿڴ���";
					case ERROR_RECBUF_OVERFLOW:
						return "��Ż������ݵĽ��ջ��������";
					case ERROR_VALERR:
						return "��Mifare��������ֵ�����ʱ����ֵ��ֵ����";
					case ERROR_ERRTYPE:
						return "����Ŀ�����";
					case ERROR_SWDIFF:
						return "��MifarePro����TypeB�������ݽ���ʱ����Ƭ����״̬�ֽ�SW1!= 0x90��SW2!=0x00��";
					case ERROR_TRANSERR:
						return "ͨ�Ŵ���";
					case ERROR_PROTERR:
						return "��Ƭ�������ݲ����Ϲ淶Ҫ��";
					case ERROR_MULTIERR:
						return "��Ӧ���ڶ࿨����";
					case ERROR_NOCARD:
						return "��Ӧ�����޿�";
					case ERROR_CARDEXIST:
						return "�������ڸ�Ӧ��";
					case ERROR_CARDTIMEOUT:
						return "��ʱ����Ӧ";
					case ERROR_CARDNOACT:
						return "Pro������TypeB��δ����";
					}
					return "δ֪����";
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
			RFCardReader.getInstance().searchCard(searchListener); // Ѱ������ʱ�û��ɻӿ�	
		} catch (ReqeustException e) {
//			Log.i(TAG_LOG, "ReqeustException 4444");
			e.printStackTrace();
			onServiceException();
		}
	}

}
