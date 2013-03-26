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
import fr.apa.fieldcommander.webservice.WebService.WebServiceResponse;

public class TeamController extends Observable {

	public Team getTeam(Team team) throws IllegalArgumentException,
			ClientProtocolException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, IOException,
			JSONException {
		WebServiceResponse<Team> response = WebService.execute(
				RequestType.RETRIEVE, team);

		if (!response.isSuccess())
			// TODO
			throw new IllegalStateException(response.getError());
		return response.getBean();
	}

	public class RetrieveTeamCallBack implements JSONCallBack<Team> {

		@Override
		public void execute(Team object) {
			TeamController.this.notifyObservers();
		}
	}

}
