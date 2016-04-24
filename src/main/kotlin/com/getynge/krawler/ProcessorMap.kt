package com.getynge.krawler

import org.jsoup.nodes.Document
import rx.Observable
import rx.functions.Func1
import java.net.URL

/**
 * Created by getynge on 4/23/16.
 */

class ProcessorMap : Func1<Document, Observable<URL>>{

    override fun call(t1: Document?): Observable<URL>? {
        return null
    }
}