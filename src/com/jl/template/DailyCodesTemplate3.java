package com.jl.template;

import java.util.*;

import com.jl.common.ListNode;

/**
 * xh
 */
public class DailyCodesTemplate3 {

    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            Integer val = map.get(nums1[i]);
            if (val == null) {
                map.put(nums1[i], 1);
            } else {
                val += 1;
                map.put(nums1[i], val);
            }
        }

        int[] res = new int[Math.max(nums1.length, nums2.length)];
        int index = 0;
        for (int i = 0; i < nums2.length; i++) {
            Integer val = map.get(nums2[i]);
            if (val != null && val > 0) {
                res[index++] = nums2[i];
                val -= 1;
                map.put(nums2[i], val);
            }
        }
        return Arrays.copyOfRange(res, 0, index);
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String benchmark = strs[0];
        for (int i = 1; i < strs.length; i++) {
            benchmark = common(benchmark, strs[i]);
            if (benchmark.length() == 0) {
                return "";
            }
        }
        return benchmark;
    }

    public String common(String s1, String s2) {
        int len = Math.min(s1.length(), s2.length());
        int index = 0;
        while (index < len && s1.charAt(index) == s2.charAt(index)) {
            index++;
        }
        return s1.substring(0, index);
    }

    public void rotate(int[] nums, int k) {
        int len = nums.length;
        int l = k % len;
        rotate(nums, 0, len - 1);
        rotate(nums, 0, l - 1);
        rotate(nums, l, len - 1);
    }

    public void rotate(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[end];
            nums[end] = nums[start];
            nums[start] = temp;
            start++;
            end--;
        }
    }

    public int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[index++] = nums[i];
            }
        }
        return index;
    }

    int cap = 0;

    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = i == digits.length - 1 ? digits[i] + 1 : cap + digits[i];
            int d = sum % 10;
            cap = sum / 10;
            if (cap != 0) {
                digits[i] = d;
            } else {
                digits[i] = d;
                break;
            }
        }

        if (cap != 0) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            res[1] = 0;
            int index = 2;
            for (int i = 1; i < digits.length; i++) {
                res[index++] = digits[i];
            }
            return res;
        }
        return digits;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                if (nums[i] + nums[l] + nums[r] == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    while (l < r && nums[l] == nums[l + 1]) l++;
                    while (l < r && nums[r] == nums[r - 1]) r--;
                    l++;
                    r--;
                } else if (nums[i] + nums[l] + nums[r] < 0) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return res;
    }

    // todo
    public String convert(String s, int numRows) {
        if (s.length() == 0) return "";

        if (numRows < 2) return s;

        List<StringBuilder> list = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }

        int index = 0, flag = -1;
        for (int i = 0; i < s.length(); i++) {
            list.get(index).append(s.charAt(i));
            if (index == 0 || numRows - 1 == index) flag = -flag;
            index += flag;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return String.valueOf(sb);
    }

    // todo
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        ListNode node = slow.next;
        slow.next = node.next;
        return dummy.next;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode res = dummy;
        while (l1 != null && l2 != null) {
            int val1 = l1.val;
            int val2 = l2.val;
            if (val1 < val2) {
                dummy.next = l1;
                l1 = l1.next;
                dummy = dummy.next;
            } else {
                dummy.next = l2;
                l2 = l2.next;
                dummy = dummy.next;
            }
        }

        if (l1 != null) {
            dummy.next = l1;
        }

        if (l2 != null) {
            dummy.next = l2;
        }
        return res.next;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            if (slow == fast) return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode res = dummy;
        while (l1 != null || l2 != null) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            int sum = cap + val1 + val2;
            int d = sum % 10;
            cap = sum / 10;
            dummy.next = new ListNode(d);
            dummy = dummy.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        if (cap != 0) {
            ListNode tail = new ListNode(1);
            dummy.next = tail;
        }
        return res.next;
    }

    public static void main(String[] args) {
        DailyCodesTemplate3 d = new DailyCodesTemplate3();
        d.convert("AB", 1);
    }

}
