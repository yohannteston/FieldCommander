package fr.apa.fieldcommander.utils;

public class AccountIDHolder {

	private static int accountID;

	public static int getAccountID() {
		return accountID;
	}

	public static void setAccountID(int accountID) {
		AccountIDHolder.accountID = accountID;
	}

}
