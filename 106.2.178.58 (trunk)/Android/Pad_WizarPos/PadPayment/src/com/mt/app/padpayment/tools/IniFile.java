package com.mt.app.padpayment.tools;

import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import android.util.Log;

public class IniFile {
	private String fileName;
	private HashMap<String, HashMap<String, String>> hm;

	public IniFile(String IniFile) {
		this.fileName = IniFile;
		this.hm = new HashMap<String, HashMap<String, String>>();
		try {
			File f = new File(IniFile);
			if (!f.exists()) {
				f.createNewFile();
			} else {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(IniFile),
								"utf-8"));
				// BufferedReader reader = new BufferedReader(new
				// InputStreamReader(new FileInputStream(IniFile)));;
				// BufferedReader reader = new BufferedReader(new
				// InputStreamReader(new FileInputStream(IniFile), "gb2312"));
				String section = "";
				String key = "";
				String value = "";
				String line = "";
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.indexOf("[") != -1 && line.indexOf("]") != -1
							&& line.indexOf("]") > line.indexOf("[")) {
						line = line.substring(line.indexOf('['));
					}
					if (!line.equals("") && !line.startsWith("#")
							&& !line.startsWith("//")) {
						if (line.startsWith("[") && line.endsWith("]")) { // section
							section = line.substring(1, line.length() - 1)
									.trim();
							this.hm.put(section, new HashMap<String, String>());
						} else if (line.matches("[^=]*={1}[^=]*")) { // key-value
							int p = line.indexOf('=');
							if (p != -1) {
								key = line.substring(0, p).trim();
								value = line.substring(p + 1).trim();
								if (hm.containsKey(section)) {
									this.hm.get(section).put(key, value);
								} else {
									addSection("");
								}
							}
						}
					}
				}
				reader.close();
			}
		} catch (IOException e) {
		}
	}

	public void update() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(this.fileName), "utf-8"));
			// BufferedWriter writer = new BufferedWriter(new
			// OutputStreamWriter(new FileOutputStream(this.fileName),
			// "gb2312"));
			if (hm.size() > 0) {
				for (String section : hm.keySet()) {
					if (!section.trim().equals("")) {
						writer.write("[" + section + "]"
								+ System.getProperty("line.separator"));
					}
					HashMap<String, String> kvp = new HashMap<String, String>();
					kvp.putAll(hm.get(section));
					for (String key : kvp.keySet()) {
						writer.write(key + "=" + kvp.get(key)
								+ System.getProperty("line.separator"));
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("info", "................................................................................");
			Log.i("info", e.getMessage());
		}
	}

	public void dispose() {
		hm.clear();
		hm = null;
	}

	// return all keys of a section, not including values
	public String[] readSection(String section) {
		if (hm.containsKey(section)) {
			HashMap<String, String> kvp = new HashMap<String, String>();
			kvp.putAll(hm.get(section));
			if (!kvp.isEmpty()) {
				String[] keysOfSection = new String[kvp.size()];
				int i = 0;
				for (String key : kvp.keySet()) {
					keysOfSection[i++] = key;
				}
				return keysOfSection;
			}
		}
		return null;
	}

	// return all sections, not including key-value of these sections
	public String[] readSections() {
		if (!hm.isEmpty()) {
			String[] keysOfSection = new String[hm.size()];
			int i = 0;
			for (String key : hm.keySet()) {
				keysOfSection[i++] = key;
			}
			return keysOfSection;
		}
		return null;
	}

	// return all key-value of a section
	public String[] readSectionValues(String section) {
		if (hm.containsKey(section)) {
			HashMap<String, String> kvp = new HashMap<String, String>();
			kvp.putAll(hm.get(section));
			if (!kvp.isEmpty()) {
				String[] keysOfSection = new String[kvp.size()];
				int i = 0;
				for (String key : kvp.keySet()) {
					keysOfSection[i++] = key + "=" + kvp.get(key);
				}
				return keysOfSection;
			}
		}
		return null;
	}

	// remove a section including all key-value of this section
	public void eraseSection(String section) {
		if (hm.containsKey(section)) {
			hm.remove(section);
			update();
		}
	}

	// delete the key-value of the section
	public void deleteKey(String section, String key) {
		if (hm.containsKey(section)) {
			if (hm.get(section).containsKey(key)) {
				hm.get(section).remove(key);
				update();
			}
		}
	}

	public boolean readBoolean(String section, String key, boolean defaultValue) {
		if (hm.containsKey(section)) {
			if (hm.get(section).containsKey(key)) {
				return Boolean.parseBoolean(hm.get(section).get(key));
			}
		}
		return defaultValue;
	}

	public void writeBoolean(String section, String key, boolean value) {
		addKey(section, key, value);
		update();
	}

	public int readInt(String section, String key, int defaultValue) {
		if (hm.containsKey(section)) {
			if (hm.get(section).containsKey(key)) {
				return Integer.parseInt(hm.get(section).get(key));
			}
		}
		return defaultValue;
	}

	public void writeInt(String section, String key, int value) {
		addKey(section, key, value);
		update();
	}

	public String readString(String section, String key, String defaultValue) {
		if (hm.containsKey(section)) {
			if (hm.get(section).containsKey(key)) {
				return hm.get(section).get(key);
			}
		}
		return defaultValue;
	}

	public void writeString(String section, String key, String value) {
		addKey(section, key, value);
		update();
	}

	public boolean sectionExists(String section) {
		return hm.containsKey(section);
	}

	public boolean keyExists(String section, String key) {
		return hm.containsKey(section) ? hm.get(section).containsKey(key)
				: false;
	}

	public void addSection(String section) {
		if (!hm.containsKey(section)) {
			hm.put(section, new HashMap<String, String>());
		}
	}

	public void addKey(String section, String key, boolean value) {
		addSection(section);
		hm.get(section).put(key, Boolean.toString(value));
	}

	public void addKey(String section, String key, int value) {
		addSection(section);
		hm.get(section).put(key, Integer.toString(value));
	}

	public void addKey(String section, String key, String value) {
		addSection(section);
		hm.get(section).put(key, value);
	}

	public String getFileName() {
		return this.fileName;
	}

	// merge section1 and section2 to newSection, newSection can be section1 or
	// section2 or a new section name
	public void mergeSections(String section1, String section2,
			String newSection) {
		if (sectionExists(section1) && sectionExists(section2)) {
			String[] kvp1 = readSectionValues(section1);
			String[] kvp2 = readSectionValues(section2);
			if (kvp1.length > 0 && kvp2.length > 0) {
				if (newSection.equalsIgnoreCase(section1)) {
					eraseSection(section2);
					for (int i = 0; i < kvp2.length; i++) {
						int p = kvp2[i].indexOf('=');
						hm.get(section1).put(kvp2[i].substring(0, p).trim(),
								kvp2[i].substring(p + 1).trim());
					}
				} else if (newSection.equalsIgnoreCase(section2)) {
					eraseSection(section1);
					for (int i = 0; i < kvp1.length; i++) {
						int p = kvp1[i].indexOf('=');
						hm.get(section2).put(kvp1[i].substring(0, p).trim(),
								kvp1[i].substring(p + 1).trim());
					}
				} else {
					eraseSection(section1);
					eraseSection(section2);
					addSection(newSection);
					for (int i = 0; i < kvp1.length; i++) {
						int p = kvp1[i].indexOf('=');
						hm.get(newSection).put(kvp1[i].substring(0, p).trim(),
								kvp1[i].substring(p + 1).trim());
					}
					for (int i = 0; i < kvp2.length; i++) {
						int p = kvp2[i].indexOf('=');
						hm.get(newSection).put(kvp2[i].substring(0, p).trim(),
								kvp2[i].substring(p + 1).trim());
					}
				}
				update();
			}
		}
	}
}
