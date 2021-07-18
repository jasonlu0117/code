package com.jl.template;


import java.util.*;

import com.jl.common.TreeNode;

/**
 * 二叉树全，回溯全
 */
public class DailyCodesTemplate2 {

    public void pre(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) stack.add(node.right);
            if (node.left != null) stack.add(node.left);
        }
    }

    public void in(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.add(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.print(root.val + " ");
                root = root.right;
            }
        }
    }

    public void level(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node.val + " ");
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
    }

    public void inOrderMorris(TreeNode root) {
        if (null == root) {
            return;
        }
        TreeNode cur = root, predecessor = null;
        while (null != cur) {
            predecessor = cur.left;
            if (null != predecessor) {
                // 获取左子树上的最右结点
                while (null != predecessor.right && cur != predecessor.right) {
                    predecessor = predecessor.right;
                }

                if (null == predecessor.right) {
                    // 左子树上的最右结点 指向当前结点
                    predecessor.right = cur;
                    cur = cur.left;
                    continue; // 注意这里的continue;
                } else {
                    // 复原空指针
                    predecessor.right = null;
                }
            }
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        System.out.println();
        return;
    }

    public TreeNode invert(TreeNode root) {
        if (root == null) return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invert(root.left);
        invert(root.right);
        return root;
    }

    public boolean isSymetric(TreeNode root) {
        if (root == null) return true;
        return isSym(root.left, root.right);
    }

    public boolean isSym(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 != null && t2 == null) return false;
        if (t1 == null && t2 != null) return false;
        if (t1.val != t2.val) return false;

        boolean lr = isSym(t1.left, t2.right);
        boolean rr = isSym(t1.right, t2.left);
        return lr && rr;
    }

    public int dep(TreeNode root) {
        if (root == null) return 0;
        int l = dep(root.left);
        int r = dep(root.right);
        return Math.max(l, r) + 1;
    }

    public int mindep(TreeNode root) {
        if (root == null) return 0;
        int l = mindep(root.left);
        int r = mindep(root.right);
        if (root.left == null && root.right != null) {
            return r + 1;
        }
        if (root.left != null && root.right == null) {
            return l + 1;
        }
        return Math.min(l, r) + 1;
    }

    public boolean isBalance(TreeNode root) {
        if (root == null) return true;
        int l = dep(root.left);
        int r = dep(root.right);
        if (Math.abs(l - r) > 1) return false;
        return isBalance(root.left) && isBalance(root.right);
    }

    List<String> r = new ArrayList<>();

    public List<String> load(TreeNode root) {
        if (root == null) return r;
        backtrack(root, new ArrayDeque<>());
        return r;
    }

    public void backtrack(TreeNode root, Deque<TreeNode> path) {
        if (root == null) return;
        path.addLast(root);
        if (root.left == null && root.right == null) {
            List<TreeNode> list = new ArrayList<>(path);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).val);
            }
            r.add(String.valueOf(sb));
            return;
        }

        if (root.left != null) {
            backtrack(root.left, path);
            path.removeLast();
        }
        if (root.right != null) {
            backtrack(root.right, path);
            path.removeLast();
        }
    }

    // todo
    public int leftSum(TreeNode root) {
        if (root == null) return 0;
        int l = leftSum(root.left);
        int r = leftSum(root.right);
        int mid = 0;
        if (root.left != null && root.left.left == null && root.left.right == null) {
            mid = root.left.val;
        }
        return mid + l + r;
    }

    // todo !
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (postorder.length == 0) return null;
        int mid = postorder[postorder.length - 1];
        if (postorder.length <= 1) return new TreeNode(mid);
        int midIndex = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == mid) {
                midIndex = i;
                break;
            }
        }

        TreeNode root = new TreeNode(mid);
        int[] inL = Arrays.copyOfRange(inorder, 0, midIndex);
        int[] inR = Arrays.copyOfRange(inorder, midIndex + 1, inorder.length);
        int[] postL = Arrays.copyOfRange(postorder, 0, midIndex);
        // todo !
        int[] postR = Arrays.copyOfRange(postorder, midIndex, postorder.length - 1);
        root.left = buildTree(inL, postL);
        root.right = buildTree(inR, postR);
        return root;
    }

    // todo
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;
        int mid = preorder[0];
        if (preorder.length == 1) return new TreeNode(mid);
        int midIndex = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (mid == inorder[i]) {
                midIndex = i;
                break;
            }
        }

        TreeNode root = new TreeNode(mid);
        int[] inL = Arrays.copyOfRange(inorder, 0, midIndex);
        int[] inR = Arrays.copyOfRange(inorder, midIndex + 1, inorder.length);
        int[] preL = Arrays.copyOfRange(preorder, 1, midIndex + 1);
        int[] preR = Arrays.copyOfRange(preorder, midIndex + 1, preorder.length);
        root.left = buildTree2(preL, inL);
        root.right = buildTree2(preR, inR);
        return root;
    }

    public TreeNode mergeTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        if (t1 != null && t2 == null) return t1;
        if (t1 == null && t2 != null) return t2;
        t1.val = t1.val + t2.val;
        t1.left = mergeTree(t1.left, t2.left);
        t1.right = mergeTree(t1.right, t2.right);
        return t1;
    }

    TreeNode pre = null;

    public boolean isBST(TreeNode root) {
        if (root == null) return true;
        boolean l = isBST(root.left);
        if (pre != null && pre.val >= root.val) return false;
        pre = root;
        boolean r = isBST(root.right);
        return l && r;
    }

    // todo
    public TreeNode ancestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || q == root || p == root) return root;
        TreeNode l = ancestor(root.left, p, q);
        TreeNode r = ancestor(root.right, p, q);
        if (l != null && r != null) return root;
        if (l != null && r == null) return l;
        if (l == null && r != null) return r;
        return null;
    }

    // todo
    public TreeNode ancestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        while (root != null) {
            if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else {
                return root;
            }
        }
        return null;
    }

    // todo
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            TreeNode node = new TreeNode(val);
            return node;
        }

        if (root.val > val) root.left = insertIntoBST(root.left, val);
        if (root.val < val) root.right = insertIntoBST(root.right, val);
        return root;
    }

    // todo
    public TreeNode delBST(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val > val) {
            root.left = delBST(root.left, val);
        } else if (root.val < val) {
            root.right = delBST(root.right, val);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left != null && root.right == null) {
                return root.left;
            } else if (root.left == null && root.right != null) {
                return root.right;
            } else if (root.left != null && root.right != null) {
                TreeNode left = root.left;
                TreeNode node = root.right;
                while (node.left != null) {
                    node = node.left;
                }
                node.left = left;
                root = root.right;
                return root;
            }
        }
        return root;
    }

    // todo
    public TreeNode trimTree(TreeNode root, int L, int R) {
        if (root == null) return null;
        if (root.val < L) {
            TreeNode right = trimTree(root.right, L, R);
            return right;
        }
        if (root.val > R) {
            TreeNode left = trimTree(root.left, L, R);
            return left;
        }
        root.left = trimTree(root.left, L, R);
        root.right = trimTree(root.right, L, R);
        return root;
    }

    int pre2 = 0;

    public TreeNode addTree(TreeNode root) {
        if (root == null) return null;
        TreeNode r = addTree(root.right);
        root.val = root.val + pre2;
        pre2 = root.val;
        TreeNode l = addTree(root.left);
        root.left = l;
        root.right = r;
        return root;
    }

    TreeNode first = null;
    TreeNode second = null;
    public void recoverTree(TreeNode root) {
        if (root == null) return;
        inOrder(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
        return;
    }

    public void inOrder(TreeNode root) {
        if (root == null) return;
        inOrder(root.left);
        if (pre != null && pre.val > root.val) {
            if (first == null) {
                first = pre;
                second = root;
            } else {
                second = root;
            }
        }
        pre = root;
        inOrder(root.right);
    }

    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length < 1) return res;
        bk(candidates, new ArrayDeque<>(), 0, target, 0);
        return res;
    }

    public void bk(int[] candidates, Deque<Integer> path, int sum, int target, int start) {
        if (sum == target) {
            List<Integer> temp = new ArrayList<>(path);
            res.add(temp);
            return;
        }

        if (sum > target) return;

        for (int i = start; i < candidates.length; i++) {
            path.addLast(candidates[i]);
            bk(candidates, path, sum + candidates[i], target, i);
            path.removeLast();
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length < 1) return res;
        Arrays.sort(candidates);
        bk2(candidates, new ArrayDeque<>(), 0, target, 0);
        return res;
    }

    public void bk2(int[] candidates, Deque<Integer> path, int sum, int target, int start) {
        if (sum == target) {
            List<Integer> temp = new ArrayList<>(path);
            res.add(temp);
            return;
        }

        if (sum > target) return;

        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) continue;
            path.addLast(candidates[i]);
            bk2(candidates, path, sum + candidates[i], target, i + 1);
            path.removeLast();
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        if (nums.length < 1) return res;
        boolean[] visited = new boolean[nums.length];
        bk3(nums, new ArrayDeque<>(), visited);
        return res;
    }

    public void bk3(int[] nums, Deque<Integer> path, boolean[] visited) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            path.addLast(nums[i]);
            bk3(nums, path, visited);
            visited[i] = false;
            path.removeLast();
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums.length < 1) return res;
        Arrays.sort(nums);
        boolean[] visited = new boolean[nums.length];
        bk4(nums, new ArrayDeque<>(), visited);
        return res;
    }

    public void bk4(int[] nums, Deque<Integer> path, boolean[] visited) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;
            if (i > 0 && nums[i] == nums[i - 1] && visited[i - 1] == true) continue;
            visited[i] = true;
            path.addLast(nums[i]);
            bk4(nums, path, visited);
            visited[i] = false;
            path.removeLast();
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        if (n < 1) return res;
        bk5(n, new ArrayDeque<>(), k, 1);
        return res;
    }

    public void bk5(int n, Deque<Integer> path, int k, int start) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i <= n; i++) {
            path.addLast(i);
            bk5(n, path, k, i + 1);
            path.removeLast();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        if (nums.length < 1) return res;
        bk6(nums, new ArrayDeque<>(), 0);
        return res;
    }

    public void bk6(int[] nums, Deque<Integer> path, int start) {
        res.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            path.addLast(nums[i]);
            bk6(nums, path, i + 1);
            path.removeLast();
        }
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums.length < 1) return res;
        Arrays.sort(nums);
        bk7(nums, new ArrayDeque<>(), 0);
        return res;
    }

    // todo
    // 113
    List<List<Integer>> res3 = new ArrayList<>();

    public void bk7(int[] nums, Deque<Integer> path, int start) {
        res.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            path.addLast(nums[i]);
            bk7(nums, path, i + 1);
            path.removeLast();
        }
    }

    /**
     * 只有求路径和的时候，才在终止条件回溯一次，再在left、right下面总的再回溯一次。
     * 且都是再开头就path.add
     */
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
        if (root.left == null && root.right == null) {
            if (sum == 0) {
                isHas = true;
            }
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

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) return res3;
        dfs(root, new ArrayDeque<>(), sum);
        return res3;
    }

    public void dfs(TreeNode root, Deque<Integer> path, int sum) {
        if (root == null) return;
        path.addLast(root.val);
        sum -= root.val;
        if (root.left == null && root.right == null) {
            if (sum == 0) {
                res3.add(new ArrayList<>(path));
            }
            sum += root.val;
            path.removeLast();
            return;
        }

        if (root.left != null) {
            dfs(root.left, path, sum);
        }
        if (root.right != null) {
            dfs(root.right, path, sum);
        }
        sum += root.val;
        path.removeLast();
    }

    // todo
    // 257
    List<String> res4 = new ArrayList<>();

    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) return res4;
        bk9(root, new ArrayDeque<>());
        return res4;
    }

    public void bk9(TreeNode root, Deque<Integer> path) {
        if (root == null) return;
        path.addLast(root.val);
        if (root.left == null && root.right == null) {
            List<Integer> list = new ArrayList<>(path);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i)).append("->");
            }
            String s = String.valueOf(sb).substring(0, sb.length() - 2);
            res4.add(s);
            return;
        }

        if (root.left != null) {
            bk9(root.left, path);
            path.removeLast();
        }
        if (root.right != null) {
            bk9(root.right, path);
            path.removeLast();
        }
    }

    List<String> rs = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.equals("")) return rs;

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
        return rs;
    }

    public void bk(String digits, Deque<String> path, int start, Map<String, List<String>> map) {
        if (path.size() == digits.length()) {
            List<String> list = new ArrayList<>(path);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
            }
            rs.add(String.valueOf(sb));
        }

        if (start > digits.length() - 1) return;
        List<String> s = map.get(String.valueOf(digits.charAt(start)));
        for (int i = 0; i < s.size(); i++) {
            path.addLast(s.get(i));
            bk(digits, path, start + 1, map);
            path.removeLast();
        }
    }

}
