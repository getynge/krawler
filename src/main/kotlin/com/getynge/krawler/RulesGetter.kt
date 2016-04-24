package com.getynge.krawler
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.File
import java.io.IOException

/**
 * Created by getynge on 4/23/16.
 */

/**
 * This class does all of the I/O required to fetch the rules JSON file
 * I put it here soas to avoid cluttering the dependency manager, which has
 * to get this file ready as a prerequisite.
 */
object RulesGetter{

    /**
     * This will retrieve the JSONObject provided, check for errors,
     * then extract the "rules" JSONObject and return that object once
     * the fields have been overriden
     * @param args The program arguments, should be identical to those passed to the main method
     * @return A JSONObject that contains the rules for the program to follow
     */
    fun getRules(args: Array<String>) : JSONObject {
        var (asset, site, json) = getArgs(args)

        //TODO: Check for errors in the JSON file

        val canOverride = json["overrideable"] as Boolean
        val toReturn = json["rules"] as JSONObject
        if(asset != null && canOverride){
            toReturn["assetType"] = asset
        }
        if(site != null && canOverride){
            toReturn["website"] = site
        }

        return toReturn
    }
    /**
     * A simple utility class that acts as a three element tuple
     */
    private data class Triple<X, Y, Z>(val a:X,val b:Y, val c:Z)

    /**
     * A function that will parse the arguments, and do some incomplete parsing of the provided rules file
     * where applicable.
     * @return A triple containing the asset type to read from, the url to start from, and the rules file, in order
     */
    private fun getArgs(args: Array<String>) : Triple<String?, String?, JSONObject> {
        val cmdargs = DefaultParser().parse(setupOptions(), args)
        val file:String? = cmdargs.getOptionValue('r')
        val asset:String? = cmdargs.getOptionValue('a')
        val website:String? = cmdargs.getOptionValue('s')
        val jsonFile = if(file != null){
            getRules(getFileReader(File(file)))
        }else{
            println("Using default rules file...")
            getRules(RulesGetter::class.java.getResourceAsStream("/configs/defaultConfig.json").bufferedReader())
        }
        if(asset == null && file == null){
            System.err.println("ERROR: either provide an asset type to be downloaded, or provide a rules file to be parsed")
            System.err.println("Please use either -a to provide an asset type, or -r to provide a rules file")
            System.exit(-1)
        }
        if(website == null && file == null){
            System.err.println("ERROR: either provide a website to start from, or provide a rules file to be parsed")
            System.err.println("Please use either -s to provide a website, or ")
        }
        return Triple(asset, website, jsonFile)
    }

    /**
     * This function sets up the Apache Commons CLI options
     * @return Apache Commons CLI options
     */

    private fun setupOptions() : Options {
        val toReturn = Options()
        toReturn.addOption("r", "rules", true, "The directory of the rules file to be parsed")
        toReturn.addOption("s", "site", true, "The website to start from")
        toReturn.addOption("a", "asset-type", true, "The type of asset to parse")
        return toReturn
    }

    /**
     * Produces a buffered reader from a file
     * use this when reading from a file in a directory
     * DO NOT USE THIS if you need to read a file stored in the jar
     * @param file the file to read from
     * @return a BufferedReader that will read from the provided file
     */

    private fun getFileReader(file: File)
        = try {
            if(!file.exists()) throw IOException()
            file.bufferedReader()
        }catch(e: IOException){
            println("Error reading file, potentially incorrect file name")
            throw e
        }catch(e: Exception){
            println("Unknown error occurred")
            throw e
        }

    //TODO: Check to se what happens when we hand it a malformed JSON
    /**
     * This produces a JSONObject for the supplied rules file
     * To be honest I'm not too sure what happens if the JSON file is malformed
     * @param rulesFileReader The bufferedReader that we are reading from
     * @return a JSONObject containing all of the rules for the crawler to follow
     */
    private fun getRules(rulesFileReader: BufferedReader) : JSONObject {
        var curline:String? = rulesFileReader.readLine()
        var json = ""
        while(curline != null){
            json += curline
            curline = rulesFileReader.readLine()
        }
        json = json.filter { !it.isWhitespace() } //remove any whitespace from the parsed string
        return JSONParser().parse(json) as JSONObject
    }
}