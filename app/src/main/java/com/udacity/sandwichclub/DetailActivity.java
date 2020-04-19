package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mDescription;
    private TextView mDescriptionLabel;
    private TextView mOrigin;
    private TextView mOriginLabel;
    private TextView mAka;
    private TextView mAkaLabel;
    private TextView mIngredients;
    private TextView mGgetIngredientsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        // get references to all UI elements
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mDescription = findViewById(R.id.description_tv);
        mDescriptionLabel = findViewById(R.id.detail_description_label);

        mOrigin = findViewById(R.id.origin_tv);
        mOriginLabel = findViewById(R.id.detail_place_of_origin_label);

        mAka = findViewById(R.id.also_known_tv);
        mAkaLabel = findViewById(R.id.detail_also_known_as_label);

        mIngredients = findViewById(R.id.ingredients_tv);
        mGgetIngredientsLabel = findViewById(R.id.detail_ingredients_label);

        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // read the sandwich ingredient data in from strings.xml
        //
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        // Retrieve the JSON ingredient data for the current sandwich
        //
        String json = sandwiches[position];

        // parse the ingredient data and return it as a Sandwich object
        //
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getDescription().isEmpty()) {
            mDescription.setVisibility(View.GONE);
            mDescriptionLabel.setVisibility(View.GONE);
        } else {
            mDescriptionLabel.setVisibility(View.VISIBLE);
            mDescriptionLabel.setVisibility(View.VISIBLE);
            mDescription.setText(sandwich.getDescription());
        }

        // Handle the visibility of the Origin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginLabel.setVisibility(View.GONE);
            mOrigin.setVisibility(View.GONE);
        } else {
            mOriginLabel.setVisibility(View.VISIBLE);
            mOrigin.setVisibility(View.VISIBLE);
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        // Handle the visibility of the AKAs list
        if (sandwich.getAlsoKnownAs().size() > 0) {
            mAka.setVisibility(View.VISIBLE);
            mAkaLabel.setVisibility(View.VISIBLE);
            for (String aka : sandwich.getAlsoKnownAs()) {
                mAka.append(aka);
            }
        } else {
            mAka.setVisibility(View.GONE);
            mAkaLabel.setVisibility(View.GONE);
        }

        // Handle the visibility of the ingredients list
        //
        if (sandwich.getIngredients().size() > 0) {
            mIngredients.setVisibility(View.VISIBLE);
            for (String ingredient : sandwich.getIngredients()) {
                mIngredients.append(ingredient);
            }
        } else {
            mIngredients.setVisibility(View.GONE);
        }
    }
}
