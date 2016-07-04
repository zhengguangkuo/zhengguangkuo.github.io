package com.mt.android.view.form;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.mt.android.frame.listview.AdapterRegist;
import com.mt.android.sys.util.StringFormat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.RadioButton;

public class BaseFormImpl implements IBaseForm {

	private List<View> viewList = new ArrayList<View>();

	@Override
	public boolean bean2Form(Object srcBean, View desView, Context context) {

		boolean bflg = false;

		try {
			// ��ȡ��ǰlayout�������е�Ҷ�Ӽ������Ԫ��
			List<View> viewList = getAllLeafElements(desView);
			// ��JavaBean���е�����ֵ�洢��map����
			Map<String, Object> beanMap = setBeanValToMap(srcBean);

			if(viewList != null){
				// ����ǰMap���е�������������ֵ�Ķ�Ӧ��ϵ���õ�View��Ӧ����Ԫ�ص���
				for (int i = 0; i < viewList.size(); i++) {
					View view = viewList.get(i);
					if (view.getContentDescription() != null) {
						if (view instanceof CheckBox) {// ��ѡ
							paddingForCheckBox((CheckBox) view, beanMap);
						} else if (view instanceof ToggleButton) {// ����
							paddingForToggleButton((ToggleButton) view, beanMap);
						} else if (view instanceof RadioButton) {// ��ѡ
							paddingForRadioButton(desView, (RadioButton) view,
									beanMap);
						} else if (view instanceof TextView) {// �ı���
							paddingForTextView((TextView) view, beanMap);
						} else if (view instanceof ListView) {// listView,���봫��context
							paddingForListView((ListView) view, beanMap, context);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			bflg = false;
		}
		// ��JavaBean���е�ֵ��䵽Map����

		return bflg;
	}

	@Override
	public boolean form2Bean(Object desBean, View srcView) {

		boolean bflg = true;

		try {
			List<View> viewList = getAllLeafElements(srcView);
			Map<String, Object> viewMap = setViewValToMap(viewList);
			if(viewList != null){
				for (int i = 0; i < viewList.size(); i++) {
					if (viewList.get(i).getContentDescription() != null) {
						String fieldName = viewList.get(i).getContentDescription()
								.toString().toUpperCase(Locale.getDefault());

						Object fieldVlaue = viewMap.get(fieldName);

						setObj(fieldName, fieldVlaue, desBean);
					}
				}
			}
		} catch (Exception ex) {
			bflg = false;
			ex.printStackTrace();
		}

		return bflg;
	}

	@Override
	public List<View> getAllLeafElements(View view) {
		if (view != null && view instanceof ListView) {// Ŀǰ��ദ������listview
			ListView lv = (ListView) view;
			if (lv.getChildAt(0) instanceof ViewGroup) {// ���list��Ԫ�ػ���list
				this.viewList.add(view);
				for (int i = 0; i < lv.getChildCount(); i++) {
					getAllLeafElements(lv.getChildAt(i));
				}
			} else {
				this.viewList.add(view);
			}
		} else if (view != null && view instanceof ViewGroup) {
			ViewGroup vGroup = (ViewGroup) view;
			for (int i = 0; i < vGroup.getChildCount(); i++) {
				getAllLeafElements(vGroup.getChildAt(i));
			}
		} else if (view != null && view instanceof RadioGroup) {
			RadioGroup rg = (RadioGroup) view;
			for (int i = 0; i < rg.getChildCount(); i++) {
				getAllLeafElements(rg.getChildAt(i));
			}
		} else {
			this.viewList.add(view);
		}

		return viewList;
	}

	@Override
	public Map<String, Object> setBeanValToMap(Object srcBean) {

		Map<String, Object> map = new HashMap<String, Object>();
		if(srcBean != null){
			Class<?> cla = srcBean.getClass();
			Method[] ma = cla.getDeclaredMethods();// ��ȡ���������ķ�������
			Method method = null;
			String methodName = null;

			try {
				for (int i = 0; i < ma.length; i++) {
					method = ma[i];
					methodName = method.getName();// ������

					if (methodName.indexOf("get") == 0) {// ��get��ʼ�ķ������ų�set����
						// ȡ�����Ե�������ΪKey
						String key = methodName.toUpperCase(Locale.getDefault())
								.substring(3);
						Object obj = method.invoke(srcBean);
						map.put(key, obj);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> setViewValToMap(List<View> viewList) {
		Map<String, Object> viewMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();// ����ѡ��ĸ������͵�ǰcheckBox��ע��������list�����ڴ�������
		Map<String, Integer> checkMap = new HashMap<String, Integer>();// һ����������ж��鸴ѡ������ÿ�鸴ѡ��ļ���

		if(viewList != null){
			for (int i = 0; i < viewList.size(); i++) {
				View view = (View) viewList.get(i);
				if (view instanceof CheckBox) {// ��ѡ
					if (((CheckBox) view).isChecked()) {
						String checkBoxName = view.getContentDescription()
								.toString();// ��ǰcheckBox��ע����
						String parentName = ((View) view.getParent())
								.getContentDescription().toString();// ��������ע����
						list.add(StringFormat.isNull(parentName).toUpperCase(
								Locale.getDefault())
								+ ","
								+ StringFormat.isNull(checkBoxName).toUpperCase(
										Locale.getDefault()));
						if (!checkMap.isEmpty()) {// ����Ѿ��и�ѡ��
							if (checkMap.get(StringFormat.isNull(parentName)
									.toUpperCase(Locale.getDefault())) != null) {// �����ǰ�����Ѿ����ڣ��������1
								checkMap.put(StringFormat.isNull(parentName)
										.toUpperCase(Locale.getDefault()),
										checkMap.get(StringFormat
												.isNull(parentName).toUpperCase(
														Locale.getDefault())) + 1);
							} else {// ���µĸ�ѡ�����������map��������1
								checkMap.put(StringFormat.isNull(parentName)
										.toUpperCase(Locale.getDefault()), 1);
							}
						} else {// ����һ����ѡ�����������map��������1
							checkMap.put(StringFormat.isNull(parentName)
									.toUpperCase(Locale.getDefault()), 1);
						}
					}
				} else if (view instanceof ToggleButton) {// ����
					if (((ToggleButton) view).isChecked()) {
						viewMap.put(
								StringFormat.isNull(view.getContentDescription())
										.toUpperCase(Locale.getDefault()), true);
					} else {
						viewMap.put(
								StringFormat.isNull(view.getContentDescription())
										.toUpperCase(Locale.getDefault()), false);
					}
				} else if (view instanceof RadioButton) {// ��ѡ,��ѡ�еĵ�ѡ���ע��������
															// ������Ϊradiogroup��ע����������
					if (((RadioButton) view).isChecked()) {
						String radioName = view.getContentDescription().toString();// ��ǰradiobutton��ע����
						String radioGroupName = ((RadioGroup) view.getParent())
								.getContentDescription().toString();// radiogroup��ע����
						viewMap.put(StringFormat.isNull(radioGroupName)
								.toUpperCase(Locale.getDefault()), StringFormat
								.isNull(radioName).toUpperCase(Locale.getDefault()));
					}
				} else if (view instanceof TextView) {// �ı���

					viewMap.put(StringFormat.isNull(view.getContentDescription())
							.toUpperCase(Locale.getDefault()), ((TextView) view)
							.getText().toString());

				} else if (view instanceof ListView) {// listView
					// listview�����ʱ������
					/*
					 * String lvName = view.getContentDescription().toString();
					 * 
					 * List lvList = new ArrayList();
					 * 
					 * Class clazz = map.get(lvName); // �õ�list�з�������
					 * 
					 * ListView lv = ((ListView) view);
					 * 
					 * int n=0;
					 * 
					 * if(lv.getChildCount()>0){ n = ((ListView)
					 * lv.getChildAt(0)).getChildCount(); // �õ�list��ÿ�� // �ӽڵ�ĸ��� }
					 * 
					 * for (int j = 0; j < lv.getChildCount(); j++) {
					 * 
					 * try {
					 * 
					 * Object object = clazz.newInstance(); List<View> vList =
					 * getAllLeafElements(lv.getChildAt(j)); // �õ�һ��listԪ�ص�����ֵ
					 * Map<String, Object> listMap = setViewValToMap(vList,
					 * getParameterizedType(object));
					 * 
					 * for (int m = 0; m < n; m++) { // Ϊÿ��list�ֵ // �õ�������� String
					 * fieldName = ((ViewGroup) lv.getChildAt(j))
					 * .getChildAt(m).getContentDescription()
					 * .toString().toUpperCase(Locale.getDefault()); // �õ����ֵ Object
					 * fieldValue = listMap.get(fieldName); setObj(fieldName,
					 * fieldValue, object); }
					 * 
					 * lvList.add(object);
					 * 
					 * } catch (InstantiationException e) { e.printStackTrace(); }
					 * catch (IllegalAccessException e) { block e.printStackTrace();
					 * } catch (SecurityException e) { e.printStackTrace(); } catch
					 * (NoSuchMethodException e) { e.printStackTrace(); } catch
					 * (IllegalArgumentException e) { e.printStackTrace(); } catch
					 * (InvocationTargetException e) { e.printStackTrace(); } }
					 * viewMap.put(lvName, lvList);
					 */
				}

			}
		}
		Iterator<Entry<String, Integer>> it = checkMap.entrySet().iterator();
		int sum = 0;
		while (it.hasNext()) {
			Entry<String, Integer> e = (Entry<String, Integer>) it.next();
			List<Object> checkList = new ArrayList<Object>();
			for (int j = 0; j < e.getValue(); j++) {
				checkList.add(list.get(sum + j));
			}
			sum = e.getValue();
			viewMap.put(e.getKey(), checkList);
		}
		return viewMap;
	}

	/**
	 * ��ָ����ֵ���õ�TextView����
	 * 
	 * @param textview
	 */
	public void paddingForTextView(TextView textView, Map<String, Object> map) {
		String sName = "";
		Object obj = null;

		if(textView != null){
			sName = StringFormat.isNull(textView.getContentDescription())
					.toString();
			obj = map.get(sName.toUpperCase(Locale.getDefault()));
			if (obj == null) {
				;
			} else {
				textView.setText(obj.toString());
			}
		}
	}

	/**
	 * ��ָ����ֵ���õ�CheckBox���� ע�⣺checkBox������ѡ����һ�����顣�����������checkBox�ĸ�����
	 * ��contentDescriptionֵ�������е�ֵ��checkBox��contentDescriptionֵ��ͬ
	 * 
	 * @param textview
	 */
	public void paddingForCheckBox(CheckBox checkBox, Map<String, Object> map) {
		if(checkBox != null){
			String sName = checkBox.getContentDescription().toString();
			String pName = ((View) checkBox.getParent()).getContentDescription()
					.toString();
			String[] arr = (String[]) map
					.get(pName.toUpperCase(Locale.getDefault()));
			if (arr == null) {
				;
			} else {
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].equals(sName)) {
						checkBox.setChecked(true);
					}
				}
			}
		}
	}

	/**
	 * �������ж�Ӧ��ֵ��䵽��ѡ����
	 * ע�⣺�����е�������Ӧ����Ϊ��radioButton����radioGroup��contentDescriptionֵ��ͬ
	 * ����ֵ��Ҫѡ���radioButton��contentDescriptionֵ��ͬ
	 * 
	 * @param view
	 * @param radioButton
	 * @param map
	 */
	public void paddingForRadioButton(View view, RadioButton radioButton,
			Map<String, Object> map) {
		if(radioButton != null){
			String sName = radioButton.getContentDescription().toString();
			String radioGroupName = ((RadioGroup) radioButton.getParent())
					.getContentDescription().toString();
			String checked = (String) map.get(radioGroupName.toUpperCase(Locale
					.getDefault()));
			if (checked == null || sName == null) {
				;
			} else {
				if (sName.equals(checked)) {
					radioButton.setChecked(true);
				}
			}
		}
	}

	/**
	 * �������ж�Ӧ��ֵ��䵽������ ��������Ҫѡ���ToggleButton��contentDescriptionֵ��ͬ
	 * 
	 * @param view
	 * @param map
	 */
	public void paddingForToggleButton(ToggleButton toggleButton,
			Map<String, Object> map) {
		if(toggleButton != null){
			String sName = toggleButton.getContentDescription().toString();
			Boolean status = (Boolean) map.get(sName.toUpperCase(Locale
					.getDefault()));
			if (status != null) {
				if (status.booleanValue()) {
					toggleButton.setChecked(true);
				} else {
					toggleButton.setChecked(false);
				}
			}
		}
	}

	/**
	 * �������е�list��䵽��Ӧ��ListView�У�listView��contentDescriptionֵ�������list��������ͬ ע�⣺
	 * 1.������ǰע���listview��Ӧ��adapter�࣬ע��ʱ������ΪlistView��contentDescription
	 * ֵΪ�����������,context��Ҫ���� 2.adapter�����ʵ�����������Ĺ��췽�� ��һ������ List datas �ڶ�������
	 * Context context
	 * 
	 * @param listView
	 * @param map
	 * @param context
	 */
	public void paddingForListView(ListView listView, Map<String, Object> map,
			Context context) {
		if(listView != null){
			String sName = listView.getContentDescription().toString();
			List<?> list = (List<?>) map
					.get(sName.toUpperCase(Locale.getDefault()));
			Class<?> adpterClass = AdapterRegist.mAdapterRegist.get(sName);

			try {
				Constructor<?> constructor = adpterClass.getConstructor(List.class,
						Context.class);
				BaseAdapter adapter = (BaseAdapter) constructor.newInstance(list,
						context);
				listView.setAdapter(adapter);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �õ�list�з��͵����ͣ�����map���󣬼���list����ֵΪ���͵�ʵ��
	 * 
	 * @param obj
	 * @return
	 */
	public Map<String, Class<?>> getParameterizedType(Object obj) {
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		if(obj != null){
			Class<?> objClass = obj.getClass();
			Field[] fields = objClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {

				Field field = fields[i];
				// �õ��������
				Class<?> fieldType = field.getType();
				// Object fieldValue = null;

				boolean accessible = false;
				accessible = field.isAccessible();
				// �������˽����
				if (accessible == false) {
					field.setAccessible(true);
				}
				if (fieldType.isAssignableFrom(List.class)) {

					Type fc = field.getGenericType(); // �ؼ��ĵط��������List���ͣ��õ���Generic������

					if (fc == null)
						continue;

					if (fc instanceof ParameterizedType) // ����Ƿ��Ͳ���������
					{
						ParameterizedType pt = (ParameterizedType) fc;

						Class<?> genericClazz = (Class<?>) pt
								.getActualTypeArguments()[0]; // �õ��������class���Ͷ���

						map.put(field.getName(), genericClazz);

					}
				}
			}
		}
		return map;
	}

	public void setObj(String fieldName, Object fieldValue, Object object)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		if(object != null){
			Class<?> clazz = object.getClass();

			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {

				Field field = fields[i];

				if (field.getName().equalsIgnoreCase(fieldName)) {

					Class<?> fieldType = field.getType();
					boolean accessible = false;
					accessible = field.isAccessible();
					// �������˽����
					if (accessible == false) {
						field.setAccessible(true);
					}
					if (fieldType.isPrimitive()) {// �������͵ĸ�ֵ
						if (fieldType.equals(float.class)) {
							field.set(object,
									Float.parseFloat(fieldValue.toString()));
						} else if (fieldType.equals(int.class)) {
							field.set(object,
									Integer.parseInt(fieldValue.toString()));
						} else if (fieldType.equals(double.class)) {
							field.set(object,
									Double.parseDouble(fieldValue.toString()));
						} else if (fieldType.equals(short.class)) {
							field.set(object,
									Short.parseShort(fieldValue.toString()));
						} else if (fieldType.equals(long.class)) {
							field.set(object, Long.parseLong(fieldValue.toString()));
						} else if (fieldType.equals(boolean.class)) {
							field.set(object,
									Boolean.parseBoolean(fieldValue.toString()));
						} else if (fieldType.equals(byte.class)) {
							field.set(object, Byte.parseByte(fieldValue.toString()));
						} else if (fieldType.equals(char.class)) {
							field.set(object,
									fieldValue.toString().toCharArray()[0]);
						}
					} else {
						field.set(object, fieldValue);
					}
					field.setAccessible(accessible);
					break;
				}
			}
		}
		
	}

}
