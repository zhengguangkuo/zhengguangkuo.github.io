package com.mt.android.view.common;

import com.mt.android.sys.mvc.command.CommandExecutor;

public final class CommandID extends BaseCommandID {
	public CommandID() {
		registerBaseCommand();
		registerCommand();
	}

	@Override
	public void registerCommand() {
		// ��¼����
		map.put("LOGIN_DO", CommandExecutor.IDistributer());
		map.put("TEST", CommandExecutor.IDistributer());
		map.put("PAYSUCCESS", CommandExecutor.IDistributer());
		map.put("APPBINDPWD", CommandExecutor.IDistributer());
		map.put("APPPAYSUCCESS", CommandExecutor.IDistributer());
		map.put("VOUCHERS", CommandExecutor.IDistributer());
		map.put("ORIGINALDEAL", CommandExecutor.IDistributer());
		map.put("ADMINPWD", CommandExecutor.IDistributer());
		map.put("CANCELRESULT", CommandExecutor.IDistributer());
		map.put("APPSELECT", CommandExecutor.IDistributer());
		map.put("BALANCE", CommandExecutor.IDistributer());
		map.put("FLASH_OVER", CommandExecutor.IDistributer());
		map.put("TO_MAIN", CommandExecutor.IDistributer());
		map.put("TO_PAYMENT", CommandExecutor.IDistributer());
		map.put("TO_SWIP_CARD", CommandExecutor.IDistributer());
		map.put("TO_WAIT", CommandExecutor.IDistributer());
		map.put("TO_SURE_CONSUME", CommandExecutor.IDistributer());
		map.put("TO_SCORE", CommandExecutor.IDistributer());
		map.put("TO_CANCEL_RESULT", CommandExecutor.IDistributer());
		map.put("QUERY_BACK", CommandExecutor.IDistributer());
		map.put("TO_ADMINPW", CommandExecutor.IDistributer());
		map.put("ToConsumeRecord", CommandExecutor.IDistributer());
		// �ύˢ������
		map.put("SubmitCard", CommandExecutor.IDistributer());
		// ����ȷ��ҳ�� ȷ������
		map.put("SureConsume", CommandExecutor.IDistributer());
		// ��������
		map.put("CreditsConsumeSubmit", CommandExecutor.IDistributer());

		/*----------------19-24 start------------------------*/
		// �ύ�Ż�ȯ��Ϣ
		map.put("CouponsInfoSubmit", CommandExecutor.IDistributer());
		// �Ż�ȯ�·��󷵻�
		map.put("CouponsSuccReturn", CommandExecutor.IDistributer());
		// ��ת���������ѽ���
		map.put("CreditsConsume", CommandExecutor.IDistributer());
		// ��ת��ԭ����ƾ�ο����������
		map.put("ToInputFlowNum", CommandExecutor.IDistributer());
		// ��ת���˻�����������
		map.put("FlowNumSubmit", CommandExecutor.IDistributer());
		// ��ѯ������Ϣ
		map.put("CreditsQuery", CommandExecutor.IDistributer());
		//��ת����������ҳ��
		map.put("TO_SETTING", CommandExecutor.IDistributer());
		//��������
		map.put("SETTING", CommandExecutor.IDistributer());
		//��ת���û����ҳ��
		map.put("TO_ADDADMIN", CommandExecutor.IDistributer());
		//�û����
		map.put("ADDADMIN", CommandExecutor.IDistributer());
		//��ת����Ա���������
		map.put("TO_INPUTADMIN", CommandExecutor.IDistributer());
		//�����Ա�ź󵽸��¹�Ա��Ϣҳ��
		map.put("SubmitUser", CommandExecutor.IDistributer());
		//�޸Ĺ�Ա��Ϣ
		map.put("UPDATEADMIN", CommandExecutor.IDistributer());
		//��ת����Ա�������˵�
		map.put("TO_ADMINMAIN", CommandExecutor.IDistributer());
		/*----------------19-24 end------------------------*/
		map.put("TO_SIGN_IN", CommandExecutor.IDistributer());
		map.put("TO_SIGN_OUT", CommandExecutor.IDistributer());
		map.put("SEARCH_COMMAND", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_MAIN", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_SUM", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_LOCK", CommandExecutor.IDistributer());
		map.put("TO_MANAGE_VERSION", CommandExecutor.IDistributer());
		map.put("TO_PAYMENTQUERY", CommandExecutor.IDistributer());
		map.put("TO_PAYMENTQUERYRESULT", CommandExecutor.IDistributer());
		map.put("TO_AdminSetPassWordActivity", CommandExecutor.IDistributer());
		map.put("TO_AdminResetPassWordActivity", CommandExecutor.IDistributer());

		map.put("TO_AdminSetPassWordRuActivity", CommandExecutor.IDistributer());
		map.put("TO_AdminResetPassWordRuActivity", CommandExecutor.IDistributer());
		map.put("TO_DELETE_ADMIN", CommandExecutor.IDistributer());//��Աɾ����ťcommand
		map.put("TO_DELETE_ADMIN_LIST", CommandExecutor.IDistributer());
		
		map.put("TO_QUERYADMIN", CommandExecutor.IDistributer());
		map.put("LOCKED_OVER", CommandExecutor.IDistributer());
		map.put("TO_ANDMINSIGN_OUT", CommandExecutor.IDistributer());
		map.put("TO_ORIGINALDATE", CommandExecutor.IDistributer());
		map.put("CouponsList",CommandExecutor.IDistributer());//�Ż�ȯ�б��ѯ
		map.put("FirstLogin", CommandExecutor.IDistributer());
		map.put("ToAdminPwdCommand", CommandExecutor.IDistributer());//��ת�����������������
		map.put("ToAdminCommand", CommandExecutor.IDistributer());// ��ת����Ա�������
		map.put("CLEARING",CommandExecutor.IDistributer());//����command
		map.put("ToAppPassword",CommandExecutor.IDistributer());//Ӧ����������
		map.put("TO_REPRINT_LISTTIP", CommandExecutor.IDistributer());//���´�ӡǩ����
	}

}
