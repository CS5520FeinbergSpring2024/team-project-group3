package com.example.final_project;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Breed Api class that fetches api.
 */
public class BreedApi {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public interface FetchBreedDataCallback {
        void onDataFetched(List<petData> petDataList);
    }

    // API link:
    // https://developers.thecatapi.com/view-account/ylX4blBYT9FaoVd6OhvR?report=aZyiLrsCh#tag/Breeds/paths/~1breeds~1search/get
    public void fetchBreedData(String petType, FetchBreedDataCallback callback) {
        List<petData> petDataList = new ArrayList<>();
        executorService.execute(() -> {
            try {
                String apiKey = "live_xouvzjH87bpes6UKhI4UKrLZkocBcRvfBiUcxFkyahpiBB6yQ931zs4n8q6lAw9a";
                URL url = new URL("https://api.the"+petType+"api.com/v1/breeds?api_key=" + apiKey);

                // Make HTTP request
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                StringBuilder result = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                        Log.d(petType, line);
                    }
                }
//                Log.d("result", result.toString());
                JSONArray jsonArray = new JSONArray(result.toString());

                // extract temperament/description, image id (for image url) and breed name.
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String des;
                    if (jsonObject.has("temperament")) {
                        des = jsonObject.getString("temperament");
                    } else if (jsonObject.has("description")) {
                        des = jsonObject.getString("description");
                    } else {
                        des = "";
                    }
                    String id;
                    if (jsonObject.has("reference_image_id")) {
                        id = jsonObject.getString("reference_image_id");
                    } else {
                        id = "";
                    }

                    // create a new object for each breed.
                    petData petData = new petData(
                            petType,
                            jsonObject.getString("name"),
                            des,
                            id
                    );
//                    Log.d("Pet Breed data: ", petData.getBreed() + " " + petData.getImageURL() + " " + petData.getDescription());
                    petDataList.add(petData);
                    callback.onDataFetched(petDataList);
                }

            } catch (Exception e) {
                // check errors.
                e.printStackTrace();
            }
        });
    }
}
