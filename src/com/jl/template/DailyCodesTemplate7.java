package com.jl.template;

import com.jl.common.ListNode;

/**
 * 剑指
 */
public class DailyCodesTemplate7 {

    // 11 旋转数组的最小值（关键是中间和右边比）
    public int minRotateArr(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[high]) {
                high = mid;
            } else if (nums[mid] > nums[high]) {
                low = mid + 1;
            } else {
                high -= 1;
            }
        }
        return nums[low];
    }

    // 12 矩阵中是否存在指定路径
    // O(4^K*M*N) 其中k是字符串长度，m和n是矩阵的长和宽
    public boolean exist(char[][] board, String word) {
        if (board.length == 0 || board[0].length == 0) return false;
        boolean[][] visited = new boolean[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0) && dfs(board, i, j, 0, word, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, int x, int y, int index, String word, boolean[][] visited) {
        if (!isValid(board, x, y)) return false;
        if (board[x][y] != word.charAt(index)) return false;
        if (visited[x][y]) return false;
        if (index == word.length() - 1) return true;

        visited[x][y] = true;
        char c = board[x][y];
        boolean r = dfs(board, x + 1, y, index + 1, word, visited);
        boolean u = dfs(board, x, y + 1, index + 1, word, visited);
        boolean l = dfs(board, x - 1, y, index + 1, word, visited);
        boolean d = dfs(board, x, y - 1, index + 1, word, visited);
        if (r || u || l || d) return true;
        visited[x][y] = false;
        return false;
    }

    public boolean isValid(char[][] board, int x, int y) {
        if (x < 0 || x > board.length - 1 || y < 0 || y > board[0].length - 1) return false;
        return true;
    }

    // 13 矩阵可达范围
    public int movingCount(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        // 机器人从[0,0]坐标开始移动
        return dfs(m, n ,k , visited, 0, 0);
    }

    public int dfs(int m, int n, int k, boolean[][] visited, int x, int y){
        // 递归终止条件
        if((get(x) + get(y) > k) || x < 0 || x >= m || y < 0 || y >= n || visited[x][y]){
            return 0;
        }
        // 将该格子标记为已经访问过
        visited[x][y] = true;
        // 继续搜索四个方向
        return 1 + dfs(m, n , k, visited, x, y+1)
                + dfs(m, n , k, visited, x, y-1)
                + dfs(m, n , k, visited, x+1, y)
                + dfs(m, n , k, visited, x-1, y);
        // 回溯的返回过程
    }

    // 计算一个数的各个位数之和
    private int get(int x) {
        int res = 0;
        while (x != 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }

    // 15
    public int hammingWeight(int n) {
        // 返回结果
        int res = 0;
        while(n != 0){
            res = res + (n & 1);
            // 无符号右移1位
            n = n >>> 1;
        }
        return res;
    }

    // 16
    public double myPow(double x, int n) {
        long N = n;
        if (n < 0) {
            return 1 / quickPow(x, -N);
        }
        return quickPow(x, N);
    }

    public double quickPow(double x, long n) {
        if (n == 0) return 1;
        if (n % 2 == 0) {
            double res = quickPow(x, n / 2);
            return res * res;
        } else {
            double res = quickPow(x, n - 1);
            return x * res;
        }
    }

    // 18
    public ListNode deleteNode(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        while (head != null) {
            if (head.val == val) {
                ListNode next = head.next;
                pre.next = next;
            }
            head = head.next;
            pre = pre.next;
        }
        return dummy.next;
    }

    // 21
    public int[] exchange(int[] nums) {
        int l = 0, r = 0;
        for (; r < nums.length; r++) {
            if (nums[r] % 2 != 0) {
                int temp = nums[r];
                nums[r] = nums[l];
                nums[l] = temp;
                l++;
            }
        }
        return nums;
    }

    //
    public boolean verifyPostOrder(int[] nums) {

        return false;
    }

}
