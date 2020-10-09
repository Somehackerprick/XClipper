package com.kpstv.xclipper.extensions.listeners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.kpstv.xclipper.extensions.FirebaseFunction

class FChildEventListener(
    private val onDataAdded: FirebaseFunction? = null,
    private val onDataRemoved: FirebaseFunction? = null
): ChildEventListener {
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        onDataAdded?.invoke(snapshot)
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        onDataRemoved?.invoke(snapshot)
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

    }

    override fun onCancelled(error: DatabaseError) {

    }
}