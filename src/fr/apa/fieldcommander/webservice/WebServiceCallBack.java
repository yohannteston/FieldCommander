package fr.apa.fieldcommander.webservice;

public interface WebServiceCallBack<T> {

	public void execute(WebServiceResponse<T> object);
}
