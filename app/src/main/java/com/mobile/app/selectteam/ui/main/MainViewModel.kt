package com.mobile.app.selectteam.ui.main

import android.R
import android.content.res.Resources
import android.graphics.*
import android.graphics.Color.parseColor
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mobile.app.selectteam.DisposableViewModel
import com.mobile.app.selectteam.data.UserRespository
import com.mobile.app.selectteam.data.local.UserData
import com.mobile.app.selectteam.util.SingleLiveEvent


class MainViewModel internal constructor(private val userRespository: UserRespository): DisposableViewModel() {

    private val _allDatas = MediatorLiveData<List<UserData>>()
    val allDatas get() = _allDatas

    val listEvent = SingleLiveEvent<List<UserData>>()
    val message = SingleLiveEvent<String>()
    val selectEvent = SingleLiveEvent<Int>()
    val finishEvent = SingleLiveEvent<Any>()

    fun onFinishEvent(){
        finishEvent.call()
    }
    fun addUser(userData : UserData){
        userRespository.addUser(userData)
            .subscribe({
                message.value = "success"
            },{
                message.value = "error"
            }).let { addDisposable(it) }
    }
    fun suffleData() : List<UserData>{
        val data = allDatas.value?.filter { it.select }?: emptyList()
        return data.shuffled()
    }
    fun onSelectEvent(userData: UserData,position : Int){
        userData.select = !userData.select
        selectEvent.value = position
    }
    fun removeUser(userData : UserData){
        userRespository.removeUser(userData)
            .subscribe({
                message.value = "success"
            },{
                message.value = "error"
            }).let { addDisposable(it) }
    }
    fun load(){
        _allDatas.addSource(userRespository.getUsers()){users->
            _allDatas.value = users
        }
    }

    fun getSwipeCallback(resource : Resources) : ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if(direction == ItemTouchHelper.LEFT){
                    removeUser(allDatas.value?.get(position)?:return)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon : Bitmap
                val paint = Paint()
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = itemView.bottom - itemView.top
                    val width = height / 3
                    val background = RectF(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                    icon = BitmapFactory.decodeResource(resource, R.drawable.ic_menu_delete)
                    if (dX <= 0) {
                        paint.color = parseColor("#D32F2F")
                        c.drawRect(background, paint)
                        val icon_dest = RectF(
                            itemView.right.toFloat() - 2 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, icon_dest, paint)
                    }
                }
                clearView(recyclerView,viewHolder)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
    }

}
