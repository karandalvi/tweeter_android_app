package com.mobile_computing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

//Created by Karan
//This is the result display activity that pop ups from the main activity if the user selects an item

public class ResultDisplayActivity extends AppCompatActivity {

    Bundle myBundle;                    //to retrieve data received from main activity
    Datum item;                         //to store data on selected item
    ImageLoader imgLoad;                //to load image of the book
    NetworkImageView networkImageView;
    TextView textView;
    ToggleButton toggle;
    android.support.v7.app.ActionBar actionBar;  //to display the book title and provide back button functionality
    DBHandler myDBHandler;                      //to store favorites into SQLite database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        imgLoad = VolleySingleton.getInstance(getApplicationContext()).getImageLoader();
        myDBHandler = new DBHandler(this, null, null, 1);

        //retrieving information of the item selected on the main activity
        myBundle = getIntent().getExtras();
        item = new Datum(myBundle.getInt("m_id"),
                myBundle.getString("m_title"),
                myBundle.getString("m_date"),
                myBundle.getString("m_text"),
                myBundle.getString("m_imageUrl"));

        //setting the title to book name and adding back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("Book: " + item.title());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set the values as received from the main activity
        networkImageView = (NetworkImageView) findViewById(R.id.image);
        networkImageView.setImageUrl(item.imageUrl(), imgLoad);

        textView = (TextView) findViewById(R.id.title);
        textView.setText(item.title());

        textView = (TextView) findViewById(R.id.date);
        textView.setText(item.date());

        textView = (TextView) findViewById(R.id.text);
        textView.setText(item.text());

        toggle = (ToggleButton) findViewById(R.id.starButton);

        //make the text scrollable
        textView.setMovementMethod(new ScrollingMovementMethod());

        //code to insert & delete entry on toggle of star button
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
                    myDBHandler.addFavorite(item);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
                    myDBHandler.removeFavorite(item.id());
                }
             }});}


}
