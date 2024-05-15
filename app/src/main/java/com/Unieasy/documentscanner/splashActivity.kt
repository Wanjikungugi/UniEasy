package com.tonyxlh.documentscanner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Unieasy.documentscanner.institution
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tonyxlh.documentscanner.ui.theme.lBlue

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySplash()


        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MySplash(){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        val mContext = LocalContext.current
        val coroutine = rememberCoroutineScope()
        coroutine.launch {
            delay(3000)
            mContext.startActivity(Intent(mContext,institution::class.java))

        }
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            //lottie Animation
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.unieasy))
            val progress by animateLottieCompositionAsState(composition)
            LottieAnimation(composition, progress,
                modifier = Modifier.size(300.dp))
            //end of Lottie Animation
        }
        Text(text = "UniEasy",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive)


    }

}

@Preview(showBackground = true)
@Composable
fun MySplashPreview() {
    MySplash()
}
