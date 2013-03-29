package fr.apa.fieldcommander.utils;

import android.content.Context;
import android.widget.Toast;

public class UIUtils {

	public static void toast(String message, Context where){
		Toast.makeText(where, message, Toast.LENGTH_SHORT).show();
	}
}
