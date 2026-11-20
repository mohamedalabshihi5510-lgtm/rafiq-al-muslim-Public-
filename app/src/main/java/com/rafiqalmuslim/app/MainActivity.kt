package com.rafiqalmuslim.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Sky = Color(0xFF168BC4)
private val Pale = Color(0xFFF1F9FD)
private val Ink = Color(0xFF173849)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RafiqApp() }
    }
}

@Composable
fun RafiqApp() {
    var selected by remember { mutableStateOf("الرئيسية") }
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Sky,
            secondary = Color(0xFF61B7DF),
            background = Pale,
            surface = Color.White
        )
    ) {
        Scaffold(
            containerColor = Pale,
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val tabs = listOf(
                        "الرئيسية" to Icons.Default.Home,
                        "القرآن" to Icons.Default.MenuBook,
                        "الأذكار" to Icons.Default.Spa,
                        "السبحة" to Icons.Default.Favorite
                    )
                    tabs.forEach { (label, icon) ->
                        NavigationBarItem(
                            selected = selected == label,
                            onClick = { selected = label },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { padding ->
            when (selected) {
                "السبحة" -> TasbeehScreen(Modifier.padding(padding))
                "الأذكار" -> AdhkarScreen(Modifier.padding(padding))
                "القرآن" -> QuranScreen(Modifier.padding(padding))
                else -> HomeScreen(Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun Page(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        content = content
    )
}

@Composable
fun Header(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Ink)
        Text(subtitle, fontSize = 14.sp, color = Color(0xFF64808E))
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Page(modifier) {
        Header("رفيق المسلم", "رفيقك اليومي للعبادة والذكر")
        Card(colors = CardDefaults.cardColors(containerColor = Sky), shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("مواقيت الصلاة", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("سيتم ربط المواقيت بموقعك وطريقة الحساب في مرحلة إعداد الصلاة.", color = Color.White)
                Text("تنبيه: هذه شاشة تأسيسية وليست مواقيت فعلية بعد.", color = Color.White.copy(alpha = .9f), fontSize = 12.sp)
            }
        }
        Text("أقسام التطبيق", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink)
        FeatureCard("القرآن الكريم", "المصحف والتلاوات الصوتية", Icons.Default.MenuBook)
        FeatureCard("الأذكار", "الصباح والمساء وسائر الأذكار", Icons.Default.Spa)
        FeatureCard("السبحة الإلكترونية", "عداد تسبيح يحفظ العدد على الشاشة", Icons.Default.Favorite)
        FeatureCard("إذاعة القرآن", "سيتم ربط بث إذاعي موثوق في مرحلة الصوت", Icons.Default.Radio)
    }
}

@Composable
fun FeatureCard(title: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Sky, modifier = Modifier.size(30.dp))
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, color = Ink, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(description, color = Color(0xFF64808E), fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun TasbeehScreen(modifier: Modifier = Modifier) {
    var count by remember { mutableIntStateOf(0) }
    var total by remember { mutableIntStateOf(0) }
    var phraseIndex by remember { mutableIntStateOf(0) }
    val phrases = listOf("سبحان الله", "الحمد لله", "الله أكبر")
    Page(modifier) {
        Header("السبحة الإلكترونية", "عداد تفاعلي للتسبيح")
        Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(phrases[phraseIndex], color = Sky, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text(count.toString(), color = Ink, fontSize = 64.sp, fontWeight = FontWeight.Bold)
                Text("الإجمالي: $total", color = Color(0xFF64808E))
                Button(
                    onClick = {
                        total++
                        count++
                        if (count == 33) {
                            count = 0
                            phraseIndex = (phraseIndex + 1) % phrases.size
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(62.dp),
                    shape = RoundedCornerShape(18.dp)
                ) { Text("سبّح", fontSize = 21.sp) }
                OutlinedButton(onClick = { count = 0; total = 0; phraseIndex = 0 }) { Text("تصفير العداد") }
                Text("يتغير الذكر تلقائيًا بعد كل 33 تسبيحة.", color = Color(0xFF64808E), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun AdhkarScreen(modifier: Modifier = Modifier) {
    Page(modifier) {
        Header("الأذكار", "الأقسام الأساسية — المحتوى الكامل والصوت في مراحل الربط")
        listOf(
            "أذكار الصباح" to "07:00 صباحًا",
            "أذكار المساء" to "05:00 مساءً",
            "أذكار النوم والاستيقاظ" to "حسب اختيارك",
            "أذكار بعد الصلاة" to "بعد كل صلاة",
            "الاستغفار والصلاة على النبي ﷺ" to "تذكير اختياري"
        ).forEach { (name, time) ->
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(name, fontWeight = FontWeight.Bold, color = Ink)
                    Text(time, color = Sky)
                }
            }
        }
        Text("إعدادك المعتمد: تذكير صوتي كل 10 دقائق. لم يتم تفعيل الجدولة أو الصوت بعد.", color = Color(0xFF9A5A12))
    }
}

@Composable
fun QuranScreen(modifier: Modifier = Modifier) {
    Page(modifier) {
        Header("القرآن الكريم", "المصحف الشريف")
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("القرآن الكريم", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Ink)
                Text("سيتم إدراج نص المصحف المعتمد وربط تلاوات الشيخ مشاري العفاسي وخيارات التحميل دون إنترنت.")
                Text("هذه النسخة التأسيسية لا تحتوي بعد على نص المصحف أو ملفات التلاوة.", color = Color(0xFF9A5A12), fontSize = 13.sp)
            }
        }
    }
}
