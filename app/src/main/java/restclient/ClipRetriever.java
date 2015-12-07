package restclient;

import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import domain.Clip;

/**
 * Created by Arscd on 07.12.2015.
 */
public class ClipRetriever {
    private static final String URL = "http://ellotv.bigdig.com.ua/api/home/video";

    public List<Clip> getClipList() {
        List<Clip> clipList;
        String clipStr = getClipsAsJSON();
        clipList = parseJSON(clipStr);
        for (Clip clip : clipList) {
            clip.setPicture(retrieveImage(clip.getPictureURL()));
        }
        return clipList;
    }

    private String getClipsAsJSON() {
        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return StringEscapeUtils.unescapeJava(response.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Clip> parseJSON(String clipsJsonStr) {
        try {
            List<Clip> clipList = new ArrayList<Clip>();
            JSONObject jsonObject = new JSONObject(clipsJsonStr);


            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray items = data.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String title = item.getString("title");
                String pictureURL = item.getString("picture");

                List<String> artistList = new ArrayList<String>();
                JSONArray artists = item.getJSONArray("artists");
                for (int j = 0; j < artists.length(); j++) {
                    JSONObject artist = artists.getJSONObject(j);
                    artistList.add(artist.getString("name"));
                }
                Long view_count = item.getLong("view_count");
                Clip clip = new Clip(title, pictureURL, artistList, view_count);
                clipList.add(clip);
            }
            return clipList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Drawable retrieveImage(String pictureURL) {
        InputStream is = null;
        try {
            URL url = new URL(pictureURL);
            is = url.openStream();
            Drawable image = Drawable.createFromStream(is, "src");
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
