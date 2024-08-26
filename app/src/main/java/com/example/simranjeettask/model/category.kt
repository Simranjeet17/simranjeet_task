package com.example.simranjeettask.model

import android.os.Parcel
import android.os.Parcelable

class category : ArrayList<categoryItem>()

data class categoryItem(
    val created_at: String?=null,
    val id: Int?=null,
    val name: String?=null,
    val restaurant: List<Restaurant>?=null,
    val status: Int?=null,
    val updated_at: String?=null
)

data class Restaurant(
    val address: String,
    val category_id: Int,
    val created_at: String,
    val filename: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val path: String,
    val phone_number: String,
    val status: Int,
    val updated_at: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeInt(category_id)
        parcel.writeString(created_at)
        parcel.writeString(filename)
        parcel.writeInt(id)
        parcel.writeString(image_url)
        parcel.writeString(name)
        parcel.writeString(path)
        parcel.writeString(phone_number)
        parcel.writeInt(status)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}