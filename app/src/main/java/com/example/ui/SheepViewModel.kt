package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SheepViewModel : ViewModel() {

    // 是否在启动Splash页
    private val _isSplashShow = MutableStateFlow(true)
    val isSplashShow: StateFlow<Boolean> = _isSplashShow.asStateFlow()

    // Splash倒计时秒数
    private val _splashCountdown = MutableStateFlow(3)
    val splashCountdown: StateFlow<Int> = _splashCountdown.asStateFlow()

    // 当前底部导航 Tab (0:首页, 1:睡眠, 2:社区, 3:商城, 4:个人)
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    // 是否在沉浸式夜间睡眠监测模式
    private val _isSleepingMode = MutableStateFlow(false)
    val isSleepingMode: StateFlow<Boolean> = _isSleepingMode.asStateFlow()

    // 宠物绵羊状态
    private val _sheep = MutableStateFlow(Sheep())
    val sheep: StateFlow<Sheep> = _sheep.asStateFlow()

    // 睡眠报告
    private val _sleepReport = MutableStateFlow(SleepReport())
    val sleepReport: StateFlow<SleepReport> = _sleepReport.asStateFlow()

    // 选中的音乐分类 Tab
    private val _musicCategory = MutableStateFlow("松弛")
    val musicCategory: StateFlow<String> = _musicCategory.asStateFlow()

    // 助眠音乐列表
    private val _musicList = MutableStateFlow(
        listOf(
            MusicItem(1, "声临其境（改善睡眠）", "带你走近自然，沉浸在呼吸节拍中", "30min", "松弛", "24.5万次播放"),
            MusicItem(2, "冥想神器", "30min带给你心灵的纯净与宁静", "30min", "松弛", "18.2万次播放"),
            MusicItem(3, "发呆放空", "请问美好的时代怎么去？让脑波变慢", "45min", "松弛", "9.6万次播放"),
            MusicItem(4, "沐浴心灵", "森林深处的微风与松叶摩擦声", "30min", "松弛", "15.1万次播放"),
            MusicItem(5, "深度专注白噪音", "隔绝外界繁杂喧嚣，伴随雨点轻打窗棂", "60min", "专注", "32.0万次播放"),
            MusicItem(6, "晨间能量唤醒", "清晨鸟鸣与轻快钢琴助你活力提升", "15min", "提神", "11.3万次播放"),
            MusicItem(7, "情绪抚慰波长", "专为入睡前焦虑情绪设计的低频舒缓乐", "40min", "焦虑", "28.9万次播放")
        )
    )
    val musicList: StateFlow<List<MusicItem>> = _musicList.asStateFlow()

    // 当前播放的音乐
    private val _currentPlayingMusic = MutableStateFlow<MusicItem?>(null)
    val currentPlayingMusic: StateFlow<MusicItem?> = _currentPlayingMusic.asStateFlow()

    // 社区动态（梦话记录/树洞）
    private val _posts = MutableStateFlow(
        listOf(
            PostItem(101, "白云软糖", "昨晚 03:24", "说梦话了：‘明天一定要吃到第四颗草莓糕点...’ 小羊帮我记下来的，好羞涩哈哈", true, "00:12", 45, true, "绵羊小AI：梦里的草莓一定超级甜！今天记得奖励自己一颗哦~"),
            PostItem(102, "不熬夜的星猫", "前天 01:15", "工作压力真的好大，感觉每天脑子转不停，躺在床上闭上眼全是KPI。", false, "", 89, false, "绵羊小AI：辛苦了拍拍肩~ 试着深呼吸，把工作留给明天，今晚只属于松软的被窝。"),
            PostItem(103, "呼噜呼噜咩", "6月18日", "记录一段奇妙的声音，我家猫和小羊呼噜声同步了！", true, "00:25", 132, true)
        )
    )
    val posts: StateFlow<List<PostItem>> = _posts.asStateFlow()

    // 压力等级 (1-10)
    private val _stressLevel = MutableStateFlow(4)
    val stressLevel: StateFlow<Int> = _stressLevel.asStateFlow()

    // 商城商品
    private val _products = MutableStateFlow(
        listOf(
            StoreProduct(201, "睡刻 × 啵啵枕", "¥199", "sleep_pillow_product", "月牙型枕身设计，集柔软与支撑并存，美梦升级。仰睡肩颈保持健康曲线，舒缓释压，创造睡在云朵里的包裹感。", true),
            StoreProduct(202, "静音轻柔助眠耳塞", "¥49", "ic_earplugs", "人体工学慢回弹记忆棉，耳道零压迫感，有效隔绝呼噜声与车流声。", false),
            StoreProduct(203, "森之雾薰衣草扩香石", "¥99", "ic_aroma", "天然火山石滴入安神精油，缓缓释放南法薰衣草香气，舒缓紧绷神经。", false),
            StoreProduct(204, "褪黑素软糖（葡萄味）", "¥128", "ic_gummies", "睡前咀嚼两颗，甜甜葡萄香气，天然植物提取助你30分钟自然产生困意。", true)
        )
    )
    val products: StateFlow<List<StoreProduct>> = _products.asStateFlow()

    // 当前查看详情的商品
    private val _selectedProduct = MutableStateFlow<StoreProduct?>(null)
    val selectedProduct: StateFlow<StoreProduct?> = _selectedProduct.asStateFlow()

    init {
        // 自动倒计时关闭启动页
        viewModelScope.launch {
            for (i in 3 downTo 1) {
                _splashCountdown.value = i
                delay(1000)
            }
            _isSplashShow.value = false
        }
    }

    fun skipSplash() {
        _isSplashShow.value = false
    }

    fun selectTab(index: Int) {
        _currentTab.value = index
    }

    fun toggleSleepMode(sleep: Boolean) {
        _isSleepingMode.value = sleep
        if (sleep) {
            // 进入睡眠，奖励小羊健康与经验
            _sheep.update {
                it.copy(
                    health = (it.health + 5).coerceAtMost(100),
                    mood = "酣睡香甜",
                    exp = (it.exp + 50).coerceAtMost(it.maxExp)
                )
            }
        } else {
            _sheep.update { it.copy(mood = "活力满满") }
        }
    }

    fun selectMusicCategory(cat: String) {
        _musicCategory.value = cat
    }

    fun playMusic(item: MusicItem) {
        _musicList.update { list ->
            list.map { it.copy(isPlaying = it.id == item.id && !it.isPlaying) }
        }
        val target = _musicList.value.find { it.id == item.id }
        _currentPlayingMusic.value = if (target?.isPlaying == true) target else null
    }

    fun updateStressLevel(level: Int) {
        _stressLevel.value = level
    }

    fun getStressAdvice(level: Int): List<String> {
        return when {
            level <= 3 -> listOf(
                "1、你的心态超级棒！处于极佳的轻松状态。",
                "2、继续保持规律作息，和小羊一起享受宁静时光。",
                "3、今晚可以尝试听听轻快的自然音律，进入深度梦乡。"
            )
            level <= 6 -> listOf(
                "1、准时上床。当我们有足够的休息，处理压力的能力就会增强。",
                "2、准时起床。这样你才可以不慌不忙地开始这一天。",
                "3、学会拒绝。适度减少超出精力负荷的任务安排。",
                "4、简化你的生活。专注重要的事情，和小羊一起做个舒缓拉伸。"
            )
            else -> listOf(
                "1、当前压力值较高，身体正呼唤一段彻底的放空休息！",
                "2、强烈建议今晚提前45分钟放下手机，调暗卧室灯光。",
                "3、开启【在线问诊】或听听树洞白噪音，把焦虑写下来释放掉。",
                "4、深呼吸5次，小羊一直陪在你身边，天总会亮起来的。"
            )
        }
    }

    fun likePost(postId: Int) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val newLiked = !post.isLiked
                    post.copy(
                        isLiked = newLiked,
                        likes = if (newLiked) post.likes + 1 else post.likes - 1
                    )
                } else post
            }
        }
    }

    fun addTreeholePost(content: String) {
        if (content.isBlank()) return
        val newPost = PostItem(
            id = (_posts.value.maxOfOrNull { it.id } ?: 100) + 1,
            author = "嗯哼（我）",
            time = "刚刚",
            content = content,
            likes = 1,
            isLiked = true,
            aiReply = "绵羊小AI：听到你的倾诉啦~ 每一个认真生活的人都值得被温柔以待，今晚祝你有一个带甜味的梦。"
        )
        _posts.update { listOf(newPost) + it }
    }

    fun openProductDetail(product: StoreProduct) {
        _selectedProduct.value = product
    }

    fun closeProductDetail() {
        _selectedProduct.value = null
    }

    fun toggleProductFavorite(product: StoreProduct) {
        _products.update { list ->
            list.map { if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it }
        }
        _selectedProduct.update { if (it?.id == product.id) it.copy(isFavorite = !it.isFavorite) else it }
    }

    fun interactSheep() {
        _sheep.update {
            val newCoins = it.sheepCoins + 10
            it.copy(
                mood = listOf("开心绵软", "蹭蹭被窝", "想听催眠曲", "充满元气").random(),
                hunger = (it.hunger + 3).coerceAtMost(100),
                sheepCoins = newCoins
            )
        }
    }

    fun exchangeItem(cost: Int, name: String): Boolean {
        val current = _sheep.value
        if (current.sheepCoins >= cost) {
            _sheep.value = current.copy(
                sheepCoins = current.sheepCoins - cost,
                outfit = name
            )
            return true
        }
        return false
    }
}
