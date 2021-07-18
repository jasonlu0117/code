package com.jl.template;

import java.util.*;

import com.jl.common.CommonClass;

/**
 * topK、bottomK模板
 * topK（求频率，堆排序使用list实现）
 * 移除K位数变成最小（单调栈/队列+贪心）
 */
public class DailyCodesTemplate5 {

    public void heapSort(int[] arr) {
        buildMaxHeap(arr, arr.length);

        for (int i = arr.length - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            adjustHeap(arr, i, 0);
        }
    }

    public void buildMaxHeap(int[] arr, int len) {
        for (int i = (len - 1) / 2; i >= 0; i--) {
            adjustHeap(arr, len, i);
        }
    }

    public void adjustHeap(int[] arr, int size, int parent) {
        // todo 注意这里是parent，不是size！！
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

    public void bottomK(int[] arr, int k) {
        int[] bottomK = new int[k];
        for (int i = 0; i < k; i++) {
            bottomK[i] = arr[i];
        }
        buildMaxHeap(bottomK, k);
        for (int i = k; i < arr.length; i++) {
            int temp = arr[i];
            if (temp < bottomK[0]) {
                int tmp = bottomK[0];
                bottomK[0] = arr[i];
                arr[i] = tmp;
                adjustHeap(bottomK, k, 0);
            }
        }

        CommonClass.print(bottomK);
    }

    public void buildMinHeap(int[] arr, int len) {
        for (int i = (len - 1) / 2; i >= 0; i--) {
            adjustMinHeap(arr, len, i);
        }
    }

    public void adjustMinHeap(int[] arr, int size, int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int least = parent;

        if (left < size && arr[left] < arr[least]) {
            least = left;
        }

        if (right < size && arr[right] < arr[least]) {
            least = right;
        }

        if (least != parent) {
            int temp = arr[least];
            arr[least] = arr[parent];
            arr[parent] = temp;
            adjustMinHeap(arr, size, least);
        }
    }

    public void topK(int[] arr, int k) {
        int[] topK = new int[k];
        for (int i = 0; i < k; i++) {
            topK[i] = arr[i];
        }
        buildMinHeap(topK, k);
        for (int i = k; i < arr.length; i++) {
            int temp = arr[i];
            if (temp > topK[0]) {
                int tmp = topK[0];
                topK[0] = arr[i];
                arr[i] = tmp;
                adjustMinHeap(topK, k, 0);
            }
        }
        CommonClass.print(topK);
    }

    // todo
    // 347
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        List<Element> list = new ArrayList<>(map.size());
        for (Integer key : map.keySet()) {
            Element e = new Element();
            e.setIndex(key);
            e.setValue(map.get(key));
            list.add(e);
        }

        int[] res = topK(list, k);
        CommonClass.print(res);
        return res;
    }

    private class Element {
        public Integer index;
        public Integer value;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public void buildMinHeap(List<Element> arr, int len) {
        for (int i = (len - 1) / 2; i >= 0; i--) {
            adjustHeap(arr, len, i);
        }
    }

    public void adjustHeap(List<Element> arr, int size, int parent) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int least = parent;

        if (left < size && arr.get(left).getValue() < arr.get(least).getValue()) {
            least = left;
        }

        if (right < size && arr.get(right).getValue() < arr.get(least).getValue()) {
            least = right;
        }

        if (least != parent) {
            Collections.swap(arr, least, parent);
            adjustHeap(arr, size, least);
        }
    }

    public int[] topK(List<Element> arr, int k) {
        List<Element> topK = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            topK.add(arr.get(i));
        }
        buildMinHeap(topK, k);
        for (int i = k; i < arr.size(); i++) {
            Element temp = arr.get(i);
            if (temp.getValue() > topK.get(0).getValue()) {
                topK.get(0).setIndex(temp.getIndex());
                topK.get(0).setValue(temp.getValue());
                adjustHeap(topK, k, 0);
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < res.length; i++) {
            res[i] = topK.get(i).getIndex();
        }
        return res;
    }

    public int[] topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o1) - map.get(o2);
            }
        });

        for (Integer key : map.keySet()) {
            if (queue.size() < k) {
                queue.add(key);
            } else if (map.get(key) > map.get(queue.peek())) {
                queue.poll();
                queue.add(key);
            }
        }

        int[] res = new int[k];
        int index = 0;
        while (!queue.isEmpty()) {
            res[index++] = queue.poll();
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = new int[]{3, 6, 8, 4, 1, 9, 2, 1, 6};
//        int[] a = new int[]{3, 8, 4, 9, 2, 1, 6};
        DailyCodesTemplate5 d = new DailyCodesTemplate5();
        d.heapSort(a);
        CommonClass.print(a);
        d.bottomK(a, 5);
        d.topK(a, 5);
        d.topKFrequent(new int[]{-1, 1, 4, -4, 3, 5, 4, -2, 3, -1}, 3);

        System.out.println(d.removeKdigits("1432219", 3));
        int[] r = d.MergeNOrderArrays(new int[][]{{1,4,5},{1,3,6}});
        CommonClass.print(r);


        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(3);
        list1.add(10);
        list1.add(5);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(0);
        list2.add(5);
        list2.add(3);

        List<Integer> res = d.killProcess(list1, list2, 3);
    }


    /**
     * 思路：首先将N个数组的第一位放到PriorityQueue，循环取出优先队列的首位（最小值）放入result数组中，并且插入该首位数字所在数组的下一个数字（如果存在），直到所有数字均被加入到result数组即停止（N*L）次。
     * 时间复杂度：O(N*LlogN)
     * 空间复杂度：O(N)
     */
    public int[] MergeNOrderArrays(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;

        if (row == 0 || col == 0) return new int[0];
        for (int i = 0; i < row; i++) {
            if (arr[i].length != col) return new int[0];
        }

        int[] res = new int[row * col];
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.val - o2.val;
            }
        });

        int[] index = new int[col];
        for (int i = 0; i < row; i++) {
            Node node = new Node(arr[i][index[i]++], i);
            priorityQueue.add(node);
        }

        int idx = 0;
        while (idx < row * col) {
            Node min = priorityQueue.poll();
            res[idx++] = min.val;
            if (index[min.index] < col) {
                priorityQueue.add(new Node(arr[min.index][index[min.index]++], min.index));
            }
        }
        return res;
    }

    private class Node {
        private int val;
        private int index;

        public Node(int val, int index) {
            this.val = val;
            this.index = index;
        }
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] num1Copy = new int[m];
        System.arraycopy(nums1, 0, num1Copy, 0, m);

        int p1 = 0, p2 = 0;
        int index = 0;

        while ((p1 < m) && (p2 < n)) {
            nums1[index++] = num1Copy[p1] < nums2[p2] ? num1Copy[p1++] : nums2[p2++];
        }

        while (p1 < m) {
            nums1[index++] = num1Copy[p1++];
        }

        while (p2 < n) {
            nums1[index++] = nums2[p2++];
        }
    }


    public String removeKdigits(String num, int k) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > c) {
                deque.pollLast();
                k--;
            }
            deque.addLast(c);
        }

        // 意思是如果k不为0，说明队列中的位数还大于k，继续出队列！
        for (int i = 0; i < k; i++) {
            deque.pollLast();
        }

        StringBuilder sb = new StringBuilder();
        boolean leadZero = true;
        while (!deque.isEmpty()) {
            char d = deque.pollFirst();
            if (leadZero && d == '0') {
                continue;
            }
            leadZero = false;
            sb.append(d);
        }
        return sb.length() == 0 ? "0" : String.valueOf(sb);
    }

    public List < Integer > killProcess(List < Integer > pid, List < Integer > ppid, int kill) {
        List<Integer> res = new ArrayList<>();
        if (pid == null || pid.size() == 0 || ppid == null || ppid.size() == 0) return res;
        Map<Integer, List<Integer>> map = new HashMap<>();
        int len = ppid.size();
        for (int i = 0; i < len; i++) {
            int ppidVal = ppid.get(i);
            int pidVal = pid.get(i);
            if (!map.containsKey(ppidVal)) {
                List<Integer> list = new ArrayList<>();
                list.add(pidVal);
                map.put(ppidVal, list);
            } else {
                List<Integer> list = map.get(ppidVal);
                list.add(pidVal);
                map.put(ppidVal, list);
            }
        }

        if (!map.containsKey(kill)) {
            res.add(kill);
        } else {
            res.add(kill);
            List<Integer> list = map.get(kill);
            getChildren(list, map, res);
        }
        return res;
    }


    public void getChildren(List<Integer> list, Map<Integer, List<Integer>> map, List<Integer> res) {
        for (int i = 0; i < list.size(); i++) {
            if (!map.containsKey(list.get(i))) {
                res.add(list.get(i));
            } else {
                res.add(list.get(i));
                getChildren(map.get(list.get(i)), map, res);
            }
        }
    }

    public List<Integer> killProcess2(List<Integer> ppid, List<Integer> pid, int kill) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int len = ppid.size();
        for (int i = 0; i < len; i++) {
            int p = pid.get(i);
            int pp = ppid.get(i);
            if (!map.containsKey(pp)) {
                map.put(pp, new LinkedList<>());
            }
            map.get(pp).add(p);
        }

        List<Integer> res = new ArrayList<>();
        getChildren(map, kill, res);
        return res;
    }

    public void getChildren(Map<Integer, List<Integer>> map, int kill, List<Integer> res) {
        res.add(kill);
        if (!map.containsKey(kill)) {
            return;
        }
        for (Integer key : map.get(kill)) {
            getChildren(map, key, res);
        }
    }

    public List<Integer> killProcess3(List<Integer> ppid, List<Integer> pid, int kill) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int len = ppid.size();
        for (int i = 0; i < len; i++) {
            int p = pid.get(i);
            int pp = ppid.get(i);
            if (!map.containsKey(pp)) {
                map.put(pp, new LinkedList<>());
            }
            map.get(pp).add(p);
        }

        List<Integer> res = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(kill);
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            res.add(node);
            if (map.containsKey(node)) {
                for (Integer key : map.get(node)) {
                    queue.add(key);
                }
            }
        }
        return res;
    }

}
