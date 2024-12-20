package org.example;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Text2ImageAPI {
    private String url;
    private String apiKey;
    private String secretKey;

    public Text2ImageAPI(String url, String apiKey, String secretKey) {
        this.url = url;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    private String getModel() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url + "key/api/v1/models").openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Key", "Key " + apiKey);
        connection.setRequestProperty("X-Secret", "Secret " + secretKey);

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to get models: HTTP error code " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONArray data = new JSONArray(response.toString());
        return data.getJSONObject(0).getString("id");
    }

    public String generate(String prompt, String model, int images, int width, int height) throws Exception {
        JSONObject params = new JSONObject();
        params.put("type", "GENERATE");
        params.put("numImages", images);
        params.put("width", width);
        params.put("height", height);
        params.put("generateParams", new JSONObject().put("query", prompt));

        String boundary = Long.toHexString(System.currentTimeMillis());
        HttpURLConnection connection = (HttpURLConnection) new URL(url + "key/api/v1/text2image/run").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("X-Key", "Key " + apiKey);
        connection.setRequestProperty("X-Secret", "Secret " + secretKey);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setDoOutput(true);

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.writeBytes("--" + boundary + "\r\n");
            out.writeBytes("Content-Disposition: form-data; name=\"model_id\"\r\n\r\n");
            out.writeBytes(model + "\r\n");
            out.writeBytes("--" + boundary + "\r\n");
            out.writeBytes("Content-Disposition: form-data; name=\"params\"; filename=\"params.json\"\r\n");
            out.writeBytes("Content-Type: application/json\r\n\r\n");
            out.writeBytes(params.toString() + "\r\n");
            out.writeBytes("--" + boundary + "--\r\n");
            out.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to generate image: HTTP error code " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject data = new JSONObject(response.toString());
        return data.getString("uuid");
    }

    public String[] checkGeneration(String requestId, int attempts, int delay) throws Exception {
        while (attempts > 0) {
            HttpURLConnection connection = (HttpURLConnection) new URL(url + "key/api/v1/text2image/status/" + requestId).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Key", "Key " + apiKey);
            connection.setRequestProperty("X-Secret", "Secret " + secretKey);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to check generation status: HTTP error code " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject data = new JSONObject(response.toString());
            if (data.getString("status").equals("DONE")) {
                JSONArray images = data.getJSONArray("images");
                String[] imageUrls = new String[images.length()];
                for (int i = 0; i < images.length(); i++) {
                    imageUrls[i] = images.getString(i);
                }
                return imageUrls; // Возвращаем массив URL-адресов сгенерированных изображений
            }

            attempts--;
            Thread.sleep(delay * 1000); // Задержка перед следующей попыткой
        }

        throw new RuntimeException("Image generation timed out."); // Если все попытки исчерпаны
    }
}