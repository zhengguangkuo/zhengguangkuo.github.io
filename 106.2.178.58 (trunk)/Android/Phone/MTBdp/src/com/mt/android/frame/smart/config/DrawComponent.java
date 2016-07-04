package com.mt.android.frame.smart.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.frame.smart.DemoSmartActivity;
import com.mt.android.frame.smart.init.BaseDialogManager;
import com.mt.android.global.Globals;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.view.common.BaseCommandID;

public class DrawComponent {

	/**
	 * 定义控件的公共属性
	 */
	int ileftspace, itopspace, iwidth, iheight;
	private Context context = null;
	public RelativeLayout layout = null;
	public static String spinnerChecked = "";
	private Handler handler;

	// 与timer定时器相关的属性
	public static int second;
	public static Timer timer;
	public static TextView tvTime;
	public static boolean runFlag;

	public DrawComponent(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	// LayOut
	public void createLayOut() {
		layout = new RelativeLayout(context);
	}

	// BackGround
	public void drawBackGround(String background, RelativeLayout rl) {
		int resid = getIdByResouceName(background, R.drawable.class);
		rl.setBackgroundResource(resid);
	}

	public void drawDialog(String dialogName) {
		this.createLayOut();
		drawLayout(dialogName, layout);
		this.showView();
	}

	public RelativeLayout getRelativeLayout() {
		return layout;
	}

	public void drawLayout(String dialogName, RelativeLayout rl) {
		Map dialogMap = ((BaseDialogManager) Globals.map.get("DialogManager"))
				.getDialogcfgs();
		DialogConfigs config = (DialogConfigs) dialogMap.get(dialogName);
		List ls = config.getComponent();
		drawBackGround(config.getBackground(), rl);

		for (int i = 0; i < ls.size(); i++) {
			ComponentProperty pro = (ComponentProperty) ls.get(i);
			if (pro.getType().equalsIgnoreCase("BUTTON")) {
				draw(new Button(context), pro, rl);
			} else if (pro.getType().equalsIgnoreCase("IMAGEVIEW")) {
				draw(new ImageView(context), pro, rl);
			} else if (pro.getType().equalsIgnoreCase("TEXTVIEW")) {
				draw(new TextView(context), pro, rl);
			} else if (pro.getType().equalsIgnoreCase("EDITTEXT")) {
				EditText et = new EditText(context);
				et.setInputType(InputType.TYPE_CLASS_NUMBER);
				
				draw(et, pro, rl);
			} else if (pro.getType().equalsIgnoreCase("IMAGEBUTTOM")) {
				draw(new ImageButton(context), pro, rl);
			} else if (pro.getType().equalsIgnoreCase("LISTVIEW")) {
				drawListView(pro, rl);
			} else if (pro.getType().equalsIgnoreCase("SPINNER")) {
				drawSpinner(pro, rl);
			} else if (pro.getType().equalsIgnoreCase("RADIOBUTTON")) {
				this.drawRadio(pro, rl);
			} else if (pro.getType().equalsIgnoreCase("TIMMER")) {
				drawTimmer(pro, rl);
			} else if (pro.getType().equalsIgnoreCase("INCLUDE")) {
				RelativeLayout relativeLayout = new RelativeLayout(context);
				String id = pro.getId();
				drawLayout(id, relativeLayout);
				draw(relativeLayout, pro, rl);
			} else if (pro.getType().equalsIgnoreCase("GRIDVIEW")) {
				drawGridView(pro, rl);
			}
		}
	}

	public void draw(View view, final ComponentProperty pro, RelativeLayout rl) {
		/* 设置位置 */

		setPosition(view, pro, rl);

		/* 设置id */
		if (isSet(pro.getId())) {
			view.setId(RegisterId.map.get(pro.getId()));
		}
		if (view instanceof TextView) {  //设置文字
			((TextView) view).setTextSize(25);
			((TextView) view).setTypeface(Typeface
					.defaultFromStyle(Typeface.BOLD));
			((TextView) view).setTextColor(Color.BLACK);
		}
		/* 设置文字内容，默认文字的大小 ，默认文字被加粗，默认文字的颜色 */
		if (isSet(pro.getText())) {
			((TextView) view).setText(pro.getText());	
		}
		/* 设置背景 */
		if (isSet(pro.getBackground())) {
			Map<String, Integer> drawableIDMap = (Map<String, Integer>) Globals.map.get("DrawableID");
			int resid = drawableIDMap.get(pro.getBackground());
			view.setBackgroundResource(resid);
		}
		/* 设置命令 */
		if (isSet(pro.getCommondinvoke())) {
			view.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i(context.getClass().getName(),"******************"+ pro.getText());
					// TODO Auto-generated method stub
					// 获取命令对应的id
					((DemoSmartActivity) context).setActivityIDById(pro.getId());
					Request requestBean = ((DemoSmartActivity) context)
							.getRequestByCommandName(pro.getCommondinvoke());
					if (requestBean == null) {
						return;
					}
					int commandId = getIdByCommondName(pro.getCommondinvoke());

					((BaseActivity) context).go(commandId, requestBean, false);
				}

			});
		}
	}

	// ListView
	public void drawListView(final ComponentProperty pro, RelativeLayout rl) {
		ListView lsview = new ListView(context);
		draw(lsview, pro, rl);
		List ls = ((DemoSmartActivity) context).getDataListById(pro.getId());
		DrawListViewAdapter adapter = new DrawListViewAdapter(ls, context);
		lsview.setAdapter(adapter);
	}

	// spinner
	public void drawSpinner(final ComponentProperty pro, RelativeLayout rl) {
		Spinner spinner = new Spinner(context);
		draw(spinner, pro, rl);
		List list = ((DemoSmartActivity) context).getDataListById(pro.getId());
		//list.add(0, "请选择");
		// 为下拉列表定义一个适配器
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		// 为适配器设置下拉列表下拉时的菜单样式
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(R.layout.sys_frame_spinner_dropdown_item);
		// 将适配器添加到下拉列表上
		spinner.setAdapter(adapter);
		// 为下拉列表设置各种事件的响应，这个事响应菜单被选中
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/* 将所选spinner 的值带入得到 */
				Toast.makeText(context, "您选择的是：" + adapter.getItem(arg2),
						Toast.LENGTH_LONG).show();
				spinnerChecked = adapter.getItem(arg2);
				/* 将spinner 显示 */
				arg0.setVisibility(View.VISIBLE);
			}

			@SuppressWarnings("unchecked")
			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "您选择的是：NONE", Toast.LENGTH_LONG).show();
				arg0.setVisibility(View.VISIBLE);
			}
		});
		/* 下拉菜单弹出的内容选项触屏事件处理 */
		spinner.setOnTouchListener(new Spinner.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				/* 将spinner 隐藏，不隐藏也可以 */
				// v.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		/* 下拉菜单弹出的内容选项焦点改变事件处理 */
		spinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				v.setVisibility(View.VISIBLE);
			}
		});
	}

	// drawRadio
	public void drawRadio(final ComponentProperty pro, RelativeLayout rl) {
		RadioGroup radioGroup = new RadioGroup(context);
		setPosition(radioGroup, pro, rl);
		/* 设置id */
		if (isSet(pro.getId())) {
			radioGroup.setId(RegisterId.map.get(pro.getId()));
		}
		/* 设置背景 */
		if (isSet(pro.getBackground())) {
			Map<String, Integer> drawableIDMap = (Map<String, Integer>) Globals.map.get("DrawableID");
			int resid = drawableIDMap.get(pro.getBackground());
			radioGroup.setBackgroundResource(resid);
		}
		/* 设置水平排列 */
		radioGroup.setOrientation(LinearLayout.HORIZONTAL);
		String[] arr = pro.getText().split(";");
		for (int i = 0; i < arr.length; i++) {
			RadioButton radioButton = new RadioButton(context);
			/* 注册id */
			RegisterId.registerId(arr[i].split(":")[0]);
			/* 设置id */
			radioButton.setId(RegisterId.map.get(arr[i].split(":")[0]));
			/* 设置text内容 */
			radioButton.setText(arr[i].split(":")[1] + "   ");
			radioButton.setTextSize(25);
			/* 默认设置第一个选中 */
			if (i == 0) {
				radioButton.setChecked(true);
			}
			radioGroup.addView(radioButton);
		}
	}

	// GridView
	public void drawGridView(final ComponentProperty pro, RelativeLayout rl) {
		GridView gridView = new GridView(context);
		gridView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f));
		gridView.setNumColumns(3);
		draw(gridView, pro, rl);
		List ls = ((DemoSmartActivity) context).getDataListById(pro.getId());
		try {
			Class clazz = Class.forName("com.mt.android.frame.smart.config."+pro.getAdapter());
			
			Constructor adapter = clazz.getConstructor(List.class,Context.class);
			
			BaseAdapter baseAdapter = (BaseAdapter)adapter.newInstance(ls,context);
			
			gridView.setAdapter(baseAdapter);
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	public void drawTimmer(ComponentProperty pro, RelativeLayout rl) {
		this.tvTime = new TextView(context);
		// 设置定时器控件的位置
		setPosition(tvTime, pro, rl);
		// 设置字体大小
		tvTime.setTextSize(25);
		// 设置id
		if (isSet(pro.getId())) {
			tvTime.setId(RegisterId.map.get(pro.getId()));
		}
		// 设置定时器的显示
		if (isSet(pro.getSecond())) {
			this.second = Integer.parseInt(pro.getSecond());
			timer = new Timer();
			runFlag = true;
			timer.schedule(new MyTask(), 1000, 1000);
		}
	}

	class MyTask extends TimerTask {
		@Override
		public void run() {
			Message msg = Message.obtain(handler);
			if (second != 0) {
				msg.what = 0;
			} else if (second == 0) {
				msg.what = 1;
			}
			msg.sendToTarget();
		}
	}

	public void showView() {
		((Activity) context).setContentView(layout);
	}

	/**
	 * 判断当前属性是否在配置文件中设置
	 * 
	 * @param obj
	 * @return
	 */
	private boolean isSet(Object obj) {
		if (obj != null && !obj.toString().equalsIgnoreCase("")) {
			return true;
		}

		return false;
	}

	/**
	 * 设置当前控件的位置
	 */
	private void setPosition(View view, ComponentProperty property,
			RelativeLayout rl) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		double width = dm.widthPixels;
		double height = dm.heightPixels;

		// 将百分比大小转换成实际大小
		ileftspace = (int) (width * (Double.valueOf(property.getLeft())) / 100);
		itopspace = (int) (height * (Double.valueOf(property.getTop())) / 100);
		iwidth = (int) (width * (Double.valueOf(property.getWidth())) / 100);
		iheight = (int) (height * (Double.valueOf(property.getHeight())) / 100);

		RelativeLayout.LayoutParams btParams1 = new RelativeLayout.LayoutParams(
				iwidth, iheight);

		btParams1.leftMargin = ileftspace; // 横坐标定位
		btParams1.topMargin = itopspace; // 纵坐标定位
		
		rl.addView(view, btParams1);
		
	}

	/**
	 * 设置当前控件的位置
	 */
	private void setPosition(View view, ComponentProperty property) {
		setPosition(view, property, null);
	}

	/**
	 * 根据命令的名字获取命令对应的id
	 * 
	 * @param commandName
	 * @return
	 */
	private int getIdByCommondName(String commandName) {
		if (commandName != null && !commandName.equals("")
				&& Globals.map.get("CommandID") != null) {
			 if (((BaseCommandID) Globals.map.get("CommandID")).map
					.get(commandName) != null) {
				 return ((BaseCommandID) Globals.map.get("CommandID")).map
							.get(commandName);
			 }
		}
		return 0;
	}

	/**
	 * 根据控件的ID获取命令请求的bean
	 * 
	 * @param commandName
	 * @return
	 */
	private Object getReqBeanById(String commandName) {

		// return (Activity)context.getReqBean();
		return null;
	}

	private int getIdByResouceName(String name, Class cls) {
		int value = 0;
		try {
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				//Log.i("info", field.getName());
				if (field.getName().equals(name)) {
					value = field.getInt(cls);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;
	}
}
