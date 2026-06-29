package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MusicItem
import com.example.model.Sheep
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    sheep: Sheep,
    musicList: List<MusicItem>,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    onPlayMusic: (MusicItem) -> Unit,
    onInteractSheep: () -> Unit
) {
    var showSheepSpeech by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SheepBackground)
    ) {
        // 顶部品牌Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = SheepTextPrimary,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "绵羊图标",
                        tint = Color.White,
                        modifier = Modifier.padding(6.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "眠羊", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = SheepTextPrimary)
                    Text(text = "Sleeping sheep", fontSize = 10.sp, color = SheepTextSecondary, fontWeight = FontWeight.Bold)
                }
            }

            // 搜索框或快捷输入
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                tonalElevation = 2.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .height(36.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "搜索", tint = SheepTextSecondary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "失眠专业音乐", color = SheepTextSecondary, fontSize = 13.sp)
                }
            }

            IconButton(onClick = { /* 筛选事件 */ }) {
                Icon(Icons.Default.Menu, contentDescription = "菜单", tint = SheepTextPrimary)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // 云养绵羊互动区
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            showSheepSpeech = !showSheepSpeech
                            onInteractSheep()
                        },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SheepCardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.green_meadow_day_1782658846282),
                            contentDescription = "草地背景",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // 小绵羊形象
                        Image(
                            painter = painterResource(id = R.drawable.sheep_pink_cute_1782658818849),
                            contentDescription = "小绵羊",
                            modifier = Modifier
                                .size(110.dp)
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp)
                        )

                        // 左上角状态标签
                        Surface(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "互动宠物 · ${sheep.name} (Lv.${sheep.level})",
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        // 右上角金币与心情
                        Column(
                            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Surface(color = SheepAccentYellow, shape = RoundedCornerShape(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Star, contentDescription = "金币", tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "${sheep.sheepCoins}", color = SheepTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // 对话气泡
                        if (showSheepSpeech || sheep.mood.isNotEmpty()) {
                            Surface(
                                color = Color.White.copy(alpha = 0.95f),
                                shape = RoundedCornerShape(16.dp),
                                shadowElevation = 4.dp,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 40.dp)
                            ) {
                                Text(
                                    text = "咩~ 身体健康:${sheep.health}分! 心情:${sheep.mood}",
                                    color = SheepPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }

                        Text(
                            text = "点击与小绵羊同步作息",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp)
                        )
                    }
                }
            }

            // 精选推文Banner大卡片
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(180.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.meditation_vinyl_1782658872879),
                            contentDescription = "推荐专题",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.35f))
                        )
                        Surface(
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "浅睡者福音 · 眠羊APP",
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "声临其境（改善睡眠）",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "带你走近自然，沉浸于松软脑波中",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // 音乐分类Tab
            item {
                val categories = listOf("松弛", "专注", "提升", "提神", "焦虑")
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { onSelectCategory(cat) }
                        ) {
                            Text(
                                text = cat,
                                fontSize = if (isSelected) 18.sp else 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) SheepTextPrimary else SheepTextSecondary
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(width = 24.dp, height = 3.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(SheepPrimary)
                                )
                            }
                        }
                    }
                }
            }

            // 白噪音与助眠音乐列表标题
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "精选助眠音乐", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                    Text(text = "自由搭配 >", fontSize = 13.sp, color = SheepPrimary)
                }
            }

            // 音乐项列表
            val filteredMusic = musicList.filter { it.category == selectedCategory || selectedCategory == "松弛" }
            items(filteredMusic) { music ->
                MusicListItem(music = music, onPlay = { onPlayMusic(music) })
            }
        }
    }
}

@Composable
fun MusicListItem(music: MusicItem, onPlay: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onPlay() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧小封面
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SheepCardBg,
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (music.isPlaying) Icons.Default.PlayArrow else Icons.Default.Notifications,
                        contentDescription = "音乐",
                        tint = SheepPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = music.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SheepTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${music.duration} · ${music.subtitle}",
                    fontSize = 12.sp,
                    color = SheepTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // 播放状态按键
            IconButton(
                onClick = onPlay,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (music.isPlaying) SheepPrimary else SheepCardBg)
            ) {
                Icon(
                    imageVector = if (music.isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                    contentDescription = "播放",
                    tint = if (music.isPlaying) Color.White else SheepPrimary
                )
            }
        }
    }
}
