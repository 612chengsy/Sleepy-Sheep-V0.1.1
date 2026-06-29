package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Sheep
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    sheep: Sheep,
    onExchange: (Int, String) -> Boolean
) {
    var showVipCenter by remember { mutableStateOf(false) }
    var showLoginModal by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("嗯哼") }
    var toastMsg by remember { mutableStateOf<String?>(null) }

    if (showVipCenter) {
        VipCenterView(
            sheep = sheep,
            onBack = { showVipCenter = false },
            onExchangeItem = { cost, name ->
                val success = onExchange(cost, name)
                toastMsg = if (success) "兑换成功！已为小羊穿上【$name】" else "羊羊币不足哦，快去多睡觉和小羊互动赚取吧~"
            },
            toastMsg = toastMsg,
            onClearToast = { toastMsg = null }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
        ) {
            // 顶部头像与注册登录区 (对应截图 圆形蓝色笑脸卡片 注册/登录 或 嗯哼)
            Surface(
                color = SheepCardBg,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = SheepPrimary,
                        modifier = Modifier.size(72.dp).clickable { showLoginModal = true }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (isLoggedIn) {
                                Icon(Icons.Default.SentimentVerySatisfied, contentDescription = "头像", tint = Color.White, modifier = Modifier.size(48.dp))
                            } else {
                                Icon(Icons.Default.Person, contentDescription = "未登录", tint = Color.White, modifier = Modifier.size(48.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column(modifier = Modifier.clickable { showLoginModal = true }) {
                        Text(
                            text = if (isLoggedIn) username else "注册/登录",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = SheepTextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isLoggedIn) "绵羊同步率 98% · 良好作息达人" else "点击登录同步小羊健康云端数据",
                            fontSize = 12.sp,
                            color = SheepTextSecondary
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 我的收藏/订单/地址 三合一快捷栏 (对应截图 云朵收藏 购物车订单 小屋地址)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ProfileShortcutIcon("我的收藏", Icons.Default.CloudQueue, SheepPrimary)
                        ProfileShortcutIcon("我的订单", Icons.Default.ShoppingCart, SheepPrimary)
                        ProfileShortcutIcon("我的地址", Icons.Default.HomeWork, SheepPrimary)
                    }
                }

                // 会员中心横幅卡片 (对应截图 会员中心 超多权益，任你挑选 点击进入)
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showVipCenter = true },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = SheepPrimary)
                    ) {
                        Row(
                            modifier = Modifier.padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("会员中心", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("超多权益，任你挑选 · 专属黑羊卡", fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
                            }
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color.White
                            ) {
                                Text(
                                    text = "点击进入",
                                    color = SheepPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // 功能列表区块 (设置、历史记录、意见反馈、权限说明)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = SheepCardBg)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ProfileMenuRow("设置", Icons.Default.Settings, onClick = { toastMsg = "设置功能：可修改通知、下载与监测灵敏度" })
                            ProfileMenuRow("历史记录", Icons.Default.History, onClick = {})
                            ProfileMenuRow("意见反馈", Icons.Default.Feedback, onClick = {})
                            ProfileMenuRow("权限申请与使用说明", Icons.Default.VerifiedUser, onClick = {})
                        }
                    }
                }

                // 退出当前账号按钮
                if (isLoggedIn) {
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { isLoggedIn = false },
                            colors = ButtonDefaults.buttonColors(containerColor = SheepPrimary),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("退出当前账号", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // 登录弹窗模拟
    if (showLoginModal) {
        AlertDialog(
            onDismissRequest = { showLoginModal = false },
            title = { Text("用户登录 · 眠羊", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("账号/手机号") })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = "••••••••", onValueChange = {}, label = { Text("密码") })
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("第三方一键登录支持：微信 / QQ / 微博", fontSize = 12.sp, color = SheepTextSecondary)
                }
            },
            confirmButton = {
                Button(onClick = {
                    isLoggedIn = true
                    showLoginModal = false
                }) { Text("一键登录") }
            },
            dismissButton = {
                TextButton(onClick = { showLoginModal = false }) { Text("取消") }
            }
        )
    }

    if (toastMsg != null) {
        LaunchedEffect(toastMsg) {
            kotlinx.coroutines.delay(2500)
            toastMsg = null
        }
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp), contentAlignment = Alignment.BottomCenter) {
            Surface(color = SheepTextPrimary, shape = RoundedCornerShape(16.dp)) {
                Text(toastMsg!!, color = Color.White, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
            }
        }
    }
}

@Composable
fun ProfileShortcutIcon(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { }) {
        Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.height(6.dp))
        Text(title, fontSize = 13.sp, color = SheepTextPrimary, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ProfileMenuRow(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = SheepTextPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(title, fontSize = 16.sp, color = SheepTextPrimary, fontWeight = FontWeight.Medium)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "右箭", tint = SheepTextSecondary)
    }
}

@Composable
fun VipCenterView(
    sheep: Sheep,
    onBack: () -> Unit,
    onExchangeItem: (Int, String) -> Unit,
    toastMsg: String?,
    onClearToast: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        // 返回Header
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "返回", tint = SheepTextPrimary)
            }
            Text("会员中心", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(modifier = Modifier.width(48.dp))
        }

        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // 黑胶唱片Banner (对应截图 节日快乐~ 失眠患者福音)
            item {
                Card(shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth().height(170.dp)) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.meditation_vinyl_1782658872879),
                            contentDescription = "唱片",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)))
                        Column(modifier = Modifier.align(Alignment.CenterEnd).padding(24.dp), horizontalAlignment = Alignment.End) {
                            Text("节日快乐~", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                            Text("失眠患者专属福音", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        }
                    }
                }
            }

            // 道具与经验Tab栏 (对应截图 我的羊毛道具 | 我的经验值)
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Surface(color = SheepCardBg, shape = RoundedCornerShape(12.dp), modifier = Modifier.weight(1f)) {
                        Text("羊毛币余额：${sheep.sheepCoins}", color = SheepPrimary, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(12.dp))
                    }
                    Surface(color = SheepCardBg, shape = RoundedCornerShape(12.dp), modifier = Modifier.weight(1f)) {
                        Text("经验值：${sheep.exp}/${sheep.maxExp}", color = SheepTextPrimary, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(12.dp))
                    }
                }
            }

            // 兑换福利标题
            item {
                Text("兑换福利 (黑羊会员特权)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
            }

            // 福利列表 (对应截图 羊羊小袄 2000羊兑换、8g褪黑素、葡萄味软糖)
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    VipRewardItem("羊羊粉小袄", "2000羊兑换", Icons.Default.Checkroom) { onExchangeItem(2000, "羊羊粉小袄") }
                    VipRewardItem("8g褪黑素", "1500羊兑换", Icons.Default.Medication) { onExchangeItem(1500, "8g褪黑素") }
                    VipRewardItem("助眠软糖礼包", "1000羊兑换", Icons.Default.CardGiftcard) { onExchangeItem(1000, "助眠软糖") }
                }
            }
        }
    }
}

@Composable
fun VipRewardItem(name: String, priceText: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(100.dp).clickable { onClick() }) {
        Surface(shape = RoundedCornerShape(16.dp), color = SheepCardBg, modifier = Modifier.size(80.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = name, tint = SheepPrimary, modifier = Modifier.size(40.dp))
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary, maxLines = 1)
        Text(priceText, fontSize = 11.sp, color = SheepAccentYellow, fontWeight = FontWeight.Bold)
    }
}
