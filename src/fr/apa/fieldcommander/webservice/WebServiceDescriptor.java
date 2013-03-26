package fr.apa.fieldcommander.webservice;

import java.util.List;

public class WebServiceDescriptor {

	public WebServiceDescriptor(WebServiceDescriptorKey wsKey, String url,
			List<String> properties) {
		super();
		this.wsKey = wsKey;
		this.url = url;
		this.properties = properties;
	}

	private WebServiceDescriptorKey wsKey;
	private String url;
	private List<String> properties;

	public List<String> getProperties() {
		return properties;
	}

	public WebServiceDescriptorKey getWsKey() {
		return wsKey;
	}

	public String getUrl() {
		return url;
	}

	public static class WebServiceDescriptorKey {

		public WebServiceDescriptorKey(RequestType requestType,
				Class<?> parameterClass) {
			super();
			this.requestType = requestType;
			this.parameterClass = parameterClass;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((parameterClass == null) ? 0 : parameterClass.hashCode());
			result = prime * result
					+ ((requestType == null) ? 0 : requestType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof WebServiceDescriptorKey))
				return false;
			if (this == obj)
				return true;
			WebServiceDescriptorKey other = (WebServiceDescriptorKey) obj;
			if (parameterClass == null) {
				if (other.parameterClass != null)
					return false;
			} else if (!parameterClass.equals(other.parameterClass))
				return false;
			if (requestType != other.requestType)
				return false;
			return true;
		}

		private RequestType requestType;
		private Class<?> parameterClass;

		public Class<?> getParameterClass() {
			return parameterClass;
		}

	}
}
