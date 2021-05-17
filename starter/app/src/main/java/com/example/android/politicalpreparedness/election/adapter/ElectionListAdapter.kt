package com.example.android.politicalpreparedness.election.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionItemBinding
//import com.example.android.politicalpreparedness.databinding.ViewholderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback()) {
    private var _items: MutableList<Election> = mutableListOf()

    private val items: List<Election>?
        get() = this._items

    override fun getItemCount() = _items.size

    override fun getItem(position: Int) = _items[position]

    fun addData(items: List<Election>) {
        _items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        _items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener,item)
    }

    class ElectionViewHolder private constructor(val binding: ElectionItemBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: ElectionListener, item: Election){
            Log.d("FLUX",item.name)
            binding.election = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ElectionViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ElectionItemBinding.inflate(layoutInflater, parent, false)
                return ElectionViewHolder(binding)
            }
        }
    }
}

class ElectionListener(val clickListener: (electionId: Int) -> Unit){
    fun onClick(election: Election) = clickListener(election.id)
}

class ElectionDiffCallback: DiffUtil.ItemCallback<Election>(){
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

}