package com.example.cameratest2;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class ViewImage extends AppCompatActivity {

    private EditText editTextId;
    private Button buttonGetImage;
    private ImageView imageView;
    InputStream aa;
    private final String imageURL = "http://vakratundasystem.in/harsh/getImage.php?id=";
    private RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGetImage = (Button) findViewById(R.id.buttonGetImage);
        imageView = (ImageView) findViewById(R.id.imageViewShow);

        requestHandler = new RequestHandler();

        buttonGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
    }

    private void getImage() {
        String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String, Void, Bitmap> {
            ProgressDialog loading;
            ImageView bmImage;

            public GetImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewImage.this, "Downloading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imageView.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String url = imageURL + params[0];
                Bitmap image = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    image = BitmapFactory.decodeStream(in);
                } catch (MalformedURLException e) {
                    Log.e("Harsh", e.getMessage());
                } catch (IOException e) {
                    Log.e("Harsh", e.getMessage());
                }
                return image;
            }
        }

        GetImage gi = new GetImage(imageView);
        gi.execute(id);
    }
}
