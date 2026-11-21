package com.animekuuuu.app

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimeApp()
        }
    }
}

val Background = Color(0xFF090914)
val CardColor = Color(0xFF17172A)
val Purple = Color(0xFF9B5CFF)
val Blue = Color(0xFF00C8FF)
val White = Color.White

data class Anime(
    val title: String,
    val genre: String,
    val videoId: String
)

val animeList = listOf(
    Anime("Demon Slayer", "Action • Fantasy", "dQw4w9WgXcQ"),
    Anime("Jujutsu Kaisen", "Action • Supernatural", "dQw4w9WgXcQ"),
    Anime("Solo Leveling", "Action • Adventure", "dQw4w9WgXcQ"),
    Anime("Naruto", "Action • Ninja", "dQw4w9WgXcQ"),
    Anime("One Piece", "Adventure • Comedy", "dQw4w9WgXcQ"),
    Anime("Attack on Titan", "Action • Drama", "dQw4w9WgXcQ"),
    Anime("Blue Lock", "Sports • Drama", "dQw4w9WgXcQ"),
    Anime("Chainsaw Man", "Action • Horror", "dQw4w9WgXcQ")
)

@Composable
fun AnimeApp() {
    var selectedAnime by remember { mutableStateOf<Anime?>(null) }
    var search by remember { mutableStateOf("") }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            if (selectedAnime == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "ANIMEKUUUU",
                        color = Purple,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "Anime favoritmu ada di sini",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Cari anime...")
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedBorderColor = Purple,
                            unfocusedBorderColor = Color.DarkGray,
                            focusedContainerColor = CardColor,
                            unfocusedContainerColor = CardColor
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Anime Populer",
                        color = White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            animeList.filter {
                                it.title.contains(search, ignoreCase = true)
                            }
                        ) { anime ->

                            AnimeCard(anime) {
                                selectedAnime = anime
                            }
                        }
                    }
                }
            } else {
                PlayerScreen(
                    anime = selectedAnime!!,
                    onBack = {
                        selectedAnime = null
                    }
                )
            }
        }
    }
}

@Composable
fun AnimeCard(
    anime: Anime,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(90.dp, 120.dp)
                .background(
                    Purple.copy(alpha = 0.3f),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "アニメ",
                color = White,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = anime.title,
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = anime.genre,
                color = Blue,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "▶ Tonton",
                color = Purple,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PlayerScreen(
    anime: Anime,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        Text(
            text = "← Kembali",
            color = Purple,
            modifier = Modifier
                .clickable { onBack() }
                .padding(16.dp)
        )

        Text(
            text = anime.title,
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = WebViewClient()

                    loadUrl(
                        "https://www.youtube.com/embed/${anime.videoId}"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pemutar Video",
            color = Blue,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = "Video demo. Untuk anime asli, gunakan video atau sumber streaming yang memiliki izin.",
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
