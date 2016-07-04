package com.mt.app.padpayment.tools;

import android.util.Log;

public class ProjectPublicObject {
	
	public String projectName="PadPayment";//ÏîÄ¿Ãû³Æ
	/**
	 * 
	 * @param className 
	 * @param methodName
	 * @param appDescription
	 */
	public void sysLogOutputBegin(String className,String methodName,String appDescription)
	{
		Log.i(projectName, "");
		Log.i(projectName, "-----------------Begin-------------------");
		Log.i(projectName, "--------"+className+"."+methodName+"--------------");
		Log.i(projectName, "--------"+appDescription);
		Log.i(projectName, "---------------------------------------------------");
	}
	
	public void sysLogOutputEnd(String className,String methodName,String appDescription)
	{
		Log.i(projectName, "---------------------------------------------------");
		Log.i(projectName, "--------"+className+"."+methodName+"--------------");
		Log.i(projectName, "-----------------End-------------------------------");
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	

}
