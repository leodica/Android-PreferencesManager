/*
 * Copyright (C) 2013 Simon Marquis (http://www.simon-marquis.fr)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package fr.simon.marquis.preferencesmanager.model;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

public class PreferenceEntry {

	public enum EntryType {
		INT("int"), STRING("string"), LONG("long"), BOOLEAN("boolean");
		private String val;

		private EntryType(String val) {
			this.val = val;
		}

		public String getVal() {
			return val;
		}
	};

	private EntryType entryType;
	private String name;
	private String value;

	public PreferenceEntry(XmlPullParser parser) {
		String s = parser.getName().toUpperCase();
		entryType = EntryType.valueOf(s);
		Log.e("", "new PreferenceEntry " + s);
		name = parser.getAttributeValue(null, "name");
		Log.e("", "Name " + name);
		switch (entryType) {
		case INT:
		case BOOLEAN:
		case LONG:
			value = parser.getAttributeValue(null, "value");
			break;
		case STRING:
			try {
				value = parser.nextText();
			} catch (XmlPullParserException e) {
			} catch (IOException e) {
			}
			break;
		default:
			break;
		}
		Log.e("", "Value " + value);
	}

	public void addTag(XmlSerializer serializer) {
		try {
			serializer.startTag("", entryType.getVal());
			serializer.attribute(null, "name", name);
			switch (entryType) {
			case INT:
			case BOOLEAN:
			case LONG:
				serializer.attribute(null, "value", value);
				break;
			case STRING:
				serializer.text(value);
				break;
			}
			serializer.endTag("", entryType.getVal());
		} catch (IllegalArgumentException e) {
			Log.e("","",e);
		} catch (IllegalStateException e) {
			Log.e("","",e);
		} catch (IOException e) {
			Log.e("","",e);
		}
	}

}