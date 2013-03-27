package fr.apa.fieldcommander.webservice;

public class WebServiceResponse<T> {
	private boolean success;
	private String error;
	private T bean;

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}
}