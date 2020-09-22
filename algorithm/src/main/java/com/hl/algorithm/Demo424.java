package com.hl.algorithm;

/**
 * 描述:
 *
 * 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。
 * 在执行上述操作后，找到包含重复字母的最长子串的长度。
 * 注意:
 * 字符串长度 和 k 不会超过 104。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-repeating-character-replacement
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 作者: panhongtong
 * 创建时间: 2020-09-22 21:23
 **/
public class Demo424 {

    public int characterReplacement(String s, int k) {
        // 用于记录一个窗口中字母连续出现的最大次数
        int[] map = new int[26];

        int left = 0;
        int right;
        // 出现最大次数的字母
        int historyCharMax = 0;
        char[] chars = s.toCharArray();

        for (right = 0; right < chars.length; right++) {
            int index = chars[right] - 'A';
            map[index]++;
            historyCharMax = Math.max(map[index], historyCharMax);
            // 判断是滑动还是扩张
            if (right - left + 1 > historyCharMax + k) {
                // 滑动
                map[chars[left] - 'A']--;
                left++;
            }
        }

        return chars.length - left;
    }
}
