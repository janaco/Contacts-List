package com.nandy.contacts

/**
 * Created by yana on 03.12.17.
 */
interface OnListItemClickListener<T> {

    fun onListItemClick(item: T, position: Int)
}