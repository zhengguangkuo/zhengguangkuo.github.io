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
			// 获取当前layout当中所有的叶子级别的子元素
			List<View> viewList = getAllLeafElements(desView);
			// 将JavaBean当中的属性值存储到map当中
			Map<String, Object> beanMap = setBeanValToMap(srcBean);

			if(viewList != null){
				// 将当前Map当中的属性名与属性值的对应关系设置到View对应的子元素当中
				for (int i = 0; i < viewList.size(); i++) {
					View view = viewList.get(i);
					if (view.getContentDescription() != null) {
						if (view instanceof CheckBox) {// 复选
							paddingForCheckBox((CheckBox) view, beanMap);
						} else if (view instanceof ToggleButton) {// 开关
							paddingForToggleButton((ToggleButton) view, beanMap);
						} else if (view instanceof RadioButton) {// 单选
							paddingForRadioButton(desView, (RadioButton) view,
									beanMap);
						} else if (view instanceof TextView) {// 文本框
							paddingForTextView((TextView) view, beanMap);
						} else if (view instanceof ListView) {// listView,必须传入context
							paddingForListView((ListView) view, beanMap, context);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			bflg = false;
		}
		// 将JavaBean当中的值填充到Map当中

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
		if (view != null && view instanceof ListView) {// 目前最多处理两层listview
			ListView lv = (ListView) view;
			if (lv.getChildAt(0) instanceof ViewGroup) {// 如果list的元素还是list
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
			Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组
			Method method = null;
			String methodName = null;

			try {
				for (int i = 0; i < ma.length; i++) {
					method = ma[i];
					methodName = method.getName();// 方法名

					if (methodName.indexOf("get") == 0) {// 以get开始的方法，排除set方法
						// 取出属性的名字作为Key
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
		List<String> list = new ArrayList<String>();// 将复选框的父容器和当前checkBox的注释名存入list，用于创建数组
		Map<String, Integer> checkMap = new HashMap<String, Integer>();// 一个界面可能有多组复选框，用于每组复选框的计数

		if(viewList != null){
			for (int i = 0; i < viewList.size(); i++) {
				View view = (View) viewList.get(i);
				if (view instanceof CheckBox) {// 复选
					if (((CheckBox) view).isChecked()) {
						String checkBoxName = view.getContentDescription()
								.toString();// 当前checkBox的注释名
						String parentName = ((View) view.getParent())
								.getContentDescription().toString();// 父容器的注释名
						list.add(StringFormat.isNull(parentName).toUpperCase(
								Locale.getDefault())
								+ ","
								+ StringFormat.isNull(checkBoxName).toUpperCase(
										Locale.getDefault()));
						if (!checkMap.isEmpty()) {// 如果已经有复选框
							if (checkMap.get(StringFormat.isNull(parentName)
									.toUpperCase(Locale.getDefault())) != null) {// 如果当前组名已经存在，则计数加1
								checkMap.put(StringFormat.isNull(parentName)
										.toUpperCase(Locale.getDefault()),
										checkMap.get(StringFormat
												.isNull(parentName).toUpperCase(
														Locale.getDefault())) + 1);
							} else {// 将新的复选框的组名存入map，并计数1
								checkMap.put(StringFormat.isNull(parentName)
										.toUpperCase(Locale.getDefault()), 1);
							}
						} else {// 将第一个复选框的组名存入map，并计数1
							checkMap.put(StringFormat.isNull(parentName)
									.toUpperCase(Locale.getDefault()), 1);
						}
					}
				} else if (view instanceof ToggleButton) {// 开关
					if (((ToggleButton) view).isChecked()) {
						viewMap.put(
								StringFormat.isNull(view.getContentDescription())
										.toUpperCase(Locale.getDefault()), true);
					} else {
						viewMap.put(
								StringFormat.isNull(view.getContentDescription())
										.toUpperCase(Locale.getDefault()), false);
					}
				} else if (view instanceof RadioButton) {// 单选,将选中的单选框的注释名存入
															// 变量名为radiogroup的注释名变量中
					if (((RadioButton) view).isChecked()) {
						String radioName = view.getContentDescription().toString();// 当前radiobutton的注释名
						String radioGroupName = ((RadioGroup) view.getParent())
								.getContentDescription().toString();// radiogroup的注释名
						viewMap.put(StringFormat.isNull(radioGroupName)
								.toUpperCase(Locale.getDefault()), StringFormat
								.isNull(radioName).toUpperCase(Locale.getDefault()));
					}
				} else if (view instanceof TextView) {// 文本框

					viewMap.put(StringFormat.isNull(view.getContentDescription())
							.toUpperCase(Locale.getDefault()), ((TextView) view)
							.getText().toString());

				} else if (view instanceof ListView) {// listView
					// listview情况暂时不考虑
					/*
					 * String lvName = view.getContentDescription().toString();
					 * 
					 * List lvList = new ArrayList();
					 * 
					 * Class clazz = map.get(lvName); // 得到list中泛型类型
					 * 
					 * ListView lv = ((ListView) view);
					 * 
					 * int n=0;
					 * 
					 * if(lv.getChildCount()>0){ n = ((ListView)
					 * lv.getChildAt(0)).getChildCount(); // 得到list中每项 // 子节点的个数 }
					 * 
					 * for (int j = 0; j < lv.getChildCount(); j++) {
					 * 
					 * try {
					 * 
					 * Object object = clazz.newInstance(); List<View> vList =
					 * getAllLeafElements(lv.getChildAt(j)); // 得到一个list元素的所有值
					 * Map<String, Object> listMap = setViewValToMap(vList,
					 * getParameterizedType(object));
					 * 
					 * for (int m = 0; m < n; m++) { // 为每个list项赋值 // 得到域的名字 String
					 * fieldName = ((ViewGroup) lv.getChildAt(j))
					 * .getChildAt(m).getContentDescription()
					 * .toString().toUpperCase(Locale.getDefault()); // 得到域的值 Object
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
	 * 将指定的值设置到TextView当中
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
	 * 将指定的值设置到CheckBox当中 注意：checkBox的所有选项是一个数组。数组的名字是checkBox的父容器
	 * 的contentDescription值，数组中的值与checkBox的contentDescription值相同
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
	 * 将对象中对应的值填充到单选框中
	 * 注意：对象中的属性名应定义为与radioButton所属radioGroup的contentDescription值相同
	 * 属性值和要选择的radioButton的contentDescription值相同
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
	 * 将对象中对应的值填充到开关中 属性名和要选择的ToggleButton的contentDescription值相同
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
	 * 将对象中的list填充到对应的ListView中，listView的contentDescription值与对象中list的名字相同 注意：
	 * 1.必须提前注册该listview对应的adapter类，注册时变量名为listView的contentDescription
	 * 值为类的完整类名,context需要传入 2.adapter类必须实现两个参数的构造方法 第一个参数 List datas 第二个参数
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
	 * 得到list中泛型的类型，返回map对象，键是list名，值为泛型的实例
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
				// 得到域的类型
				Class<?> fieldType = field.getType();
				// Object fieldValue = null;

				boolean accessible = false;
				accessible = field.isAccessible();
				// 允许访问私有域
				if (accessible == false) {
					field.setAccessible(true);
				}
				if (fieldType.isAssignableFrom(List.class)) {

					Type fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型

					if (fc == null)
						continue;

					if (fc instanceof ParameterizedType) // 如果是泛型参数的类型
					{
						ParameterizedType pt = (ParameterizedType) fc;

						Class<?> genericClazz = (Class<?>) pt
								.getActualTypeArguments()[0]; // 得到泛型里的class类型对象。

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
					// 允许访问私有域
					if (accessible == false) {
						field.setAccessible(true);
					}
					if (fieldType.isPrimitive()) {// 基本类型的赋值
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
