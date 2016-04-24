package com.getynge.krawler

import dagger.Component
import dagger.Module
import dagger.Provides
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by getynge on 4/23/16.
 *
 */

/**
 * This module will provide Observables that should represent the entry point of the application
 * NOTE: Dependencies of the entry point should go in separate modules and SHOULD NOT be included in this module
 */

@Module class EntryModule(){

    /**
     * This is a function that satisfies the dependency for an OnSubscribe object
     * Currently this function is more or less useless, but it was added to make
     * refactoring in the future easier
     * @param ent The OnSubscribe that will be returned (provided by dagger)
     * @return The OnSubscribe ent provided
     * @see EntrySubscribe
     **/
    @Provides
    fun SubscriberProvider(ent : EntrySubscribe) : Observable.OnSubscribe<Long> = ent

    /**
     * This function provides a usable Observable provided an OnSubscribe object
     * The observable should provide action codes on every onNext
     * When an action code is passed, the dependency manager should perform the appropriate action then continue execution
     * @param sub The OnSubscribe to be used to create the returned observable (provided by dagger)
     * @return An observable representing the "entry point" of the program
     * @see EntrySubscribe for a list of valid action codes
     */
    @Provides
    fun EntryObservableProvider(sub : Observable.OnSubscribe<Long>) : Observable<Long> =
            Observable.create(sub)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.trampoline())

}

@Component(modules = arrayOf(EntryModule::class, WebCrawlerModule::class)) interface EntryComponent{
    fun provideEntry() : Observable<Long>
}