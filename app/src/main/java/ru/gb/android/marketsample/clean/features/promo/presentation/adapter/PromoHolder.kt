package ru.gb.android.marketsample.clean.features.promo.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.android.marketsample.databinding.ItemPromoBinding

class PromoHolder(
    private val binding: ItemPromoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(promo: ru.gb.android.marketsample.clean.features.promo.presentation.PromoVO) {
        binding.image.load(promo.image)
        binding.name.text = promo.name
        binding.description.text = promo.description
    }
}
