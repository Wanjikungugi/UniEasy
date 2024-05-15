package com.tonyxlh.documentscanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tonyxlh.documentscanner.ui.theme.DocumentScannerTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tonyxlh.documentscanner.model.Product
import com.tonyxlh.documentscanner.ui.theme.lBlue

class ViewAssignmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          ViewProductsScreen()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewProductsScreen() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        var context = LocalContext.current


        val emptyProductState = remember { mutableStateOf(Product("","","","","")) }
        var emptyProductsListState = remember { mutableStateListOf<Product>() }

        var products = allProducts(context,emptyProductState, emptyProductsListState)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBar(
                title = {
                    Text("Assignment", color = Color.White)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(lBlue),
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "", tint = Color.White)
                    }
                },
                actions = {
                    val context = LocalContext.current
                    IconButton(onClick = {
                        context.startActivity(Intent(context,MainActivity::class.java))
                        Log.d("DBR","clicked");

                    }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "", tint = Color.White)
                    }
                }
            )
            
            
            
            


            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(){
                items(products){
                    ProductItem(
                        name = it.name,
                        deadline = it.deadline,
                        grade = it.grade,
                        id = it.id,
                        productImage = it.imageUrl,
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(name:String, deadline:String, grade:String, id:String, productImage:String) {

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, fontSize = 20.sp)
        Text(text = deadline, fontSize = 20.sp)
        Text(text = grade, fontSize = 20.sp)


        Box (modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center){
            Image(
                painter = rememberAsyncImagePainter(productImage),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }

    }
}



fun allProducts(context:Context, product: MutableState<Product>, products: SnapshotStateList<Product>):SnapshotStateList<Product>{


    var ref = FirebaseDatabase.getInstance().getReference()
        .child("Products")

    ref.addValueEventListener(object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {




            products.clear()
            for (snap in snapshot.children){
                var retrievedProduct = snap.getValue(Product::class.java)
                product.value = retrievedProduct!!
                products.add(retrievedProduct)
            }

        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, "DB locked", Toast.LENGTH_SHORT).show()
        }
    })
    return products
}


@Composable
@Preview(showBackground = true)
fun ViewProductsScreenPreview(){

        ViewProductsScreen()

}