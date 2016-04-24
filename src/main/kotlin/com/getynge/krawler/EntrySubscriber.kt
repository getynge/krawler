package com.getynge.krawler

import rx.Observable
import rx.Subscriber
import java.io.File
import javax.inject.Inject

/**
 * Created by getynge on 4/23/16.
 */

/**
 * An Observable that represents an entry point which runs on a separate thread from the dependency manager
 * This should only ever be Scheduled on computation threads
 * This should never perform IO directly
 */

//TODO: Write list of action codes
class EntrySubscribe @Inject constructor(val listener : Observable<File>): Observable.OnSubscribe<Long>{

    override fun call(t: Subscriber<in Long>) {
        try{

        }catch(e: Exception){
            t.onError(e)
        }
    }

    fun Entry(){


    }


}