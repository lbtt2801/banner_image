package com.lbtt2801.bannerimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.lbtt2801.bannerimage.ui.theme.BannerImageTheme
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BannerImageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BannerImagePreview()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerImage(modifier: Modifier = Modifier, lstUrlImage: List<String>) {
    val pagerState = rememberPagerState(pageCount = { lstUrlImage.size })
    val coroutineScope = rememberCoroutineScope()

    Column {

        HorizontalPager(
            modifier = modifier.fillMaxWidth(),
            state = pagerState,
            userScrollEnabled = false,
            contentPadding = PaddingValues(horizontal = (LocalConfiguration.current.screenWidthDp.dp - 500.dp) / 2)
        ) { page ->
            AsyncImage(
                model = lstUrlImage[page],
                contentDescription = "Image ${page + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(300.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.7f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(lstUrlImage.size - 1)
                        }
                    }
            )
        }
    }

}

@Preview(device = Devices.PIXEL_4_XL, showBackground = true)
@Composable
fun BannerImagePreview() {
    val lstUrlImage = listOf(
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21105453/phong-canh-1.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21105259/phong-canh.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21105555/phong-canh-3.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21105809/phong-canh-4.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/10/28152553/leading-lines-composition-1.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21110437/gio-vang.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21110610/phong-canh-5.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21110851/phong-canh-6.jpg",
        "https://data.webnhiepanh.com/wp-content/uploads/2020/11/21111025/phong-canh-7.jpg"
    )

    BannerImage(lstUrlImage = lstUrlImage)
}