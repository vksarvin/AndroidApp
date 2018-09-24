package com.voltuswave.roomtempo.utils

import android.os.Bundle
import android.support.v7.util.DiffUtil
import com.voltuswave.roomtempo.models.ReservationList
import java.util.*


class MyDiffUtilsCallBack(
        private val oldList: List<ReservationList>,
        private val newList: List<ReservationList>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int {
        // Simulate a really long running diff calculation.
        try {
            Thread.sleep(Random().nextInt(3000).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].statusName == newList[newItemPosition].statusName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        val newModel = newList[newItemPosition]
        val oldModel = oldList[oldItemPosition]

        val diff = Bundle()

        if (newModel.statusName != oldModel.statusName) {
            diff.putString("statusName", newModel.statusName)
        }
        return if (diff.size() == 0) {
            null
        } else diff
       // return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}