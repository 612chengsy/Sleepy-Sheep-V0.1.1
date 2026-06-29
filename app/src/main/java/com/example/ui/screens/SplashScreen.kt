package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@Composable
fun SplashScreen(
    countdown: Int,
    onSkip: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SheepBackground)
    ) {
        // 背景云朵或插图
        Image(
            painter = painterResource(id = R.drawable.green_meadow_day_1782658846282),
            contentDescription = "启动页背景",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 顶部跳过广告按键
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                color = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onSkip() }
            ) {
                Text(
                    text = "跳过 $countdown s",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }

        // 中央品牌标识卡片
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
                .background(Color.White.copy(alpha = 0.88f), RoundedCornerShape(28.dp))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 可爱绵羊图标
            Image(
                painter = painterResource(id = R.drawable.sheep_pink_cute_1782658818849),
                contentDescription = "眠羊宠物图标",
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "眠羊",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = SheepTextPrimary
            )

            Text(
                text = "Sleeping sheep",
                fontSize = 14.sp,
                color = SheepTextSecondary,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "和小羊一起沉入松软的梦境",
                fontSize = 15.sp,
                color = SheepPrimary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        // 底部提示
        Text(
            text = "健康作息 · 智能助眠 · 电子云养羊",
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
        )
    }
}
