import java.io.File
const val  TAVERN_NAME = "Taerny's Folly"
var playerGold = 10
var playerSilver = 10
val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()
val menuList = File("data/tavern-menu-items.txt")
    .readText()
    .split("\r\n")

//val patronGold = mapOf("Eli" to 10.5, "Mordoc" to 8.0, "Sophie" to 5.5 )
val patronGold = mutableMapOf<String,Double>()

fun main() {
    (0..9).forEach{
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        uniquePatrons += name

    }
    uniquePatrons.forEach{
        patronGold[it] = 6.0
    }
    var orderCount = 0
    while (orderCount <= 9){
        placeOrder(uniquePatrons.shuffled().first(),
            menuList.shuffled().first())
        orderCount++
    }
    println(patronGold)
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
    //  performPurchase(price.toDouble())
    //  val phrase = "Ah, dlicious $name!"
    //  println("Madrigal 驚呼道: ${toDragonSpeak(phrase)}")
    val phrase = if (name == "Dragon's Breath"){
        "$patronName 驚呼道: ${toDragonSpeak("Ah, dlicious $name!")}"
    } else {
        "$patronName 說道: 感謝 $name ."
    }
    println(phrase)
    println("")
}