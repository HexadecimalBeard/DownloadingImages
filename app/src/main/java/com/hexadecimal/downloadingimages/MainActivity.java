package com.hexadecimal.downloadingimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    public void downloadImage(View view){

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {               // indirilecek resmin adresini verdik
            myImage = task.execute("https://upload.wikimedia.org/wikipedia/tr/thumb/e/ed/Bart_Simpson.svg/200px-Bart_Simpson.svg.png").get();
            imageView.setImageBitmap(myImage);      // gelen resmi imageView ogesine bastırdık

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }
    //  indirme gibi uzun zaman alabilecek islemleri gene farklı bir thread altında gerceklestirdik
    //  normal thread altında gerceklestirmemiz uygulamanın islem bitene kadar baska islem yapamaması demektir

    public static class ImageDownloader extends AsyncTask<String, Void , Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);     // strings... urls birden fazla string tipinde url ögesi var demek, bir array gibi dusunulebilir
                                                // urls[0] ilk string tipindeki url'yi alir, birden fazla url oldugunda urls[1], urls[2]... kullan
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();           // baglantiyi kurduk

                InputStream in = connection.getInputStream();       // gelen verileri almak icin kullandik
                Bitmap myBitmap = BitmapFactory.decodeStream(in);   // bitmap'i indirmek icin kullandik
                return  myBitmap;                                   // indirilen bitmap myBitmap icinde geri donduruldu
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  null;
        }
    }
}
