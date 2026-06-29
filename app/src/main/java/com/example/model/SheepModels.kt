package com.example.model

data class Sheep(
    val name: String = "咩咩小粉",
    val level: Int = 3,
    val exp: Int = 450,
    val maxExp: Int = 1000,
    val health: Int = 92, // 健康值
    val mood: String = "开心绵软", // 心情
    val hunger: Int = 85, // 饱食度
    val bedtime: String = "10:45 PM",
    val waketime: String = "07:45 AM",
    val outfit: String = "粉格小裙子",
    val sheepCoins: Int = 2450 // 羊羊币
)

data class MusicItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val duration: String,
    val category: String, // 松弛, 专注, 提升, 提神, 焦虑
    val listens: String = "12.4万次播放",
    var isPlaying: Boolean = false
)

data class PostItem(
    val id: Int,
    val author: String,
    val time: String,
    val content: String,
    val isAudio: Boolean = false,
    val audioDuration: String = "",
    var likes: Int = 12,
    var isLiked: Boolean = false,
    var aiReply: String? = null
)

data class StoreProduct(
    val id: Int,
    val name: String,
    val price: String,
    val imageResName: String, // 用于匹配占位或图
    val description: String,
    var isFavorite: Boolean = false
)

data class SleepReport(
    val score: Int = 88,
    val deepSleepPercent: Int = 42,
    val totalHours: Float = 7.8f,
    val defeatedPercent: String = "击败了全国 91.2% 的失眠者",
    val advice: String = "作息规律，小羊今天充满精神，连白云都变得更蓬松了！"
)
