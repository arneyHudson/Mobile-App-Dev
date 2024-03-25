package edu.msoe.demastri.codapizza.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val size: PizzaSize = PizzaSize.Large // Default size is large
) : Parcelable {
    val price: Double
        get() = BASE_PRICE + toppings.asSequence()
            .sumOf { (_, toppingPlacement) ->
                when (toppingPlacement) {
                    ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                    ToppingPlacement.All -> 1.0
                }
            } * size.priceMultiplier // Adjust price based on size

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    companion object {
        const val BASE_PRICE = 9.99
    }

}

enum class PizzaSize(val displayName: String, val priceMultiplier: Double) {
    Small("Small", 0.85),
    Medium("Medium", 0.90),
    Large("Large", 0.95),
    ExtraLarge("Extra Large", 1.0)
}
