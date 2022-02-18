package se.moeser.javacleanscaffold.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SharedTestHelper {


    public static JSONObject getRequest(String host, String endpoint) throws JSONException, IOException, InterruptedException {
        return SharedTestHelper.authenticatedGetRequest(host, endpoint, null);
    }

    public static JSONObject authenticatedGetRequest(String host, String endpoint, String authToken) throws JSONException, IOException, InterruptedException {
        URI uri = URI.create(host + endpoint);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json; charset=UTF-8");

        if (authToken != null) {
            requestBuilder.setHeader("Authorization","Bearer " + authToken);
        }

        HttpRequest request = requestBuilder.build();

        return SharedTestHelper.jsonRequest(request);
    }

    public static JSONObject postRequest(String host, String endpoint, JSONObject data) throws JSONException, IOException, InterruptedException {
        return SharedTestHelper.authenticatedPostRequest(host, endpoint, data, null);
    }

    public static JSONObject authenticatedPostRequest(String host, String endpoint, JSONObject data, String authToken) throws JSONException, IOException, InterruptedException {
        URI uri = URI.create(host + endpoint);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(data.toString()) );

        if (authToken != null) {
           requestBuilder.setHeader("Authorization","Bearer " + authToken);
        }

        HttpRequest request = requestBuilder.build();

        String d = data.toString();

        return SharedTestHelper.jsonRequest(request);
    }

    public static JSONObject jsonRequest(HttpRequest request) throws IOException, InterruptedException, JSONException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        // On empty response
        if (responseBody.isEmpty()) {
            return new JSONObject();
        }

        return new JSONObject(response.body());
    }
}
