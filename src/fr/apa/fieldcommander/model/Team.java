package fr.apa.fieldcommander.model;

import java.lang.reflect.InvocationTargetException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.apa.fieldcommander.webservice.JSON2JavaBeanWrapper;

public class Team {

	private Integer id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTeam(JSONArray array) throws JSONException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		if (array.length() == 1) {
			JSONObject v = array.getJSONObject(0);
			new JSON2JavaBeanWrapper<Team>().write(v, this);
		} else {
			// todo
		}
	}
}
