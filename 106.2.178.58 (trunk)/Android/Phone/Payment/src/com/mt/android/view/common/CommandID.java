package com.mt.android.view.common;

import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.controller.Controller;

public final class CommandID extends BaseCommandID
{
	public CommandID(){
		registerBaseCommand();
		registerCommand();
	}
	@Override
	public void registerCommand() {
//		// ��¼����
//		map.put("LOGIN_DO",CommandExecutor.IDistributer());
//		// ���ò˵�
//		map.put("SETMENU",CommandExecutor.IDistributer());
//		// ��������
//		map.put("SETPASSWORD",CommandExecutor.IDistributer());
//		// ���û���
//		map.put("SETCARD",CommandExecutor.IDistributer());
//		// �ҵĽ����е���˲鿴������Ϣ��ť��Ҫ������command
//		map.put("CHECK_BASE_CARD",CommandExecutor.IDistributer());
//		// �󶨻���
//		map.put("BINDCARD",CommandExecutor.IDistributer());
//		// �󶨳ɹ���Ϣ
//		map.put("BINDINFO",CommandExecutor.IDistributer());
//		// ��ת�������������
//		map.put("TO_SETPASSWORD",CommandExecutor.IDistributer());
//		// ��ת����¼ҳ��
//		map.put("TO_LOGIN",CommandExecutor.IDistributer());
//		// ��ת��ע��ҳ��
//		map.put("TO_REGISTER",CommandExecutor.IDistributer());
//		// ע������
//		map.put("REGISTER_DO",CommandExecutor.IDistributer());
//		// ��ת������ҳ��
//		map.put("TO_SETTING",CommandExecutor.IDistributer());
//		// �û���Ӧ�ú�Ҫ������command�����ڸ��µ�ǰҳ�棩
//		map.put("SURE_APP_BIND",CommandExecutor.IDistributer());
//		// �û����Ӧ�ú�Ҫ������command�����ڸ��½��棩
//		map.put("SURE_APP_UNBIND",CommandExecutor.IDistributer());
//		// �û�ѡ���ײͺ�Ҫ������command�����ڸ���ҳ�棩
//		map.put("SURE_MEAL_SELECT",CommandExecutor.IDistributer());
//		// �û�����ײͺ�Ҫ������command�����ڸ��½��棩��
//		map.put("SURE_MEAL_UNBIND",CommandExecutor.IDistributer());
//		// ���ý����е��ײ�ѡ��
//		map.put("SET_MEAL",CommandExecutor.IDistributer());
//		// ���ý����еİ�Ӧ��
//		map.put("SET_APP",CommandExecutor.IDistributer());
//		// ���ý����еĽ��Ӧ��
//		map.put("SET_UNAPP",CommandExecutor.IDistributer());
//		// ��ȡ��֤������
//		map.put("GET_VERIFICATION",CommandExecutor.IDistributer());
//		// ��¼���ȡ��֤��
//		map.put("LOGIN_VERIFICATION",CommandExecutor.IDistributer());
//		// ��ת���ҵĽ���
//		map.put("TO_MYVIEW",CommandExecutor.IDistributer());
//		// ��תδ��¼��ҳ
//		map.put("TO_NOLOGININDEX",CommandExecutor.IDistributer());
//		// ��ת����¼�����ҳ
//		map.put("TO_LOGININDEX",CommandExecutor.IDistributer());
//		// �󶨳ɹ����ٴ������ѯӦ���б�
//		map.put("SECOND_REQUEST_APP",CommandExecutor.IDistributer());
//		// ��¼���һ�ε������ʱ����Ҫ����������֤��Ľ���
//		map.put("CHECK_CODE_SETTING",CommandExecutor.IDistributer());
//		// �ύ��֤��ʱ��command
//		map.put("SUBMIT_CHECK_CODE",CommandExecutor.IDistributer());
//		// ��¼���ȡ��֤�������
//		map.put("GETCHECKCODE",CommandExecutor.IDistributer());
		
		
		//�����Ǳ�Ӧ���е�commandID
		
		map.put("POINTQUERY",CommandExecutor.IDistributer());
		map.put("PWDLOST",CommandExecutor.IDistributer());
		map.put("PACKAGESELECT",CommandExecutor.IDistributer());
		map.put("SELECTPACKAGE",CommandExecutor.IDistributer());
		map.put("TABAPPMANAGE",CommandExecutor.IDistributer());
		map.put("BASECARDSET",CommandExecutor.IDistributer());
		map.put("SKIPBASECARD",CommandExecutor.IDistributer());
		map.put("AUXILIARYFOUNCTION",CommandExecutor.IDistributer());
		map.put("APPMANAGE1",CommandExecutor.IDistributer());
		map.put("APPMANAGE2",CommandExecutor.IDistributer());
		map.put("APPMANAGE3",CommandExecutor.IDistributer());
		map.put("ToEleCard_Main", CommandExecutor.IDistributer());
		map.put("HOME", CommandExecutor.IDistributer());
		map.put("TO_Discount_01_MainList", CommandExecutor.IDistributer());
		map.put("TO_Discount_02_MainDetail", CommandExecutor.IDistributer());
		map.put("TO_Discount_03_MainBusiness", CommandExecutor.IDistributer());
		map.put("TO_Discount_04_MainDetail", CommandExecutor.IDistributer());
		
		map.put("MYACCOUNT", CommandExecutor.IDistributer());
		map.put("USER_LOGIN", CommandExecutor.IDistributer());//��תע��ҳ��
		map.put("USER_REGISTER", CommandExecutor.IDistributer());//��ת��¼ҳ��
		map.put("USER_FORGOTPASS", CommandExecutor.IDistributer());//��ת��������ҳ��
		map.put("EleApp_Query", CommandExecutor.IDistributer());  //���ӿ����е�Ӧ�ò�ѯ
		map.put("UserDoLogin", CommandExecutor.IDistributer()); //��¼����
		map.put("EleApp_unbind", CommandExecutor.IDistributer()); //���ӿ����е�Ӧ�ý��
		map.put("EleApp_Bind", CommandExecutor.IDistributer()); //���ӿ����е�Ӧ�ð�
		map.put("EleApp_Default", CommandExecutor.IDistributer()) ;	// ����Ӧ��ΪĬ��֧��Ӧ��
		map.put("UserDoRegister", CommandExecutor.IDistributer());//ע������
		map.put("EleDiscount_Query", CommandExecutor.IDistributer()); //���ӿ����������Ż�ȯ���б��ѯ
		map.put("EleDiscount_details", CommandExecutor.IDistributer());//���ӿ����������Ż�ȯ������
		map.put("HELP", CommandExecutor.IDistributer());
		map.put("ABOUT", CommandExecutor.IDistributer());		
		map.put("UserVerification",CommandExecutor.IDistributer());//ע��������֤��
		map.put("BINDCARD",CommandExecutor.IDistributer());
		map.put("UPDATECARD",CommandExecutor.IDistributer());
		map.put("POINTQUERY1",CommandExecutor.IDistributer());
		map.put("MYACCOUNTQUERY",CommandExecutor.IDistributer());
		map.put("BASECARDSTART",CommandExecutor.IDistributer());
		map.put("ElePayApp_details", CommandExecutor.IDistributer()); //���ӿ������Ѱ�֧��Ӧ�õ�����
		map.put("EleApp_PayAll_Query", CommandExecutor.IDistributer()); //��ѯ����֧����Ӧ�õ��б�
		map.put("ResetMyaccount", CommandExecutor.IDistributer());
		map.put("ResetMyaccountOfPhone", CommandExecutor.IDistributer());
		map.put("BaseCardSelect", CommandExecutor.IDistributer());
		map.put("DISCOUNT_DIS", CommandExecutor.IDistributer());
		map.put("Discount_dis_details", CommandExecutor.IDistributer());
		map.put("Discount_dis_receive", CommandExecutor.IDistributer());
		map.put("DISCOUNT_BUS", CommandExecutor.IDistributer());
		map.put("Discount_bus_details", CommandExecutor.IDistributer());
		map.put("Downloadbit", CommandExecutor.IDistributer());
		map.put("ResetMyaccountSubmit", CommandExecutor.IDistributer());
		map.put("MapSearchPoint", CommandExecutor.IDistributer());
		map.put("SearchNearBy_merchDiscount_Query", CommandExecutor.IDistributer());
		map.put("SearchNearBy_merchyouhui_Query", CommandExecutor.IDistributer());
		map.put("SelectMerchList", CommandExecutor.IDistributer());
		map.put("SeeMerchant", CommandExecutor.IDistributer());
		map.put("DiscountDisSearch",CommandExecutor.IDistributer());//�����Ż�ȯ�б�
		map.put("DiscountBusSearch",CommandExecutor.IDistributer());//�����ۿ��̼��б�
		map.put("RegCodeGet", CommandExecutor.IDistributer());
		map.put("RegCodeVerfi", CommandExecutor.IDistributer());
		map.put("BaseCardSelectTwo",CommandExecutor.IDistributer());//����ѡ�������ת��������Ϣ����
		map.put("CheckUpdate",CommandExecutor.IDistributer());//������
		map.put("UserDoForgetPwd",CommandExecutor.IDistributer());//��������
		map.put("EleDisDelete",CommandExecutor.IDistributer());//ɾ���Ż�ȯ
		map.put("GuideBASECARDSTART", CommandExecutor.IDistributer());
		map.put("GuidePACKAGESELECT", CommandExecutor.IDistributer());
		map.put("GuideBaseCardSelect",CommandExecutor.IDistributer());//����ѡ�������ת��������Ϣ����
		map.put("GuideBaseCardSelectTwo",CommandExecutor.IDistributer());//����ѡ�������ת��������Ϣ����
		map.put("Discount_dis_merchant_details", CommandExecutor.IDistributer());
		map.put("Goto_Discount_dis_merchant_details", CommandExecutor.IDistributer());
		map.put("ChangePhone", CommandExecutor.IDistributer()) ;
		map.put("Discount_bus_details_yacol", CommandExecutor.IDistributer()) ;
		map.put("Goto_Discount_dis_merchant_details_yacol", CommandExecutor.IDistributer()) ;
		map.put("SKIPBASECARDCOMMAND", CommandExecutor.IDistributer()) ;
		map.put("ADCOMMAND", CommandExecutor.IDistributer()) ;
		Controller.appmap = map;
	}
}
