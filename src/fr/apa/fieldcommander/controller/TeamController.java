package fr.apa.fieldcommander.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.webservice.WebServiceCallBack;
import fr.apa.fieldcommander.webservice.WebServiceId;
import fr.apa.fieldcommander.webservice.WebServiceRequest;
import fr.apa.fieldcommander.webservice.WebServiceResponse;

public class TeamController extends Observable {

	public void retrieveTeam(String id) throws IllegalArgumentException,
			ClientProtocolException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, IOException,
			JSONException {
		new WebServiceRequest<String, Team>().perform(
				WebServiceId.RETRIEVE_TEAM, id, new RetrieveTeamCallBack());
	}

	public class RetrieveTeamCallBack implements WebServiceCallBack<Team> {

		@Override
		public void execute(WebServiceResponse<Team> object) {
			TeamController.this.notifyObservers();

		}
	}

}
