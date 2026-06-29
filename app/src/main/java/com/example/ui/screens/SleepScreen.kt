package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.model.Sheep
import com.example.model.SleepReport
import com.example.ui.theme.*

@Composable
fun SleepScreen(
    sheep: Sheep,
    report: SleepReport,
    onStartSleep: () -> Unit,
    onInteractSheep: () -> Unit
) {
    var subTab by remember { mutableStateOf("智能闹钟") } // 智能闹钟, 小憩, 睡眠报告, 青青草原
    var vibrationOn by remember { mutableStateOf(true) }
    var snoozeOn by remember { mutableStateOf(true) }
    var napMinutes by remember { mutableStateOf(20) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SheepBackground)
            .statusBarsPadding()
    ) {
        // 顶部子Tab切换 (小憩/智能闹钟/报告/青青草原)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("智能闹钟", "小憩", "睡眠报告", "青青草原").forEach { tab ->
                val selected = subTab == tab
                Surface(
                    color = if (selected) SheepPrimary else Color.White,
                    shape = RoundedCornerShape(20.dp),
                    tonalElevation = if (selected) 4.dp else 1.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { subTab = tab }
                ) {
                    Text(
                        text = tab,
                        color = if (selected) Color.White else SheepTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (subTab) {
                "智能闹钟" -> {
                    // 就寝时间与起床时间Header
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text("10:45", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                                Text(" 下午", fontSize = 14.sp, color = SheepTextSecondary, modifier = Modifier.padding(bottom = 6.dp))
                            }
                            Text("就寝时间", fontSize = 13.sp, color = SheepTextSecondary)
                        }

                        Box(modifier = Modifier.width(1.dp).height(45.dp).background(Color.LightGray.copy(alpha = 0.5f)))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text("07:45", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                                Text(" 上午", fontSize = 14.sp, color = SheepTextSecondary, modifier = Modifier.padding(bottom = 6.dp))
                            }
                            Text("起床时间", fontSize = 13.sp, color = SheepTextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 中央圆形大表盘闹钟
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(230.dp)
                            .border(width = 3.dp, color = SheepPrimary, shape = CircleShape)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text("09:28", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, color = SheepTextPrimary)
                                Text(" 下午", fontSize = 14.sp, color = SheepTextSecondary, modifier = Modifier.padding(bottom = 8.dp))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("闹钟已开", fontSize = 14.sp, color = SheepTextSecondary)
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(shape = CircleShape, color = SheepCardBg) {
                                Icon(Icons.Default.DateRange, contentDescription = "闹钟", tint = SheepPrimary, modifier = Modifier.padding(6.dp).size(18.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 睡觉大按键
                    Button(
                        onClick = onStartSleep,
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SheepPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = "睡觉", tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("睡觉", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 底部设置参数卡片
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = SheepCardBg)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            SettingRow("铃声", "冥想音乐", Icons.Default.Notifications)
                            HorizontalDivider(color = Color.White, thickness = 1.dp)
                            SettingRow("重复", "3min", Icons.Default.Refresh)
                            HorizontalDivider(color = Color.White, thickness = 1.dp)
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("震动", fontSize = 15.sp, color = SheepTextPrimary, fontWeight = FontWeight.Medium)
                                Switch(
                                    checked = vibrationOn,
                                    onCheckedChange = { vibrationOn = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SheepPrimary)
                                )
                            }
                            HorizontalDivider(color = Color.White, thickness = 1.dp)
                            SettingRow("备注", "无", Icons.Default.List)
                            HorizontalDivider(color = Color.White, thickness = 1.dp)
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("再睡一会 (5min)", fontSize = 15.sp, color = SheepTextPrimary, fontWeight = FontWeight.Medium)
                                Switch(
                                    checked = snoozeOn,
                                    onCheckedChange = { snoozeOn = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SheepPrimary)
                                )
                            }
                        }
                    }
                }

                "小憩" -> {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("午后能量补给站", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                            Text("短时间快充小憩，小羊陪你静心20分钟", fontSize = 13.sp, color = SheepTextSecondary)

                            Spacer(modifier = Modifier.height(24.dp))

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(180.dp).clip(CircleShape).background(SheepCardBg)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("$napMinutes:00", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = SheepPrimary)
                                    Text("分钟倒计时", fontSize = 12.sp, color = SheepTextSecondary)
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                listOf(15, 20, 30, 45).forEach { min ->
                                    OutlinedButton(
                                        onClick = { napMinutes = min },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = if (napMinutes == min) SheepPrimary else Color.Transparent,
                                            contentColor = if (napMinutes == min) Color.White else SheepPrimary
                                        )
                                    ) {
                                        Text("${min}分")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = onStartSleep,
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SheepSecondary),
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                            ) {
                                Text("开始小憩", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }

                "睡眠报告" -> {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("昨日睡眠报告", fontSize = 16.sp, color = SheepTextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("${report.score}", fontSize = 64.sp, fontWeight = FontWeight.ExtraBold, color = SheepPrimary)
                            Text("昨晚睡眠得分", fontSize = 14.sp, color = SheepTextSecondary)

                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(color = SheepCardBg, shape = RoundedCornerShape(12.dp)) {
                                Text(
                                    text = report.defeatedPercent,
                                    color = SheepPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                ReportMetricItem("深度睡眠", "${report.deepSleepPercent}%", Color(0xFF0984E3))
                                ReportMetricItem("总时长", "${report.totalHours}h", SheepSecondary)
                                ReportMetricItem("醒来次数", "1 次", SheepTertiary)
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "小羊建议：${report.advice}", fontSize = 13.sp, color = SheepTextPrimary, textAlign = TextAlign.Center)

                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(onClick = { /* 分享海报 */ }, modifier = Modifier.fillMaxWidth()) {
                                Icon(Icons.Default.Share, contentDescription = "分享", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("生成我的五月报告海报")
                            }
                        }
                    }
                }

                "青青草原" -> {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(420.dp).padding(vertical = 8.dp),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.night_sky_house_1782658831334),
                                contentDescription = "庭院背景",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // 小绵羊
                            Image(
                                painter = painterResource(id = R.drawable.sheep_pink_cute_1782658818849),
                                contentDescription = "我的小绵羊",
                                modifier = Modifier
                                    .size(140.dp)
                                    .align(Alignment.Center)
                                    .clickable { onInteractSheep() }
                            )

                            // 左侧功能列
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.85f)) {
                                    Icon(Icons.Default.Favorite, contentDescription = "喂食", tint = SheepPrimary, modifier = Modifier.padding(10.dp))
                                }
                                Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.85f)) {
                                    Icon(Icons.Default.Star, contentDescription = "装扮", tint = SheepTertiary, modifier = Modifier.padding(10.dp))
                                }
                            }

                            Text(
                                text = "青青草原 · ${sheep.name}装扮室",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp)
                            )

                            Surface(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = "饱食度:${sheep.hunger}% · 当前装扮:${sheep.outfit}",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingRow(title: String, valText: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = SheepPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, fontSize = 15.sp, color = SheepTextPrimary, fontWeight = FontWeight.Medium)
        }
        Text(valText, fontSize = 14.sp, color = SheepTextSecondary)
    }
}

@Composable
fun ReportMetricItem(label: String, valText: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(valText, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
        Text(label, fontSize = 12.sp, color = SheepTextSecondary)
    }
}
