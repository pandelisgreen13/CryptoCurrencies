package com.pchasapis.cryptocurrency.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.common.extensions.getAmountWithDecimalAndCurrencySymbolEnd
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_home.*

class HomeRecyclerViewAdapter(private val liveDataList: List<RateDataModel>, private val onItemClicked: (RateDataModel) -> Unit) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_home, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cryptoRate = liveDataList[position]

        holder.cryptoNameTextView.text = "${cryptoRate.title}/${cryptoRate.target}"
        holder.cryptoRateTextView.text = cryptoRate.rate.rate?.getAmountWithDecimalAndCurrencySymbolEnd(holder.itemView.context, cryptoRate.target)
                ?: "-"
        holder.cryptoHighTextView.text = cryptoRate.rate.high?.getAmountWithDecimalAndCurrencySymbolEnd(holder.itemView.context, cryptoRate.target)
                ?: "-"
        holder.cryptoLowTextView.text = cryptoRate.rate.low?.getAmountWithDecimalAndCurrencySymbolEnd(holder.itemView.context, cryptoRate.target)
                ?: "-"

        Picasso.get().load(cryptoRate.crypto?.iconUrl).placeholder(R.mipmap.ic_splash).into(holder.cryptoImageView)

        holder.itemView.setOnClickListener { onItemClicked(cryptoRate) }
    }

    override fun getItemCount(): Int {
        return liveDataList.size
    }

    class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}