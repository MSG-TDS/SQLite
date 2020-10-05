package com.dukim.sqlite

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import kotlinx.android.synthetic.main.item_recycler.view.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var listData = mutableListOf<Memo>()
    var helper:SqliteHelper?=null

    //RecyclerView.Adapter에서 재공하는 매서드정의 Ctrl+i
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    //
    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var mMemo:Memo?=null

        init{
            itemView.buttonDelete.setOnClickListener{
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        fun setMemo(memo: Memo)
        {
            itemView.textNo.text = "${memo.no}"
            //itemView.textNo.text = memo.no.toString()
            itemView.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")

//            itemView.textDatetime.text = "${memo.datetime}"
            itemView.textDatetime.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo
        }
    }
}

/*
class  holder1(itemView: View):RecyclerView.ViewHolder(itemView)
{
    fun setMemo(memo: Memo)
    {
        itemView.textNo.text = "${memo.no}"
        itemView.textContent.text = memo.content
        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")

        itemView.textDatetime.text = "${sdf.format(memo.datetime)}"
    }
}
*/

