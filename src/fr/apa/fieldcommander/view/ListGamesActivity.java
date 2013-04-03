package fr.apa.fieldcommander.view;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import fr.apa.fieldcommander.R;
import fr.apa.fieldcommander.controller.TeamController;
import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.utils.UIUtils;
import fr.apa.fieldcommander.webservice.WebServiceResponse;

public class ListGamesActivity extends Activity implements Observer {

	private ListView gamesList;
	private TeamController teamController;
	private ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_games);

		teamController = new TeamController();
		teamController.addObserver(this);

		gamesList = (ListView) findViewById(R.id.gamesList);

		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		listAdapter.add("Default");

		gamesList.setAdapter(listAdapter);
		gamesList.setOnItemClickListener(mMessageClickedHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_games, menu);
		return true;
	}

	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			Toast.makeText(ListGamesActivity.this, position + "",
					Toast.LENGTH_SHORT).show();
		}
	};

	public void refreshClicked(View v) {
		try {
			teamController.retrieveTeam("1");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		WebServiceResponse<Team> response = (WebServiceResponse<Team>)arg1;
		
		System.out.println("Updating view with "+response.getBean().getName());
		if(response.isSuccess()){
			Team team = response.getBean();
			listAdapter.clear();
			listAdapter.add(team.getName());
			listAdapter.notifyDataSetChanged();
		}else{
			UIUtils.toast(response.getError(), getApplicationContext());
		}
	}
	
	public static class ListItemView extends View{

		private final Team team;
		
		public ListItemView(Context context, Team team) {
			super(context);
			this.team = team;
		}
		
		public Team getTeam() {
			return team;
		}
	}

}
