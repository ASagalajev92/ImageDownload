package com.devhuba.imagedownload;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Button vBtnDownload;

    String imageURL = "http://www.reklaamihai.ee/wp-content/uploads/2018/08/Pildid-2019-9.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageViewDown);
        vBtnDownload = (Button) findViewById(R.id.btnDownload);

    }

    public void onClickDownloadImage(View view) {
        DownloadImageTask task = new DownloadImageTask();
        Bitmap bitmap = null;
        try {
            bitmap = task.execute(imageURL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imgView.setImageBitmap(bitmap);
    }

    private static class DownloadImageTask extends AsyncTask<String,Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null; // Creating url
            HttpURLConnection urlConnection = null; //Creating connection
            try {
                //Standard connection settings
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                //If you download image You don't have long string. Sou we use Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream); // Converting our Stream to Bitmap format
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

}
