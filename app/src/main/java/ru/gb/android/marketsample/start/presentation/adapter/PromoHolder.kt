package ru.gb.android.marketsample.start.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.android.marketsample.databinding.ItemPromoBinding
import ru.gb.android.marketsample.start.presentation.PromoEntity

class PromoHolder(
    private val binding: ItemPromoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(promo: PromoEntity) {
        binding.image.load(promo.image)
        binding.name.text = promo.name
        binding.description.text = promo.description
    }
}
