package com.example.pojectfirebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class AjouterProduitActivity extends AppCompatActivity {
    EditText libelleG;
    EditText descriptionG;
    int codeajouter=1;
    int codeannuler=0;
    String libG,descG;
    ImageView image;
    Bitmap img2;
    Bitmap picture;
    boolean take=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);
        libelleG=(EditText)findViewById(R.id.libelleG);
        descriptionG=(EditText)findViewById(R.id.descriptionG);
        image=(ImageView)findViewById(R.id.picture) ;

        if(ContextCompat.checkSelfPermission(AjouterProduitActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AjouterProduitActivity.this,new String[]{Manifest.permission.CAMERA},100);
        }
    }

    public void takepicture(View view) {
        take=true;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            picture = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(picture);
        }
    }
 //button annuler
    public void annulerg(View view) {
        Intent intent = new Intent();
        this.setResult(codeannuler,intent);
        this.finish();
    }
    public static  String bitmaptoString(Bitmap bitmap){
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        return Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);

    }
  //button ajouter
    public void ajouterg(View view) {
        libG= libelleG.getText().toString();
        descG= descriptionG.getText().toString();


        Intent intent = new Intent();
        intent.putExtra("libelleG", libG);
        if(take==false){
            intent.putExtra("picture", "");
        }else{
        intent.putExtra("picture", bitmaptoString(picture));}

        intent.putExtra("descriptionG",descG);
        this.setResult(codeajouter,intent);
        this.finish();
    }
}
