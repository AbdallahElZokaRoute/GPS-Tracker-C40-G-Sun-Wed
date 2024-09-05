package com.route.gps_trackerc40gsunwed

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.route.gps_trackerc40gsunwed.ui.theme.GPSTrackerC40GSunWedTheme
import java.io.File

class CartCounterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPSTrackerC40GSunWedTheme {
                CartCounterContent()
            }
        }
    }
}

// Side Effect <->  Performance issues
@Composable
fun CartCounterContent(modifier: Modifier = Modifier) {
    // Stateless Composable         -> static and will never change
    // Stateful Composable          -> State -> and can change UI

    val namesList: List<String> = listOf("Ahmed", "Islam", "Mohamed")
    var counter by remember {
        mutableIntStateOf(0)
    }

    // Recomposition
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Big Cola",
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            UpdateCounterButton(text = "-", modifier = Modifier.padding(8.dp), onUpdateClick = {
                if (counter > 0)
                    counter--
                Log.e("TAG", "CartCounterContent: $counter")
            })
            Text(text = "$namesList")
            Text(text = "$counter")
            UpdateCounterButton(text = "+", modifier = Modifier.padding(8.dp), onUpdateClick = {
                counter++
                Log.e("TAG", "CartCounterContent: $counter")
            })


        }
    }
}
// lambda Expression == Function


val add: (num1: Int, num2: Int) -> Int = { num1: Int, num2: Int ->
    val result = num1 + num2
    result
}
//

@Composable
fun UpdateCounterButton(
    modifier: Modifier = Modifier,
    text: String,
    onUpdateClick: () -> Unit
) {
    Button(
        contentPadding = PaddingValues(0.dp),

        modifier = modifier.size(40.dp),
        onClick = {
            onUpdateClick()
        },
        shape = RoundedCornerShape(60.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Preview
@Composable
private fun UpdateCounterButtonPreview() {
    UpdateCounterButton(text = "+") {

    }

}

@Preview(showBackground = true)
@Composable
private fun CartCounterPreview() {
    CartCounterContent()
}
