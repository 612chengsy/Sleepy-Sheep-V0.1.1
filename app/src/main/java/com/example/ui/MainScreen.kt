package com.example.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.screens.*
import com.example.ui.theme.*

@Composable
fun MainScreen(viewModel: SheepViewModel) {
    val isSplashShow by viewModel.splashCountdown.collectAsState()
    val isSplash by viewModel.isSplashShow.collectAsState()
    val isSleepingMode by viewModel.isSleepingMode.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()

    val sheep by viewModel.sheep.collectAsState()
    val report by viewModel.sleepReport.collectAsState()
    val musicList by viewModel.musicList.collectAsState()
    val musicCategory by viewModel.musicCategory.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val stressLevel by viewModel.stressLevel.collectAsState()
    val products by viewModel.products.collectAsState()
    val selectedProduct by viewModel.selectedProduct.collectAsState()

    if (isSplash) {
        SplashScreen(
            countdown = isSplashShow,
            onSkip = { viewModel.skipSplash() }
        )
        return
    }

    if (isSleepingMode) {
        SleepMonitorScreen(
            onWakeUp = { viewModel.toggleSleepMode(false) }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = SheepPrimary,
                tonalElevation = 8.dp
            ) {
                val tabs = listOf(
                    Triple("首页", Icons.Default.Home, 0),
                    Triple("睡眠", Icons.Default.Star, 1),
                    Triple("社区", Icons.Default.Share, 2),
                    Triple("商城", Icons.Default.ShoppingCart, 3),
                    Triple("个人", Icons.Default.Person, 4)
                )

                tabs.forEach { (title, icon, index) ->
                    val selected = currentTab == index
                    NavigationBarItem(
                        selected = selected,
                        onClick = { viewModel.selectTab(index) },
                        icon = { Icon(icon, contentDescription = title) },
                        label = { Text(title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SheepPrimary,
                            selectedTextColor = SheepPrimary,
                            indicatorColor = SheepCardBg,
                            unselectedIconColor = SheepTextSecondary,
                            unselectedTextColor = SheepTextSecondary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = SheepBackground
        ) {
            when (currentTab) {
                0 -> HomeScreen(
                    sheep = sheep,
                    musicList = musicList,
                    selectedCategory = musicCategory,
                    onSelectCategory = { viewModel.selectMusicCategory(it) },
                    onPlayMusic = { viewModel.playMusic(it) },
                    onInteractSheep = { viewModel.interactSheep() }
                )
                1 -> SleepScreen(
                    sheep = sheep,
                    report = report,
                    onStartSleep = { viewModel.toggleSleepMode(true) },
                    onInteractSheep = { viewModel.interactSheep() }
                )
                2 -> CommunityScreen(
                    stressLevel = stressLevel,
                    adviceList = viewModel.getStressAdvice(stressLevel),
                    posts = posts,
                    onUpdateStress = { viewModel.updateStressLevel(it) },
                    onLikePost = { viewModel.likePost(it) },
                    onAddPost = { viewModel.addTreeholePost(it) }
                )
                3 -> StoreScreen(
                    products = products,
                    selectedProduct = selectedProduct,
                    onSelectProduct = { viewModel.openProductDetail(it) },
                    onCloseDetail = { viewModel.closeProductDetail() },
                    onToggleFavorite = { viewModel.toggleProductFavorite(it) }
                )
                4 -> ProfileScreen(
                    sheep = sheep,
                    onExchange = { cost, name -> viewModel.exchangeItem(cost, name) }
                )
            }
        }
    }
}
