package com.getynge.krawler


/**
 * Entry point, the main function acts as the "dependency manager"
 * Everything is wired from the top level down,
 * In this file we also perform init operations that should not be performed after spawning threads,
 * such as a rules file that could potentially be read by multiple threads at once
 */

fun main(args:Array<String>){
    var topass = args;
    topass += arrayOf("-a","image","-s","www.example.com")
    val json = RulesGetter.getRules(topass)
    val provider = DaggerEntryComponent.builder().entryModule(EntryModule()).webCrawlerModule(WebCrawlerModule("http://www.google.com")).build()


}

