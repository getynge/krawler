package com.getynge.krawler

import org.jsoup.nodes.Document
import rx.Observable
import rx.Subscriber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

/**
 * Created by getynge on 4/23/16.
 */

class CrawlerSubscriber @Inject constructor(val url:URL) : Observable.OnSubscribe<Document>{
    private fun URL.openUnsecureConnection() : BufferedReader{
        val conn = openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        return BufferedReader(InputStreamReader(conn.inputStream))
    }
    private fun URL.openSecureConnection() : BufferedReader{
        val conn = openConnection() as HttpsURLConnection
        conn.requestMethod = "GET"
        return BufferedReader(InputStreamReader(conn.inputStream))
    }
    private fun URL.bufferedReader() : BufferedReader{
        if(this.protocol.toLowerCase() == "http")
            return openUnsecureConnection()
        else if(this.protocol.toLowerCase() == "https")
            return openSecureConnection()
        else
            throw IOException("The provided protocol is currently unsupported")
    }
    override fun call(t1: Subscriber<in Document>?) {

    }
}