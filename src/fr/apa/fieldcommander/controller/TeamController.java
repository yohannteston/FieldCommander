package fr.apa.fieldcommander.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.webservice.JSONCallBack;
import fr.apa.fieldcommander.webservice.RequestType;
import fr.apa.fieldcommander.webservice.WebService;

public class TeamController extends Observable {

	public void retrieveTeam(String id) throws IllegalArgumentException,
			ClientProtocolException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, IOException,
			JSONException {
		WebService
				.request(RequestType.RETRIEVE, id, new RetrieveTeamCallBack());
	}

	public class RetrieveTeamCallBack implements JSONCallBack<Team> {

		@Override
		public void execute(Team object) {
			TeamController.this.notifyObservers();
		}
	}

}
