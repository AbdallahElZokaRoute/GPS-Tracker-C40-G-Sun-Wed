package com.route.gps_trackerc40gsunwed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.route.gps_trackerc40gsunwed.ui.theme.GPSTrackerC40GSunWedTheme

//        XML              : AppCompat Activity
//  Jetpack Compose        : ComponentActivity
class FirstComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        setContent {
            FacebookPostItem()
        }
    }
}
// Jetpack Compose States
// Lambda Expressions
// Eager vs Lazy initialization 

// News App (Compose)
// Chat App (Compose)
// E-Commerce (Compose or XML )

@Composable
fun FacebookPostItem(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.padding(8.dp)) {
        val (profilePicture, userName, time, postContent, postImage, privacy, like, comment, share) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ic_accessibility),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .constrainAs(profilePicture) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .background(Color.Gray, shape = CircleShape)
        )
        Text(
            text = "Ahmed Mohamed",
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .constrainAs(userName) {
                    top.linkTo(parent.top)
                    start.linkTo(profilePicture.end)
                },
            fontSize = 22.sp
        )
        Text(
            text = "2 Hrs", modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)

                .constrainAs(time) {
                    top.linkTo(userName.bottom)
                    start.linkTo(profilePicture.end)
                }, fontSize = 10.sp
        )
        Image(
            painter = painterResource(id = R.drawable.ic_privacy),
            contentDescription = "Icon Public Privacy",
            modifier = Modifier
                .size(16.dp)
                .constrainAs(privacy) {
                    top.linkTo(time.top)
                    start.linkTo(time.end)
                    bottom.linkTo(time.bottom)
                }
        )
        Text(
            text = LoremIpsum(50).values.iterator().next(),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .constrainAs(postContent) {
                    top.linkTo(time.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Post image ",
            modifier = Modifier
                .constrainAs(postImage) {
                    top.linkTo(postContent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
            // scaleType = fitXY , centerCrop
        )
        FacebookReactionButton(
            facebookReaction = FacebookReaction("Like"),
            modifier = Modifier.constrainAs(like) {
                top.linkTo(postImage.bottom)
                start.linkTo(parent.start)
            })
        FacebookReactionButton(
            facebookReaction = FacebookReaction("Comment", R.drawable.ic_comment),
            modifier = Modifier.constrainAs(comment) {
                top.linkTo(postImage.bottom)
                start.linkTo(like.end)
                end.linkTo(share.start)
            })
        FacebookReactionButton(
            facebookReaction = FacebookReaction(stringResource(R.string.share)),
            modifier = Modifier.constrainAs(share) {
                top.linkTo(postImage.bottom)
                start.linkTo(comment.end)
                end.linkTo(parent.end)
            })
    }
}

@Composable
fun FacebookReactionButton(facebookReaction: FacebookReaction, modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Gray
        )
    ) {
        Image(
            painter = painterResource(id = facebookReaction.icon ?: R.drawable.ic_like),
            contentDescription = "facebook reaction",
            modifier
                .padding(horizontal = 6.dp)
                .size(24.dp)
        )
        Text(facebookReaction.title ?: "")
    }
}

@Preview
@Composable
private fun FacebookReactionButtonPreview() {
    FacebookReactionButton(FacebookReaction("Like", null))
}

@Preview(showBackground = true)
@Composable
private fun FacebookPostItemPreview() {
    FacebookPostItem()
}

@Composable
fun SettingsList(modifier: Modifier = Modifier) {
    val settings = Settings(
        "Wifi And Data & Other settings",
        desc = "This option to handle Connected Wifi and other settings",
        imageId = R.drawable.ic_wifi
    )
    // ListView vs RecyclerView
    LazyRow() {
        items(500) {
            // OnCreateViewHolder & onBindViewHolder
            SettingsItem(settings = settings)
        }
    }
}

@Composable
fun SettingsItem(settings: Settings, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = settings.imageId ?: R.drawable.ic_accessibility),
            contentDescription = "Settings Icon"
        )
        Column {
            Text(text = settings.title ?: "", fontSize = 16.sp)
            Text(text = settings.desc ?: "", fontSize = 10.sp)
        }
    }
}


@Composable
private fun SettingsItemPreview() {
    SettingsItem(
        settings = Settings(
            "Wifi And Data & Other settings",
            desc = "This option to handle Connected Wifi and other settings",
            imageId = R.drawable.ic_wifi
        )
    )
}

@Composable
fun GreetingList(modifier: Modifier = Modifier) {
    //  Containers =>  (Column)
    //                 (Row)
    //                 (Box)
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Greeting(name = "Mohamed")
        Greeting(name = "Ahmed")
        Greeting(name = "Aya")
    }

}


@Composable
private fun GreetingListPreview() {
    GreetingList()
}

@Composable
fun Greeting(name: String) {
    // Modifier
    // android:layout_width=""


    Text(
        text = "Hello $name",
        modifier = Modifier, textAlign = TextAlign.Center,
        fontSize = 16.sp,
        color = Color.Black
    )
}


@Composable
fun GreetingPreview() {
    Greeting("Ahmed")
}
