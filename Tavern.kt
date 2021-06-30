import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()
val menuList = File("data/tavern-menu-items.txt")
    .readText()
    .split("\r\n")
val patronGold = mutableMapOf<String, Double>()
val leftPatron = mutableSetOf<String>()
fun main() {
    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastName. shuffled().first()
        val name = "$first $last"
        uniquePatrons += name
    }
    uniquePatrons.forEach {
        patronGold[it] = 6.0
    }

    var orderCount = 0
    while (orderCount <= 9) {
        placeOrder(uniquePatrons.shuffled().first(), menuList.shuffled().first())
        println("---------------------------------------------")
        orderCount++
    }

    displayPatronBalances()
    println("---------------------------------------------")

    /*println("請問Eli在店內嗎?")
    if (patronList.contains("Eli")) {
        println("酒館老闆說: Eli在後面打牌")
    } else {
        println("酒館老闆說: Eli不再這裡")
    }

    println("---------------------------------------------")
    println("請問Sophie, Mordoc在店內嗎?")
    if (patronList.containsAll(listOf("Sophie", "Mordoc"))) {
        println("酒館老闆說: 有阿，他們坐在燉鍋旁")
    } else {
        println("酒館老闆說: 沒有，他們幾小時前就離開了")
    }

    println("---------------------------------------------")*/
    println("被請出酒館的客人有:")
    patronGold.forEach { name, balance ->
        if (balance <= 0) {
            println(name)
            leftPatron += name
        }
    }
    println("---------------------------------------------")
    println("離開客人Set:$leftPatron")
    leftPatron.forEach {
        patronGold -= it
    }
    println("剩下客人Map:$patronGold")
    println("---------------------------------------------")
    println("酒館裡剩下的客人有:")
    patronGold.forEach { name, balance ->
        println("$name，餘額為:${"%.2f".format(balance)}")
    }
}

private fun displayPatronBalances() {
    patronGold.forEach { patron, balance ->
        println("$patron" + "的餘額為: ${"%.2f".format(balance)}")
    }
}

private  fun performPurchase(price: Double, patronName: String){
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}
private fun toDragonSpeak(phrase: String) =
    phrase.replace(Regex("[aeiouAEIOU]")){
        when (it.value){
            "a" -> "4"
            "e" -> "3"
            "i" -> "1"
            "o" -> "0"
            "u" -> "|_|"
            "A" -> "4"
            "E" -> "3"
            "I" -> "1"
            "O" -> "0"
            "U" -> "|_|"
            else -> it.value
        }
    }
private  fun placeOrder(patronName: String, menuData: String){
    val indexOfAppstrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfAppstrophe)
    println("$patronName 對 $tavernMaster 說了他們訂的酒水。 ")
    val (type,name,price) = menuData.split(',')
    val message = "$patronName 買了一杯 $name ($type) 花了 $price."
    println(message)
      performPurchase(price.toDouble(),patronName)
    //  val phrase = "Ah, dlicious $name!"
    //  println("Madrigal 驚呼道: ${toDragonSpeak(phrase)}")
    val phrase = if (name == "Dragon's Breath"){
        "$patronName 驚呼道: ${toDragonSpeak("Ah, dlicious $name!")}"
    } else {
        "$patronName 說道: 感謝 $name ."
    }
    println("$phrase \n")
}
