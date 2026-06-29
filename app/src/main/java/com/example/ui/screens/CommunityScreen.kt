package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PostItem
import com.example.ui.theme.*

@Composable
fun CommunityScreen(
    stressLevel: Int,
    adviceList: List<String>,
    posts: List<PostItem>,
    onUpdateStress: (Int) -> Unit,
    onLikePost: (Int) -> Unit,
    onAddPost: (String) -> Unit
) {
    var tab by remember { mutableStateOf("压力中心") } // 压力中心, 梦话树洞
    var newPostText by remember { mutableStateOf("") }
    var showInput by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SheepBackground)
            .statusBarsPadding()
    ) {
        // 顶部切换Tab
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = SheepCardBg,
                modifier = Modifier.width(240.dp)
            ) {
                Row {
                    listOf("压力中心", "梦话树洞").forEach { t ->
                        val selected = tab == t
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(24.dp))
                                .background(if (selected) SheepPrimary else Color.Transparent)
                                .clickable { tab = t }
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = t,
                                color = if (selected) Color.White else SheepTextPrimary,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }

        if (tab == "压力中心") {
            // 压力中心对应截图“请滑动选择你的压力等级 你的压力等级是4级 减压建议”
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text("请滑动选择你的压力等级", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)

                Spacer(modifier = Modifier.height(20.dp))

                Slider(
                    value = stressLevel.toFloat(),
                    onValueChange = { onUpdateStress(it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = SheepPrimary,
                        activeTrackColor = SheepPrimary,
                        inactiveTrackColor = SheepCardBg
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("1 (极低)", fontSize = 12.sp, color = SheepTextSecondary)
                    Text("4 (适中)", fontSize = 12.sp, color = SheepTextSecondary)
                    Text("10 (极高)", fontSize = 12.sp, color = SheepTextSecondary)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 当前压力等级展示横幅卡片
                Surface(
                    color = SheepPrimary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "你的压力等级是${stressLevel}级",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 建议卡片
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SheepCardBg)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("减压建议", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                        Spacer(modifier = Modifier.height(12.dp))
                        adviceList.forEach { adv ->
                            Text(text = adv, fontSize = 14.sp, color = SheepTextPrimary, lineHeight = 22.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        } else {
            // 梦话树洞
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("昨晚睡眠排行记录 >", fontSize = 13.sp, color = SheepTextSecondary)
                    Button(
                        onClick = { showInput = !showInput },
                        shape = CircleShape,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SheepSecondary)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "记录", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("倾诉心声/梦话", fontSize = 13.sp)
                    }
                }

                AnimatedVisibility(visible = showInput) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = newPostText,
                                onValueChange = { newPostText = it },
                                placeholder = { Text("记录昨晚的梦话，或者写下压在心头的烦恼，小羊AI会治愈你...") },
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 4
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Button(onClick = {
                                    onAddPost(newPostText)
                                    newPostText = ""
                                    showInput = false
                                }) {
                                    Text("发布到树洞")
                                }
                            }
                        }
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(posts) { post ->
                        PostCard(post = post, onLike = { onLikePost(post.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun PostCard(post: PostItem, onLike: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = CircleShape, color = SheepCardBg, modifier = Modifier.size(36.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(post.author.take(1), fontWeight = FontWeight.Bold, color = SheepPrimary)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(post.author, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(post.time, fontSize = 11.sp, color = SheepTextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (post.isAudio) {
                // 模拟录音条
                Surface(
                    color = SheepCardBg,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth().clickable { /* 播放梦话 */ }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "播放音频", tint = SheepPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("梦话音频录音 · ${post.audioDuration}", color = SheepPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Icon(Icons.Default.GraphicEq, contentDescription = "波形", tint = SheepPrimary)
                    }
                }
                if (post.content.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(post.content, fontSize = 13.sp, color = SheepTextSecondary)
                }
            } else {
                Text(post.content, fontSize = 14.sp, color = SheepTextPrimary, lineHeight = 20.sp)
            }

            // AI智能回应
            if (post.aiReply != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(color = SheepBackground, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Psychology, contentDescription = "AI", tint = SheepPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(post.aiReply!!, fontSize = 13.sp, color = SheepPrimary, lineHeight = 18.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 底部点赞分享栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onLike() }) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "赞",
                        tint = if (post.isLiked) SheepTertiary else SheepTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${post.likes}", fontSize = 12.sp, color = SheepTextSecondary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.StarBorder, contentDescription = "收藏", tint = SheepTextSecondary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Share, contentDescription = "分享", tint = SheepTextSecondary, modifier = Modifier.size(18.dp))
            }
        }
    }
}
