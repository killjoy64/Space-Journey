package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.graphics.Color as GdxColor

/**
 * Utility class that defines a specific, unique color palette to be used throughout the
 * game. This color palette was generated through the following link:
 * https://lospec.com/palette-list/jmp-japanese-machine-palette
 */
class JMP {

    /**
     * Companion object that holds all possible colors in our given palette.
     */
    companion object {
        val Black = GdxColor.valueOf("#000000")
        val AlmostBlack = GdxColor.valueOf("#191028")
        val Green = GdxColor.valueOf("#46af45")
        val LightGreen = GdxColor.valueOf("#a1d685")
        val DarkPurple = GdxColor.valueOf("#453e78")
        val Purple = GdxColor.valueOf("#7664fe")
        val DarkRed = GdxColor.valueOf("#833129")
        val LightBlue = GdxColor.valueOf("#9ec2e8")
        val Red = GdxColor.valueOf("#dc534b")
        val LightRed = GdxColor.valueOf("#e18d79")
        val DarkSand = GdxColor.valueOf("#d6b97b")
        val Sand = GdxColor.valueOf("#e9d8a1")
        val DarkGreen = GdxColor.valueOf("#216c4b")
        val Pink = GdxColor.valueOf("#d365c8")
        val Grey = GdxColor.valueOf("#afaab9")
        val White = GdxColor.valueOf("#f5f4eb")
    }
}