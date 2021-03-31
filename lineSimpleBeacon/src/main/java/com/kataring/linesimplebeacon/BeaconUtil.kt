package com.kataring.linesimplebeacon

class BeaconUtil {
    fun asciiToHex(asciiValue: String): String {
        val chars = asciiValue.toCharArray()
        val hex = StringBuffer()
        for (i in chars.indices) {
            hex.append(Integer.toHexString(chars[i].toInt()))
        }
        return hex.toString()
    }

    fun hexToASCII(hexValue: String): String {
        val output = StringBuilder("")
        var i = 0
        while (i < hexValue.length) {
            val str = hexValue.substring(i, i + 2)
            output.append(str.toInt(16).toChar())
            i += 2
        }
        return output.toString()
    }
}
