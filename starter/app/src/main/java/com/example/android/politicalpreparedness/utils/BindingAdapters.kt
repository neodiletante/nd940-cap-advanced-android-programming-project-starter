package com.example.android.politicalpreparedness.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.representative.model.Representative

object BindingAdapters {

    /**
     * Use binding adapter to set the recycler view data using livedata object
     */
    @BindingAdapter("android:liveData")
    @JvmStatic
    fun setRecyclerViewData(recyclerView: RecyclerView, items: LiveData<List<Election>>?) {
        items?.value?.let { itemList ->
            (recyclerView.adapter as? ElectionListAdapter)?.apply {
                clear()
                addData(itemList)
            }
        }
    }

    @BindingAdapter("infoBtnText")
    @JvmStatic
    fun bindInfoBtnText(textView: TextView, isSaved: Boolean) {
        if (isSaved) {
            textView.setText("REMOVE ELECTION")
        } else {
            textView.setText("SAVE ELECTION")
        }
    }

    @BindingAdapter("app:liveData")
    @JvmStatic
    fun setRepresentativesData(recyclerView: RecyclerView, items: LiveData<List<Representative>>?) {
        items?.value?.let { itemList ->
            (recyclerView.adapter as? RepresentativeListAdapter)?.apply {
                clear()
                addData(itemList)
            }
        }
    }

    @BindingAdapter("android:fadeVisible")
    @JvmStatic
    fun setFadeVisible(view: View, visible: Boolean? = true) {
        if (view.tag == null) {
            view.tag = true
            view.visibility = if (visible == true) View.VISIBLE else View.GONE
        } else {
            view.animate().cancel()
            if (visible == true) {
                if (view.visibility == View.GONE)
                    view.fadeIn()
            } else {
                if (view.visibility == View.VISIBLE)
                    view.fadeOut()
            }
        }
    }

}