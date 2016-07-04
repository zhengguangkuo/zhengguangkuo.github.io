package com.mt.android.help.listview;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.sys.mvc.common.Response;

public class HelpListViewBean{
	public List<Person> help_listview = new ArrayList<Person>();

	public List<Person> getHelp_listview() {
		return help_listview;
	}

	public void setHelp_listview(List<Person> help_listview) {
		this.help_listview = help_listview;
	}

}
