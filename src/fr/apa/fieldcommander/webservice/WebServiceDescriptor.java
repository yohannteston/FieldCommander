package fr.apa.fieldcommander.webservice;

import java.util.Map;

public class WebServiceDescriptor<Parameter, Result> {

	public WebServiceDescriptor(WebServiceId wsId, String url,
			Class<Parameter> parameter, Map<String, String> properties,
			Class<Result> result) {
		super();
		this.webServiceId = wsId;
		this.url = url;
		this.properties = properties;
		this.parameter = parameter;
		this.result = result;
	}

	private final WebServiceId webServiceId;
	private final String url;
	private final Class<Parameter> parameter;
	private final Map<String, String> properties;
	private final Class<Result> result;

	public Map<String, String> getProperties() {
		return properties;
	}

	public WebServiceId getWebServiceId() {
		return webServiceId;
	}

	public String getUrl() {
		return url;
	}

	public Class<Result> getResult() {
		return result;
	}
}
