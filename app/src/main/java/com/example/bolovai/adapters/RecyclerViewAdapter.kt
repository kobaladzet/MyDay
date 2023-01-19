package com.example.bolovai.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bolovai.DiaryNote
import com.example.bolovai.R

class RecyclerViewAdapter(val diaryList:ArrayList<DiaryNote>):
    RecyclerView.Adapter<RecyclerViewAdapter.DiaryViewHolder>(){

        class DiaryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val diarytext = itemView.findViewById<TextView>(R.id.diaryText)
            val emotion = itemView.findViewById<TextView>(R.id.feeling)
            val timetv = itemView.findViewById<TextView>(R.id.timetv)
            val datetv = itemView.findViewById<TextView>(R.id.datetv)

            fun rame(diaryList: DiaryNote) {
                diarytext.text = diaryList.text
                emotion.text = diaryList.mood
                timetv.text = diaryList.time
                datetv.text = diaryList.date
            }



        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_item,parent,false)
        return DiaryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.rame(diaryList[position])

    }
}