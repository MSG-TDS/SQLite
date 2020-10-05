package com.dukim.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//SQLiteOpenHelper상속받아서 class생성
class SqliteHelper(context:Context, name:String, version:Int):SQLiteOpenHelper(context,name,null,version) {
    //SQLiteOpenHelper클래스에서 제공하는 매서드 onCreate/onUpgrade Ctrl+I자동생성

    override fun onCreate(db: SQLiteDatabase?) {
        val create="create table memo ("+
                "no integer primary key, "+
                "content text, "+
                "datetime integer"+
                ")"

        db?.execSQL(create)
        //db.execSQL(create)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    //insert 입력 매서드생성
    fun insertMemo(memo:Memo)
    {
        val values = ContentValues()
        //no는 자동증가값으로 데이터를 넣지않음.
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)

        //테이블의 데이터를 입력/수정/삭제시 writableDatabase(쓰기)
        val wd = writableDatabase
        wd.insert("memo",null,values)

        //쿼리문을 직접작성해서 수행할수 있음.
        //var sqlstr = "insert into memo (content,datetime) values('${memo.content}','${memo.datetime}')"
        //wd.execSQL(sqlstr)
        wd.close()
    }

    //select 조회 매서드생성
    //조회한 데이터를 반환하기 위해서 MutableList형태로 반환함.
    fun selectMemo():MutableList<Memo>{
        val list = mutableListOf<Memo>()

        val select = "select * from memo"

        //데이블의 데이트를 조회시 readableDatabase(읽기)
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)

        while(cursor.moveToNext()) {
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            list.add(Memo(no,content,datetime))
        }

        cursor.close()
        rd.close()

        return list
    }

    //update 매서드생성
    fun updateMemo(memo: Memo){
        val values = ContentValues()
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)

        val wd=writableDatabase
        wd.update("memo",values,"no=${memo.no}",null)
        wd.close()
    }

    //delete 매서드생성
    fun deleteMemo(memo: Memo)
    {
        val delete = "delete from memo where no = ${memo.no}"
        val db = writableDatabase

        db.execSQL(delete)
        db.close()
    }

}

//no컬럼은 자동증가 값으로 null값사용 ?사용함.
data class Memo(val no : Long?, var content: String, var datetime : Long)