package ru.gb.android.marketsample.clean.features.products.presentation

import ru.gb.android.marketsample.clean.features.products.domain.Product
import ru.gb.android.marketsample.clean.common.promo.domain.Promo

class ProductVOFactory {
    fun create(product: Product, promos: List<ru.gb.android.marketsample.clean.common.promo.domain.Promo>): ProductVO {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductVO(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price,
            hasDiscount = promoForProduct != null,
            discount = (promoForProduct as? Promo.PromoForProducts)?.discount?.toInt() ?: 0
        )
    }
}