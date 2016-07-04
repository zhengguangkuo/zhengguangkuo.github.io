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
	 * ����ؼ��Ĺ�������
	 */
	int ileftspace, itopspace, iwidth, iheight;
	private Context context = null;
	public RelativeLayout layout = null;
	public static String spinnerChecked = "";
	private Handler handler;

	// ��timer��ʱ����ص�����
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
		/* ����λ�� */

		setPosition(view, pro, rl);

		/* ����id */
		if (isSet(pro.getId())) {
			view.setId(RegisterId.map.get(pro.getId()));
		}
		if (view instanceof TextView) {  //��������
			((TextView) view).setTextSize(25);
			((TextView) view).setTypeface(Typeface
					.defaultFromStyle(Typeface.BOLD));
			((TextView) view).setTextColor(Color.BLACK);
		}
		/* �����������ݣ�Ĭ�����ֵĴ�С ��Ĭ�����ֱ��Ӵ֣�Ĭ�����ֵ���ɫ */
		if (isSet(pro.getText())) {
			((TextView) view).setText(pro.getText());	
		}
		/* ���ñ��� */
		if (isSet(pro.getBackground())) {
			Map<String, Integer> drawableIDMap = (Map<String, Integer>) Globals.map.get("DrawableID");
			int resid = drawableIDMap.get(pro.getBackground());
			view.setBackgroundResource(resid);
		}
		/* �������� */
		if (isSet(pro.getCommondinvoke())) {
			view.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i(context.getClass().getName(),"******************"+ pro.getText());
					// TODO Auto-generated method stub
					// ��ȡ�����Ӧ��id
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
		//list.add(0, "��ѡ��");
		// Ϊ�����б���һ��������
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		// Ϊ���������������б�����ʱ�Ĳ˵���ʽ
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(R.layout.sys_frame_spinner_dropdown_item);
		// ����������ӵ������б���
		spinner.setAdapter(adapter);
		// Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/* ����ѡspinner ��ֵ����õ� */
				Toast.makeText(context, "��ѡ����ǣ�" + adapter.getItem(arg2),
						Toast.LENGTH_LONG).show();
				spinnerChecked = adapter.getItem(arg2);
				/* ��spinner ��ʾ */
				arg0.setVisibility(View.VISIBLE);
			}

			@SuppressWarnings("unchecked")
			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "��ѡ����ǣ�NONE", Toast.LENGTH_LONG).show();
				arg0.setVisibility(View.VISIBLE);
			}
		});
		/* �����˵�����������ѡ����¼����� */
		spinner.setOnTouchListener(new Spinner.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				/* ��spinner ���أ�������Ҳ���� */
				// v.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		/* �����˵�����������ѡ���ı��¼����� */
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
		/* ����id */
		if (isSet(pro.getId())) {
			radioGroup.setId(RegisterId.map.get(pro.getId()));
		}
		/* ���ñ��� */
		if (isSet(pro.getBackground())) {
			Map<String, Integer> drawableIDMap = (Map<String, Integer>) Globals.map.get("DrawableID");
			int resid = drawableIDMap.get(pro.getBackground());
			radioGroup.setBackgroundResource(resid);
		}
		/* ����ˮƽ���� */
		radioGroup.setOrientation(LinearLayout.HORIZONTAL);
		String[] arr = pro.getText().split(";");
		for (int i = 0; i < arr.length; i++) {
			RadioButton radioButton = new RadioButton(context);
			/* ע��id */
			RegisterId.registerId(arr[i].split(":")[0]);
			/* ����id */
			radioButton.setId(RegisterId.map.get(arr[i].split(":")[0]));
			/* ����text���� */
			radioButton.setText(arr[i].split(":")[1] + "   ");
			radioButton.setTextSize(25);
			/* Ĭ�����õ�һ��ѡ�� */
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
		// ���ö�ʱ���ؼ���λ��
		setPosition(tvTime, pro, rl);
		// ���������С
		tvTime.setTextSize(25);
		// ����id
		if (isSet(pro.getId())) {
			tvTime.setId(RegisterId.map.get(pro.getId()));
		}
		// ���ö�ʱ������ʾ
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
	 * �жϵ�ǰ�����Ƿ��������ļ�������
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
	 * ���õ�ǰ�ؼ���λ��
	 */
	private void setPosition(View view, ComponentProperty property,
			RelativeLayout rl) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		double width = dm.widthPixels;
		double height = dm.heightPixels;

		// ���ٷֱȴ�Сת����ʵ�ʴ�С
		ileftspace = (int) (width * (Double.valueOf(property.getLeft())) / 100);
		itopspace = (int) (height * (Double.valueOf(property.getTop())) / 100);
		iwidth = (int) (width * (Double.valueOf(property.getWidth())) / 100);
		iheight = (int) (height * (Double.valueOf(property.getHeight())) / 100);

		RelativeLayout.LayoutParams btParams1 = new RelativeLayout.LayoutParams(
				iwidth, iheight);

		btParams1.leftMargin = ileftspace; // �����궨λ
		btParams1.topMargin = itopspace; // �����궨λ
		
		rl.addView(view, btParams1);
		
	}

	/**
	 * ���õ�ǰ�ؼ���λ��
	 */
	private void setPosition(View view, ComponentProperty property) {
		setPosition(view, property, null);
	}

	/**
	 * ������������ֻ�ȡ�����Ӧ��id
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
	 * ���ݿؼ���ID��ȡ���������bean
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
