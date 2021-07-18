package com.jl.template;

import java.util.*;

import com.jl.common.CommonClass;
import com.jl.common.ListNode;
import com.jl.common.TreeNode;

/**
 * yyy 下部题集
 */
public class DailyCodesTemplate4 {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            if (map.containsKey(key) && map.get(key) != i) {
                return new int[]{i, map.get(key)};
            }
        }
        return new int[0];
    }

    List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.equals("")) return res;
        Map<String, List<String>> map = new HashMap<>();
        map.put("2", Arrays.asList("a", "b", "c"));
        map.put("3", Arrays.asList("d", "e", "f"));
        map.put("4", Arrays.asList("g", "h", "i"));
        map.put("5", Arrays.asList("j", "k", "l"));
        map.put("6", Arrays.asList("m", "n", "o"));
        map.put("7", Arrays.asList("p", "q", "r", "s"));
        map.put("8", Arrays.asList("t", "u", "v"));
        map.put("9", Arrays.asList("w", "x", "y", "z"));

        bk(digits, new ArrayDeque<>(), 0, map);
        return res;
    }

    public void bk(String digits, Deque<String> path, int start, Map<String, List<String>> map) {
        if (path.size() == digits.length()) {
            List<String> list = new ArrayList<>(path);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
            }
            res.add(String.valueOf(sb));
            return;
        }

        if (start > digits.length() - 1) return;

        String index = String.valueOf(digits.charAt(start));
        List<String> strList = map.get(index);
        for (int i = 0; i < strList.size(); i++) {
            path.addLast(strList.get(i));
            bk(digits, path, start + 1, map);
            path.removeLast();
        }
    }

    // todo
    // 20
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                count++;
                stack.push(c);
            } else {
                if (!stack.isEmpty()) {
                    char d = stack.peek();
                    if (d == '(' && c == ')') {
                        count--;
                        stack.pop();
                    } else if (d == '{' && c == '}') {
                        count--;
                        stack.pop();
                    } else if (d == '[' && c == ']') {
                        count--;
                        stack.pop();
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return count == 0 ? true : false;
    }

    // todo
    // 24
    public ListNode swapPairs(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode preStart = dummy, start = head,
                nextEnd = head.next, end = head;
        while (end != null && end.next != null) {
            for (int i = 0; i < 1; i++) {
                end = end.next;
            }
            nextEnd = end.next;

            // 先反转，再两边连接
            swap(start, nextEnd);
            preStart.next = end;
            start.next = nextEnd;

            // 然后pre左边=左边，左边和右边=next右边
            preStart = start;
            start = nextEnd;
            end = nextEnd;
        }
        return dummy.next;
    }

    // todo 反转链表模板，tail为反转末尾+1的元素。
    private ListNode swap(ListNode head, ListNode tail) {
        ListNode pre = null;
        while (head != tail) {
            ListNode node = head.next;
            head.next = pre;
            pre = head;
            head = node;
        }
        return pre;
    }

    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        //从第3个链表往后进行交换
        ListNode third = swapPairs2(head.next.next);
        //从第3个链表往后都交换完了，我们只需要交换前两个链表即可,
        //这里我们把链表分为3组，分别是第1个节点，第2个节点，后面
        //的所有节点，也就是1→2→3，我们要把它变为2→1→3
        ListNode second = head.next;
        head.next = third;
        second.next = head;
        return second;
    }

    public int removeDuplicates(int[] nums) {
        Arrays.sort(nums);
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            nums[index++] = nums[i];
        }
        return index;
    }

    // todo
    // 31
    public void nextPermutation(int[] nums) {
        boolean isHave = false;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (isHave) break;
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[i] < nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    Arrays.sort(nums, i + 1, nums.length);
                    isHave = true;
                    break;
                }
            }
        }

        if (!isHave) {
            Arrays.sort(nums);
        }

        CommonClass.print(nums);
    }

    // todo
    // 34
    public int[] searchRange(int[] nums, int target) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else if (nums[mid] > target) {
                high = mid;
            } else if (nums[mid] == target) {
                int l = mid, r = mid;
                // 防止越界
                while (l - 1 >= 0 && nums[l] == nums[l - 1]) l--;
                while (r + 1 < nums.length && nums[r] == nums[r + 1]) r++;
                return new int[]{l, r};
            }
        }
        return new int[]{-1, -1};
    }

    // todo
    // 35
    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0) return 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            } else if (nums[i] > target) {
                return i;
            }
        }
        return nums.length;
    }

    // todo
    public int trap(int[] height) {

        return 0;
    }

    // todo
    // 53
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    // todo
    // 55
    public boolean canJump(int[] nums) {
        int mostRight = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= mostRight) {
                mostRight = Math.max(mostRight, i + nums[i]);
                if (mostRight >= nums.length - 1) return true;
            }
        }
        return false;
    }

    // todo
    // 56
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0 || intervals[0].length == 0) return new int[0][0];
        int row = intervals.length;
        int col = intervals[0].length;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<int[]> res = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 0; i < row; i++) {
            if (right < intervals[i][0]) {
                res.add(new int[]{left, right});
                left = intervals[i][0];
                right = intervals[i][1];
            } else {
                right = Math.max(right, intervals[i][1]);
            }
        }
        res.add(new int[]{left, right});
        return res.toArray(new int[][]{});
    }

    // todo
    // 59
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int left = 0, up = 0, right = n - 1, down = n - 1;
        int index = 1;
        while (true) {
            for (int i = left; i <= right; i++) {
                res[up][i] = index++;
            }
            up++;
            if (up > down) break;

            for (int i = up; i <= down; i++) {
                res[i][right] = index++;
            }
            right--;
            if (right < left) break;

            for (int i = right; i >= left; i--) {
                res[down][i] = index++;
            }
            down--;
            if (down < up) break;

            for (int i = down; i >= up; i--) {
                res[i][left] = index++;
            }
            left++;
            if (left > right) break;
        }
        return res;
    }

    // todo
    // 62
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    // todo
    // 70
    public int climbStairs(int n) {
        if (n <= 3) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // todo
    // 83 [1,2,3,3,4,4,5] -> [1,2,3,4,5]
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode start = head, end = head.next;
        while (end != null) {
            while (end != null && end.val == start.val) {
                end = end.next;
            }
            start.next = end;
            start = end;
            if (end != null) end = end.next;
        }
        return dummy.next;
    }

    // todo
    // 82 [1,2,3,3,4,4,5] -> [1,2,5]
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode start = head, end = head.next, startPre = dummy;
        boolean hasDuplicate = false;
        while (end != null) {
            while (end != null && end.val == start.val) {
                hasDuplicate = true;
                end = end.next;
            }
            if (hasDuplicate) {
                startPre.next = end;
                hasDuplicate = false;
            } else {
                startPre = start;
            }
            start = end;
            if (end != null) end = end.next;
        }
        return dummy.next;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        return isSame(p, q);
    }

    private boolean isSame(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p != null && q == null) return false;
        if (p == null && q != null) return false;
        // todo val!
        if (p.val != q.val) return false;
        boolean l = isSame(p.left, q.left);
        boolean r = isSame(p.right, q.right);
        return l && r;
    }

    // todo
    // 112
    boolean isHas = false;

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        dfs(root, sum);
        return isHas;
    }

    public void dfs(TreeNode root, int sum) {
        if (root == null) return;
        if (isHas) return;

        sum -= root.val;
        if (root.right == null && root.left == null) {
            if (sum == 0) isHas = true;
            sum += root.val;
            return;
        }

        if (root.left != null) {
            dfs(root.left, sum);
        }
        if (root.right != null) {
            dfs(root.right, sum);
        }
        sum += root.val;
    }

    // todo
    // 113
    List<List<Integer>> res2 = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) return res2;
        bk1(root, new ArrayDeque<>(), sum);
        return res2;
    }

    public void bk1(TreeNode root, Deque<Integer> path, int sum) {
        if (root == null) return;
        path.addLast(root.val);
        sum -= root.val;
        if (root.right == null && root.left == null) {
            if (sum == 0) {
                res2.add(new ArrayList<>(path));
            }
            sum += root.val;
            path.removeLast();
            return;
        }

        if (root.left != null) {
            bk1(root.left, path, sum);
        }
        if (root.right != null) {
            bk1(root.right, path, sum);
        }
        sum += root.val;
        path.removeLast();
    }

    List<Integer> res3 = new ArrayList<>();

    public int sumNumbers(TreeNode root) {
        if (root == null) return 0;
        bk2(root, new ArrayDeque<>());
        int count = 0;
        for (int i = 0; i < res3.size(); i++) {
            count += res3.get(i);
        }
        return count;
    }

    public void bk2(TreeNode root, Deque<Integer> path) {
        if (root == null) return;
        path.addLast(root.val);
        if (root.right == null && root.left == null) {
            List<Integer> list = new ArrayList<>(path);
            int num = 0;
            for (int i = 0; i < list.size(); i++) {
                num *= 10;
                num += list.get(i);
            }
            res3.add(num);
            return;
        }

        if (root.left != null) {
            bk2(root.left, path);
            path.removeLast();
        }
        if (root.right != null) {
            bk2(root.right, path);
            path.removeLast();
        }
    }

    // todo
    // 116
    public Node connect(Node root) {
        if (root == null) return null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();

            Node nodePre = null;
            Node node = null;
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    nodePre = queue.poll();
                    node = nodePre;
                } else {
                    node = queue.poll();
                    nodePre.next = node;
                    nodePre = nodePre.next;
                }
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            nodePre.next = null;
        }
        return root;
    }

    private class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    // todo 先走步，再判断
    // 141
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    // todo
    // 142
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                ListNode ptr = head;
                while (slow != ptr) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    // todo
    // 143
    public void reorderList(ListNode head) {
        if (head == null) return;
        ListNode l = head, r = head,
                lPre = head, rPre = head;
        while (r != null && r.next != null) {
            for (int i = 0; i < 1; i++) {
                r = r.next;
            }

            ListNode newHead = swap(r, null);
            rPre.next = newHead;

            rPre = rPre.next;
            r = rPre;
        }

    }

    // todo
    // 147
//    public ListNode insertionSortList(ListNode head) {
//        if (head == null) return null;
//        ListNode dummy = new ListNode(0);
//
//
//        while (head != null) {
//            ListNode next = head.next;
//            ListNode cur = dummy;
//
//        }
//    }

    public int evalRPN(String[] tokens) {
        if (tokens.length == 0) return 0;
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            String s = tokens[i];
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                Integer two = Integer.valueOf(stack.pop());
                Integer one = Integer.valueOf(stack.pop());
                if (s.equals("+")) {
                    stack.push(String.valueOf(one + two));
                } else if (s.equals("-")) {
                    stack.push(String.valueOf(one - two));
                } else if (s.equals("*")) {
                    stack.push(String.valueOf(one * two));
                } else if (s.equals("/")) {
                    stack.push(String.valueOf(one / two));
                }
            } else {
                stack.push(s);
            }
        }
        return Integer.valueOf(stack.pop());
    }

    public String reverseWords(String s) {
        int l = 0, r = s.length() - 1;
        while (l <= r && s.charAt(l) == ' ') l++;
        while (l <= r && s.charAt(r) == ' ') r--;
        s = s.substring(l, r + 1);

        char[] c = s.toCharArray();
        int index = 0;
        for (int i = 0; i < c.length; i++) {
            if (i > 0 && c[i] == ' ' && c[i - 1] == ' ') continue;
            c[index++] = c[i];
        }
        c = Arrays.copyOfRange(c, 0, index);

        swap(c, 0, c.length - 1);
        int start = 0, end = 0;
        while (end < c.length - 1) {
            if (c[end] == ' ') {
                // todo!
                swap(c, start, end - 1);
                start = end + 1;
                end = start;
            }
            end++;
        }
        swap(c, start, c.length - 1);
        return String.valueOf(c);
    }

    public void swap(char[] c, int start, int end) {
        while (start < end) {
            char temp = c[start];
            c[start] = c[end];
            c[end] = temp;
            start++;
            end--;
        }
    }

    // ------------ 前200 ---------------


    public static void main(String[] args) {
        DailyCodesTemplate4 d = new DailyCodesTemplate4();
        ListNode l1 = new ListNode(4);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(1);
        ListNode l4 = new ListNode(3);
//        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
//        l4.next = l5;
//        d.swapPairs(l1);
//        int[] r = d.searchRange(new int[]{1}, 1);
//        d.searchInsert(new int[]{1, 3, 5, 6}, 0);
//        ListNode r = d.deleteDuplicates(l1);
//        ListNode.print(r);

//        List<List<Character>> r = d.partition("aab");
//        for (int i = 0; i < r.size(); i++) {
//            List<Character> list = r.get(i);
//            for (int j = 0; j < list.size(); j++) {
//                System.out.print(list.get(j) + " ");
//                System.out.println();
//            }
//        }

//        ListNode r = d.insertionSortList(l1);
//        ListNode.print(r);

        d.reverseWords("a good   example");
    }

}
