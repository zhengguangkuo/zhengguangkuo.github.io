package com.mt.android.frame.smart.init;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.content.Context;

import com.mt.android.frame.smart.config.ComponentProperty;
import com.mt.android.frame.smart.config.DialogConfigs;
import com.mt.android.frame.smart.config.RegisterId;

public class BaseDialogManager {
	protected Map<String, DialogConfigs> dialogcfgs = new HashMap<String, DialogConfigs>();
	protected Map<String, String> componentids = new HashMap<String, String>();
	protected int idsequences = 1000;

	public Map<String, DialogConfigs> getDialogcfgs() {
		return dialogcfgs;
	}

	public void setDialogcfgs(Map<String, DialogConfigs> dialogcfgs) {
		this.dialogcfgs = dialogcfgs;
	}

	public Map<String, String> getComponentids() {
		return componentids;
	}

	public void setComponentids(Map<String, String> componentids) {
		this.componentids = componentids;
	}

	public int getIdsequences() {
		return idsequences;
	}

	public void setIdsequences(int idsequences) {
		this.idsequences = idsequences;
	}

	public BaseDialogManager(Context context) {
		loadconfigs(context);
	}

	public void loadconfigs(Context context) {
		try {
			SAXReader reader = new SAXReader();
			InputStream in = context.getAssets().open("file/Dialog.xml");

			Document d = reader.read(in);

			Element root = d.getRootElement();
			List rootchilds = root.elements();

			if (rootchilds.size() > 0) {
				for (Iterator it_root = rootchilds.iterator(); it_root.hasNext();) {
					Element dialogele = (Element) it_root.next();
					if (dialogele.getName().toUpperCase().equals("DIALOG")) {
						DialogConfigs dialog = new DialogConfigs();
						List elements = dialogele.elements();

						for (Iterator it = elements.iterator(); it.hasNext();) {
							Element e = (Element) it.next();
							String eName = e.getName().toUpperCase();

							if (eName.equals("UNIQUEMARK")) {
								dialog.setUniquemark(e.getStringValue());
							}

							if (eName.equals("BACKGROUND")) {
								dialog.setBackground(e.getStringValue());
							}

							if (eName.equals("COMPONENT")) {
								List<ComponentProperty> component = new ArrayList<ComponentProperty>();
								List echilds = e.elements();

								for (Iterator it_com = echilds.iterator(); it_com.hasNext();) {
									Element eChild = (Element) it_com.next();
									ComponentProperty content = new ComponentProperty();

									String eChildName = eChild.getName().toUpperCase();
									content.setType(eChildName);

									List childList = eChild.elements();

									for (Iterator it_con = childList.iterator(); it_con.hasNext();) {
										Element childs = (Element) it_con.next();
										String childsName = childs.getName().toUpperCase();
										setVal(content, childsName, childs.getStringValue());
									}
									component.add(content);
								}
								dialog.setComponent(component);
							}

						}
						dialogcfgs.put(dialog.getUniquemark(), dialog);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public synchronized int regitIdForName(String idName) {
		int componentid = idsequences++;
		componentids.put(idName, componentid + "");

		return componentid;
	}

	protected void setVal(Object obj, String fieldname, String fieldval) {
		Class cla = obj.getClass();
		Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组
		Method method = null;
		String methodName = null;

		if (fieldname != null && fieldname.equalsIgnoreCase("id")) {
			RegisterId.registerId(fieldval);
		}
		for (int i = 0; i < ma.length; i++) {
			method = ma[i];
			methodName = method.getName();// 方法名
			if (methodName.indexOf("set") == 0 && methodName.substring(3).toUpperCase().equalsIgnoreCase(fieldname)) {// 以get开始的方法，排除set方法
				try {
					method.invoke(obj, fieldval);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
