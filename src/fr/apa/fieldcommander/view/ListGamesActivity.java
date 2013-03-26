package fr.apa.fieldcommander.view;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
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

public class ListGamesActivity extends Activity implements Observer {

	private ListView gamesList;
	private TeamController teamController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_games);

		teamController = new TeamController();
		teamController.addObserver(this);

		gamesList = (ListView) findViewById(R.id.gamesList);
		String[] data = new String[] { "LOL", "RELOL" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data);

		gamesList.setAdapter(adapter);
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

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
