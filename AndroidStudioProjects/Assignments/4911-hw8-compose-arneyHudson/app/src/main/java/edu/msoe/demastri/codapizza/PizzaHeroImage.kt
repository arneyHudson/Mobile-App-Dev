package edu.msoe.demastri.codapizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.msoe.demastri.codapizza.model.Pizza
import edu.msoe.demastri.codapizza.model.PizzaSize
import edu.msoe.demastri.codapizza.model.Topping
import edu.msoe.demastri.codapizza.model.ToppingPlacement

@Preview
@Composable
private fun PizzaHeroImagePreview() {
    PizzaHeroImage(
        pizza = Pizza(
            toppings = mapOf(
                Topping.Pineapple to ToppingPlacement.All,
                Topping.Pepperoni to ToppingPlacement.Left,
                Topping.Basil to ToppingPlacement.Right
            )
        )
    )
}

@Composable
fun PizzaHeroImage(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    val scalingFactor = when (pizza.size) {
        PizzaSize.ExtraLarge -> 1.0f
        PizzaSize.Large -> 0.95f
        PizzaSize.Medium -> 0.90f
        PizzaSize.Small -> 0.85f
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .scale(scalingFactor)
    ) {
        Image(
            painter = painterResource(R.drawable.pizza_crust),
            contentDescription = stringResource(R.string.pizza_preview),
            modifier = Modifier.fillMaxSize()
        )

        pizza.toppings.forEach { (topping, placement) ->
            Image(
                painter = painterResource(topping.pizzaOverlayImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = when (placement) {
                    ToppingPlacement.Left -> Alignment.TopStart
                    ToppingPlacement.Right -> Alignment.TopEnd
                    ToppingPlacement.All -> Alignment.Center
                },
                modifier = Modifier.focusable(false)
                    .aspectRatio(when (placement) {
                        ToppingPlacement.Left, ToppingPlacement.Right -> 0.5f
                        ToppingPlacement.All -> 1.0f
                    })
                    .align(when (placement) {
                        ToppingPlacement.Left -> Alignment.CenterStart
                        ToppingPlacement.Right -> Alignment.CenterEnd
                        ToppingPlacement.All -> Alignment.Center
                    })
            )
        }
    }
}

