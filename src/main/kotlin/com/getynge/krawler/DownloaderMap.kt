package com.getynge.krawler

import rx.Observable
import rx.functions.Func1
import java.io.File
import java.net.URL

/**
 * Created by getynge on 4/23/16.
 */
class DownloaderMap : Func1<URL, Observable<File>> {
    override fun call(t1: URL?): Observable<File>? {
        return null
    }
}