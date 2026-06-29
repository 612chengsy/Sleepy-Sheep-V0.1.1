package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.model.StoreProduct
import com.example.ui.theme.*

@Composable
fun StoreScreen(
    products: List<StoreProduct>,
    selectedProduct: StoreProduct?,
    onSelectProduct: (StoreProduct) -> Unit,
    onCloseDetail: () -> Unit,
    onToggleFavorite: (StoreProduct) -> Unit
) {
    if (selectedProduct != null) {
        ProductDetailScreen(
            product = selectedProduct,
            onBack = onCloseDetail,
            onFavorite = { onToggleFavorite(selectedProduct) }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
        ) {
            // 顶部搜索与购物车 Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Transparent,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "搜索", tint = SheepTextSecondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("搜索助眠好物或医生专栏...", color = SheepTextSecondary, fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = { /* 打开购物车 */ }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "购物车", tint = SheepPrimary, modifier = Modifier.size(26.dp))
                }
            }

            // 标题
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("助眠产品", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                Text("SLEEP AID PRODUCTS", fontSize = 11.sp, color = SheepTextSecondary, fontWeight = FontWeight.Bold)
            }

            // 4个分类图标格 (对应截图 定时/耳塞/声波/枕头)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StoreCategoryIcon("定时睡眠", Icons.Default.Timer, SheepCardBg)
                StoreCategoryIcon("隔音耳塞", Icons.Default.Headphones, SheepCardBg)
                StoreCategoryIcon("白波频段", Icons.Default.GraphicEq, SheepCardBg)
                StoreCategoryIcon("心理问诊", Icons.Default.LocalHospital, SheepCardBg)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 商品网格
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(products) { prod ->
                    ProductGridCard(product = prod, onClick = { onSelectProduct(prod) })
                }
            }

            // 底部收藏专区展示
            val favs = products.filter { it.isFavorite }
            if (favs.isNotEmpty()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("收藏的商品", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    favs.forEach { f ->
                        Surface(
                            color = SheepBackground,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectProduct(f) }
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CardGiftcard, contentDescription = "品", tint = SheepPrimary)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text("${f.name} · ${f.price}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }
                                Icon(Icons.Default.Favorite, contentDescription = "爱", tint = SheepTertiary, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoreCategoryIcon(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, bg: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(shape = RoundedCornerShape(16.dp), color = bg, modifier = Modifier.size(54.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = name, tint = SheepPrimary, modifier = Modifier.size(28.dp))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(name, fontSize = 11.sp, color = SheepTextPrimary)
    }
}

@Composable
fun ProductGridCard(product: StoreProduct, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SheepCardBg),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(130.dp), contentAlignment = Alignment.Center) {
                if (product.imageResName == "sleep_pillow_product") {
                    Image(
                        painter = painterResource(id = R.drawable.sleep_pillow_product_1782658859230),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(Icons.Default.Bed, contentDescription = "商品", tint = SheepPrimary, modifier = Modifier.size(64.dp))
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SheepPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(product.price, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SheepTextPrimary)
                    Surface(shape = CircleShape, color = SheepPrimary, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.AddShoppingCart, contentDescription = "加", tint = Color.White, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    product: StoreProduct,
    onBack: () -> Unit,
    onFavorite: () -> Unit
) {
    var showBuyToast by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SheepCardBg)
            .statusBarsPadding()
    ) {
        // 顶部导航栏 (对应截图 左侧箭头 产品详情 右侧爱心)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回", tint = SheepPrimary)
            }
            Surface(shape = RoundedCornerShape(20.dp), color = Color.White.copy(alpha = 0.8f)) {
                Text("产品详情", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp))
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "收藏",
                    tint = if (product.isFavorite) SheepTertiary else SheepPrimary
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(androidx.compose.foundation.rememberScrollState())
        ) {
            // 大图展示
            Box(modifier = Modifier.fillMaxWidth().height(280.dp).background(Color.White)) {
                if (product.imageResName == "sleep_pillow_product") {
                    Image(
                        painter = painterResource(id = R.drawable.sleep_pillow_product_1782658859230),
                        contentDescription = product.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(Icons.Default.Spa, contentDescription = "商品图", tint = SheepPrimary, modifier = Modifier.size(120.dp).align(Alignment.Center))
                }
            }

            // 介绍文字区块 (对应截图 产品名称 睡刻x啵啵枕 产品介绍 月牙型枕身...)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("产品名称", fontSize = 15.sp, color = SheepTextSecondary, fontWeight = FontWeight.Medium)
                        Text(product.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SheepTextPrimary)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SheepCardBg)

                    Text("产品介绍", fontSize = 15.sp, color = SheepTextSecondary, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = product.description,
                        fontSize = 15.sp,
                        color = SheepTextPrimary,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text("售价：${product.price} (含税包邮)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SheepPrimary)
                }
            }

            if (showBuyToast) {
                Surface(color = SheepSecondary, shape = RoundedCornerShape(12.dp), modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("已成功加入购物车！小羊祝你今晚睡得像啵啵枕一样香软~", color = Color.White, modifier = Modifier.padding(12.dp))
                }
            }
        }

        // 底部购买按键
        Surface(color = Color.White, shadowElevation = 8.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { showBuyToast = true },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "加车")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("加入购物车")
                }
                Button(
                    onClick = { showBuyToast = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SheepPrimary),
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("立即购买", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
