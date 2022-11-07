package uottawa.engineering.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.text.TextUtils;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseProducts;
    List<Product> products;
    ListView listViewProducts;
    EditText editTextName= findViewById(R.id.editTextName);
    EditText editTextPrice=findViewById(R.id.editTextPrice);

    protected void onStart(){
        super.onStart();
        databaseProducts.addValueEventListener(new ValueEventListener(){
           @Override
           public void onDataChange(DataSnapshot dataSnapshot){
                products.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Product product = postSnapshot.getValue(Product.class);
                    products.add(product);
                }

                ProductList productsAdapter = new ProductList(MainActivity.this, products);
                listViewProducts.setAdapter(productsAdapter);
           }
           @Override
            public void onCancelled(DatabaseError databaseError){

           }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseProducts=FirebaseDatabase.getInstance().getReference("products");
    }

    private void addProduct(){
        String name = editTextName.getText().toString().trim();
        double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
        if(!TextUtils.isEmpty(name)){
            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}