package com.jl.template;

import java.util.*;

import com.jl.common.CommonClass;
import com.jl.common.ListNode;

/**
 * xiaohao 数组、链表
 */
public class DailyCodesTemplate1 {

    /*

    */

    public void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public void heapSort(int[] arr) {
        buildMaxHeap(arr);

        for (int i = arr.length - 1; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, i, 0);
        }
    }

    public void buildMaxHeap(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            adjustHeap(arr, arr.length, i);
        }
    }

    public void adjustHeap(int[] arr, int size, int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int largest = parent;

        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != parent) {
            int temp = arr[largest];
            arr[largest] = arr[parent];
            arr[parent] = temp;
            adjustHeap(arr, size, largest);
        }
    }

    public void mergeSort(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        if (low < high) {
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);
            merge(arr, low, mid, high);
        }
    }

    public void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= high) {
            temp[k++] = arr[j++];
        }

        for (int n = 0; n < temp.length; n++) {
            arr[n + low] = temp[n];
        }
    }

    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = partition(arr, low, high);
            quickSort(arr, low, mid - 1);
            quickSort(arr, mid + 1, high);
        }
    }

    public int partition(int[] arr, int low, int high) {
        int pivot = arr[low];

        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high];

            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

    public static void main(String[] args) {
        int[] a = new int[]{3, 6, 8, 4, 1, 9, 2, 1, 6};
        DailyCodesTemplate1 d = new DailyCodesTemplate1();
        d.heapSort(a);
//        d.mergeSort(a, 0, a.length - 1);
//        d.quickSort(a, 0, a.length - 1);
//        d.binarySort(a);
        CommonClass.print(a);
    }

    public int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] < target) {
                low = mid + 1;
            } else if (arr[mid] > target) {
                high = mid;
            } else if (arr[mid] == target) {
                return mid;
            }
        }
        return -1;
    }

    public void binarySort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                continue;
            }

            int low = 0;
            int high = i - 1;
            int mid;
            int temp = arr[i];

            while (low <= high) {
                mid = low + (high - low) / 2;
                if (temp < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            for (int j = i - 1; j >= low; j--) {
                arr[j + 1] = arr[j];
            }
            arr[low] = temp;
        }
    }

    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int l = j + 1, r = nums.length - 1;
                while (l < r) {
                    if (nums[i] + nums[j] + nums[l] + nums[r] == target) {
                        res.add(Arrays.asList(i, j, l, r));
                        while (l < r && nums[l] == nums[l + 1]) l++;
                        while (l < r && nums[r] == nums[r - 1]) r--;
                        l++;
                        r--;
                    } else if (nums[i] + nums[j] + nums[l] + nums[r] < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {
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

    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            Integer val = map.get(nums1[i]);
            if (val == null) {
                map.put(nums1[i], 1);
            } else {
                val++;
                map.put(nums1[i], val);
            }
        }

        int[] res = new int[Math.max(nums1.length, nums2.length)];
        int index = 0;
        for (int i = 0; i < nums2.length; i++) {
            Integer val = map.get(nums2[i]);
            if (val != null && val > 0) {
                res[index++] = nums2[i];
                val--;
                map.put(nums2[i], val);
            }
        }
        return Arrays.copyOfRange(res, 0, index);
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length < 1) return "";
        String benchmark = strs[0];
        for (int i = 1; i < strs.length; i++) {
            benchmark = common(benchmark, strs[i]);
            if (benchmark.length() == 0) {
                return "";
            }
        }
        return benchmark;
    }

    public String common(String str1, String str2) {
        int len = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < len && str1.charAt(index) == str2.charAt(index)) {
            index++;
        }
        return str1.substring(0, index);
    }

    public void rotate(int[] nums, int k) {
        int leftNum = k % nums.length;
        r(nums, 0, nums.length - 1);
        r(nums, 0, leftNum - 1);
        r(nums, leftNum, nums.length - 1);
    }

    public void r(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    public int del(int[] nums, int val) {
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
        for (int i = 0; i < digits.length; i++) {
            int sum = i == 0 ? digits[i] + 1 : cap + digits[i];
            int d = sum % 10;
            cap = sum / 10;
            if (cap != 0) {
                digits[i] = d;
            } else {
                break;
            }
        }

        if (cap != 0) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            int index = 1;
            for (int j = 0; j < digits.length; j++) {
                res[index++] = digits[j];
            }
            return res;
        }
        return digits;
    }

    public String convert(String s, int numRows) {
        List<StringBuilder> list = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }

        int index = 0;
        int flag = -1;
        for (int i = 0; i < s.length(); i++) {
            list.get(index).append(s.charAt(i));
            if (index == 0 || numRows - index  - 1 == 0) flag = -flag;
            index += flag;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return String.valueOf(sb);
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode r = dummy, l = dummy;
        for (int i = 0; i <= n; i++) {
            l = l.next;
        }

        while (l != null) {
            l = l.next;
            r = r.next;
        }
        r.next = r.next.next;
        return dummy.next;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
                cur = cur.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
                cur = cur.next;
            }
        }

        if (l1 != null) {
            cur.next = l1;
        }

        if (l2 != null) {
            cur.next = l2;
        }

        return dummy.next;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            if (fast == slow) {
                return true;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode res = dummy;
        int cap = 0;
        while (l1 != null || l2 != null) {
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;
            int d = (cap + v1 + v2) % 10;
            cap = (cap + v1 + v2) / 10;

            dummy.next = new ListNode(d);
            dummy = dummy.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        if (cap != 0) {
            dummy.next = new ListNode(1);
        }

        return res.next;
    }

    public void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;
        while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;
            start++;
            end--;
        }
    }

}

class Singleton {
    private static Singleton instance = new Singleton();
    private Singleton(){}
    public static Singleton getInstance() {
        return instance;
    }
}

class Singleton2 {

    private static Singleton2 instance;
    private Singleton2(){}
    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

class Singleton3 {

    private Singleton3(){}
    private static class Instance {
        private static Singleton3 instance = new Singleton3();
    }
    public static Singleton3 getInstance() {
        return Singleton3.Instance.instance;
    }
}

enum Singleton4 {
    INSTANCE;
    public void fun() {

    }
}