package com.getynge.krawler

import rx.Observable

/**
 * Created by getynge on 4/23/16.
 */
interface QueueableSubscriber<T> : Observable.OnSubscribe<T>{
    fun push(item: T)
}