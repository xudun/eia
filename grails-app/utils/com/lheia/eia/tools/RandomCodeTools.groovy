package com.lheia.eia.tools

class RandomCodeTools {
    private static Random strGen = new Random();
    private
    static char[] numbersAndLetters = ("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    /** * 产生随机字符串 * */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[strGen.nextInt(61)];
        }
        return new String(randBuffer);
    }
}
