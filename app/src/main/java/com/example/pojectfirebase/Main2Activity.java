package com.example.pojectfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
   FirebaseAuth auth;
   DatabaseReference productdataset;
    DatabaseReference productdatasetset;
    ArrayList<Produit> listProduit;
    MyAdapterG myadapter;
    Bitmap image;
    int codeajouter=1;
    int codeannuler=0;
    int i;
    int j;
    String k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       listProduit= new ArrayList<Produit>();
        myadapter= new MyAdapterG(this,listProduit);
        GridView listView=(GridView) findViewById(R.id.Gridview);
        listView.setAdapter(myadapter);

        productdataset= FirebaseDatabase.getInstance().getReference("Produits");
        productdataset.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProduit.clear();
                for(DataSnapshot d :dataSnapshot.getChildren()){
                    Produit produit = d.getValue(Produit.class);

                    listProduit.add(produit);
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    //String to bitmap
    public static Bitmap StringtoBitmap(String photo)throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(photo, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray,0,decodedByteArray.length);
    }

    @Override
    //menu_add produit
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainproduit, menu);

        return true;
    }
    //click add produit
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.addNewListG){
            Intent intent = new Intent(this,AjouterProduitActivity.class) ;
            startActivityForResult(intent,1);
            // startActivity(intent);
        }
        return true;
    }

    @Override
    //back from next activity
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ajouter
        if (requestCode == 1 && resultCode == codeajouter) {
            //Récupérer un id pour le nouv produit
            String id =productdataset.push().getKey();
            //insérer le nouv produit

            productdataset.child(id).setValue(new Produit(id,data.getExtras().getString("libelleG"), data.getExtras().getString("descriptionG"),data.getExtras().get("picture").toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Main2Activity.this,"Produit ajouter avec succés",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Main2Activity.this,"onComplete"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
            myadapter.notifyDataSetChanged();
        }//modifier
        else if (requestCode == 2 && resultCode == codeajouter) {
            j=0;
            productdatasetset= FirebaseDatabase.getInstance().getReference().child("Produits");


            productdatasetset.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot d :dataSnapshot.getChildren()){
                        if(j==i){
                            productdatasetset.child(d.getKey()).setValue(new Produit(d.getKey(),data.getExtras().getString("libelleG"), data.getExtras().getString("descriptionG"),data.getExtras().get("picture").toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Main2Activity.this,"Produit modifier avec succés",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(Main2Activity.this,"onComplete"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            myadapter.notifyDataSetChanged();


                             }
                        j++;

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });







        }

    }

    private class MyAdapterG extends ArrayAdapter<Produit> {     // utilise adapter when we have list view
        private ArrayList<Produit>  listProduit ;
        private Activity context;
        // initialisation d'une list

        public MyAdapterG(Activity context,ArrayList<Produit> listProduit) {   //constrocteur
            super(context, R.layout.ma_cellule,listProduit);
            this.listProduit=listProduit;
            this.context=context;
        }

        @Override
        public int getCount() {
            return listProduit.size();
        }

        @Override
        public Produit getItem(int position) {   // getteur
            return listProduit.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater=context.getLayoutInflater() ;
                convertView=inflater.inflate(R.layout.ma_cellule,null);

            }
            final   Produit s = listProduit.get(position);

            final TextView itemName=(TextView)convertView.findViewById(R.id.nomg);
            itemName.setText(s.nom);
            final ImageView IDs=( ImageView)convertView.findViewById(R.id.imageView);
           if (s.image != ""){
            try {
                IDs.setImageBitmap(StringtoBitmap(s.image));
            } catch (IOException e) {
                e.printStackTrace();
            }}

           //supprimer
            Button sup = (Button)convertView.findViewById(R.id.supprimerG);
            sup.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    ;
                    productdataset.child(listProduit.get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError==null){
                                Toast.makeText(Main2Activity.this,"Produit supprimer avec succés",Toast.LENGTH_SHORT).show();
                            }else
                            { Toast.makeText(Main2Activity.this,"onComplete"+ databaseError.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    listProduit.remove(position);
                    myadapter.notifyDataSetChanged();

                }

            });
            //modifier
            Button modifier = (Button)convertView.findViewById(R.id.modifierG);
            modifier.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(Main2Activity.this,AjouterProduitActivity.class) ;
                    i=position;
                    startActivityForResult(intent,2);


                }
            });




            return convertView;
        }


    }
}
