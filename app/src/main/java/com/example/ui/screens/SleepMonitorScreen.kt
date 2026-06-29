package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessLow
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.ui.theme.SheepAccentYellow
import com.example.ui.theme.SheepPrimary

@Composable
fun SleepMonitorScreen(
    onWakeUp: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1427)) // 深邃夜空黑
    ) {
        // 背景星空
        Image(
            painter = painterResource(id = R.drawable.night_sky_house_1782658831334),
            contentDescription = "星空背景",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.45f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 顶部提示与夜间时钟
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "夜间睡眠监测模式",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "23:45",
                    color = Color.White,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.ExtraLight
                )
                Text(
                    text = "智能闹钟将于明天 07:45 唤醒",
                    color = SheepAccentYellow,
                    fontSize = 13.sp
                )
            }

            // 中央熟睡的小羊
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.sheep_pink_cute_1782658818849),
                    contentDescription = "熟睡小羊",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape),
                    alpha = 0.85f
                )
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.MusicNote, contentDescription = "白噪音", tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("助眠自然脑波音正在播放 (45Hz)", color = Color.White, fontSize = 12.sp)
                    }
                }
            }

            // 底部起床按键
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "小羊正在和你同步呼吸，请安心入睡",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onWakeUp,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = SheepAccentYellow),
                    modifier = Modifier
                        .size(width = 160.dp, height = 54.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WbSunny, contentDescription = "起床", tint = Color(0xFF0C1427))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "早安起床",
                            color = Color(0xFF0C1427),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
