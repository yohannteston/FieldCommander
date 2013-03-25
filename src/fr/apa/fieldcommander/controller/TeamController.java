package fr.apa.fieldcommander.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.webservice.RequestType;
import fr.apa.fieldcommander.webservice.WebService;

public class TeamController {

	public Team getTeam(Team team) throws IllegalArgumentException, ClientProtocolException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException, JSONException{
		return WebService.execute(RequestType.RETRIEVE, team);
	}
}
