package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Verify that a string is a JSONObject by trying to instantiate a new
     * JSONObject.
     *
     * @param json
     * @return JSONobject or null
     */
    private static JSONObject verifyJSON(String json) {
        JSONObject details = null;

        // create a new JSONObject with the passed json data
        // return null on a parse failure
        //
        try {
            details = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return details;
    }

    private static String getSandwichName(JSONObject details) {

        String s = "";

        try {
            JSONObject sandwichNames = details.getJSONObject("name");
            s = sandwichNames.getString("mainName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getSandwichDescription(JSONObject details) {

        String s = "";

        try {
            s = details.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getSandwichImage(JSONObject details) {

        String s = "";

        try {
            s = details.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getSandwichOrigin(JSONObject details) {
        String s = "";

        try {
            s = details.getString("placeOfOrigin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static List<String> getSandwichIngredients(JSONObject details) {
        List<String> ingredientsList = new ArrayList<String>();

        try {
            JSONArray ingredients = details.getJSONArray("ingredients");
            int len = ingredients.length();
            for (int i = 0; i < len; i++) {
                if (i < len - 1) {
                    ingredientsList.add(ingredients.getString(i) + "\n");
                } else {
                    ingredientsList.add(ingredients.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredientsList;
    }

    private static List<String> getSandwichAlsoKnownAs(JSONObject details) {
        List<String> akaList = new ArrayList<String>();

        try {
            JSONObject name = details.getJSONObject("name");
            JSONArray akas = name.getJSONArray("alsoKnownAs");
            int len = akas.length();
            for (int i = 0; i < len; i++) {
                if (i < len - 1) {
                    akaList.add(akas.getString(i) + "\n");
                } else {
                    akaList.add(akas.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return akaList;
    }

    /**
     * Create a sandwich object from a JSONObject that contains the details on
     * how to create such a delicious masterpiece.
     *
     * @param details
     * @return
     */
    private static Sandwich createSandwichFromDetails(JSONObject details) {

        Sandwich sandwich = null;

        sandwich = new Sandwich(
                getSandwichName(details),
                getSandwichAlsoKnownAs(details),
                getSandwichOrigin(details),
                getSandwichDescription(details),
                getSandwichImage(details),
                getSandwichIngredients(details)
        );

        return sandwich;
    }

    public static Sandwich parseSandwichJson(String json) {

        // create the and verify the details object
        JSONObject details = verifyJSON(json);

        // create the sandwich
        Sandwich sandwich = createSandwichFromDetails(details);

        // return the sandwich
        return sandwich;
    }
}
