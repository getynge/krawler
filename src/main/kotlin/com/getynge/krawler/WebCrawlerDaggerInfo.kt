package com.getynge.krawler

import dagger.Module
import dagger.Provides
import org.jsoup.nodes.Document
import rx.Observable
import rx.functions.Func1
import rx.schedulers.Schedulers
import java.io.File
import java.net.URL

/**
 * Created by getynge on 4/23/16.
 */

@Module class WebCrawlerModule(private val url:String){

    @Provides fun URLPRovider() : URL = URL(url)

    @Provides fun CrawlerSubscriberProvider(url:URL) : Observable.OnSubscribe<Document> = CrawlerSubscriber(url)

    @Provides fun CrawlerProvider(sub:Observable.OnSubscribe<Document>) : Observable<Document> =
        Observable
                .create(sub)
                .subscribeOn(Schedulers.io())

    @Provides fun ProcessorMapProvider() : Func1<Document, Observable<URL>> = ProcessorMap()

    @Provides fun ProcessorObservableProvider(f: Func1<Document, Observable<URL>>, obs : Observable<Document>) : Observable<URL> =
            obs.flatMap(f)
                .subscribeOn(Schedulers.computation())

    @Provides fun DownloaderMapProvider() : Func1<URL, Observable<File>> = DownloaderMap()

    @Provides fun DownloaderObservableProvider(f: Func1<URL, Observable<File>>, obs:Observable<URL>) : Observable<File> =
            obs.flatMap(f)
                .subscribeOn(Schedulers.io())
}