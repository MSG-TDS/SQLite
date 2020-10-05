package com.dukim.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //SqliteHelper클래스 변수담기
    val helper = SqliteHelper(this,"memo",1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerAdapter클래스변수담기
        val adapter = RecyclerAdapter()

        //조회한 데이터담기
        adapter.helper = helper
        adapter.listData.addAll(helper.selectMemo())

        //메인 recyclerMemo컨트롤에 RecyclerAdapter담기(item_recycler)
        recyclerMemo.adapter = adapter

        recyclerMemo.layoutManager = LinearLayoutManager(this)

        //저장버튼클릭
        buttonSave.setOnClickListener{
            //Memo data클래스에 컨트롤값 셋팅
            val memo = Memo(null, editMemo.text.toString(),System.currentTimeMillis())

            //data insert
            helper.insertMemo(memo)

            //item_recycler데이터초기화 및 데이터조회
            adapter.listData.clear()
            adapter.listData.addAll(helper.selectMemo())
            adapter.notifyDataSetChanged()
            editMemo.setText("")
        }
    }
}