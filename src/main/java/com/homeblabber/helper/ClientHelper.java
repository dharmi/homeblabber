package com.homeblabber.helper;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class ClientHelper {

	public static ClientConfig configureClient() {
		TrustManager[ ] certs = new TrustManager[ ] {
				new X509TrustManager() {
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
				}
		};
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, certs, new SecureRandom());
		} catch (java.security.GeneralSecurityException ex) {
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

		ClientConfig config = new DefaultClientConfig();
		try {
			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
					new HostnameVerifier() {
						@Override
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					}, 
					ctx
					));
		} catch(Exception e) {
		}
		return config;
	}

	public static Client createClient() {
		return Client.create(ClientHelper.configureClient());
	}
	
	public static ClientResponse postResponse(String URI, String input){
		Client client = createClient();
		WebResource webResource = client.resource(URI);
		WebResource.Builder builder = webResource.
				header("X-Parse-Application-Id", "Your Parse App ID").
				header("X-Parse-REST-API-Key", "Your Parse REST API Key").
				header("Content-Type", "application/json");
		return builder.accept("application/json").post(ClientResponse.class, input);

	}
	public static ClientResponse putResponse(String URI, String input){
		Client client = createClient();
		WebResource webResource = client.resource(URI);
		WebResource.Builder builder = webResource.
				header("X-Parse-Application-Id", "Your Parse App ID").
				header("X-Parse-REST-API-Key", "Your Parse REST API Key").
				header("Content-Type", "application/json");
		return builder.accept("application/json").put(ClientResponse.class, input);

	}
	
	public static ClientResponse getResponse(String URI){
		Client client = createClient();
		WebResource webResource = client.resource(URI);
		WebResource.Builder builder = webResource.
				header("X-Parse-Application-Id", "Your Parse App ID").
				header("X-Parse-REST-API-Key", "Your Parse REST API Key").
				header("Content-Type", "application/json");
		return builder.accept("application/json").get(ClientResponse.class);

	}
	
	public static ClientResponse deleteResponse(String URI){
		Client client = createClient();
		WebResource webResource = client.resource(URI);
		WebResource.Builder builder = webResource.
				header("X-Parse-Application-Id", "Your Parse App ID").
				header("X-Parse-REST-API-Key", "Your Parse REST API Key");
		return builder.delete(ClientResponse.class);

	}
	
	/**
	 * geo coding helper methods
	 * @param URI
	 * @return
	 */
	public static ClientResponse getGeoResponse(String URI){
		Client client = createClient();
		WebResource webResource = client.resource(URI);
		return webResource.accept("application/json").get(ClientResponse.class);

	}
}