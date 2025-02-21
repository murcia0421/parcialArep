package org.example;

import java.io.*;
import java.net.*;

import com.google.gson.JsonObject;

public class HttpFacadeServer {

    public static HttpFacadeServer instance;

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://localhost:45000/compreflex?comando";

    private HttpFacadeServer() {
    }
    public static HttpFacadeServer getInstance() {
        if (instance == null) {
            instance = new HttpFacadeServer();
        }
        return instance;
    }

    public JsonObject getReflectiveChatCommand(String requestQuery) throws IOException {
        URL obj = new URL(GET_URL + requestQuery.split("=")[1]);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        JsonObject responseJson = new JsonObject();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            responseJson.addProperty("response", response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");

        return responseJson;

    }
}


