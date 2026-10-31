import java.util.*;

class Solution {

    int LOG;
    int timer;
    int[][] up;
    int[] depth, prefix, nodeMask;
    int[] tin, tout;
    List<Integer>[] adj;
    Fenwick bit;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) 
            adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        LOG = 1;
        while ((1 << LOG) <= n) LOG++;
        up = new int[LOG][n];
        depth = new int[n];
        prefix = new int[n];
        nodeMask = new int[n];

        tin = new int[n];
        tout = new int[n];
        for (int i = 0; i < n; i++) 
            nodeMask[i] = 1 << (s.charAt(i) - 'a');
        timer = 0;
        Stack<int[]> st = new Stack<>();
        st.push(new int[]{0, -1, 0});
        while (!st.isEmpty()) {
            int[] cur = st.pop();
            int u = cur[0];
            int p = cur[1];
            int state = cur[2];
            if (state == 0) {
                tin[u] = timer++;
                up[0][u] = (p == -1) ? 0 : p;
                if (p == -1) prefix[u] = nodeMask[u];
                else {
                    depth[u] = depth[p] + 1;
                    prefix[u] = prefix[p] ^ nodeMask[u];
                }
                st.push(new int[]{u, p, 1});
                for (int i = adj[u].size() - 1; i >= 0; i--) {
                    int v = adj[u].get(i);
                    if (v != p) st.push(new int[]{v, u, 0});
                }
            } 
            else tout[u] = timer - 1;
        }
        for (int k = 1; k < LOG; k++) 
            for (int u = 0; u < n; u++) 
                up[k][u] = up[k - 1][up[k - 1][u]];
        
        bit = new Fenwick(n);
        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split("\\s+");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = nodeMask[u];
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;
                bit.rangeUpdate(tin[u], tout[u], delta);
                nodeMask[u] = newMask;
            } 
            else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int ancestor = lca(u, v);
                int currentU = prefix[u] ^ bit.query(tin[u]);
                int currentV = prefix[v] ^ bit.query(tin[v]);
                int mask = currentU ^ currentV ^ nodeMask[ancestor];
                ans.add(mask == 0 || (mask & (mask - 1)) == 0);
            }
        }
        return ans;
    }
    public int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        int diff = depth[u] - depth[v];
        for (int k = 0; k < LOG; k++) 
            if ((diff & (1 << k)) != 0) u = up[k][u];
        if (u == v) return u;
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }
        return up[0][u];
    }

    class Fenwick {
        int[] bit;
        int n;
        Fenwick(int n) {
            this.n = n;
            bit = new int[n + 2];
        }
        void update(int idx, int val) {
            idx++;
            while (idx <= n + 1) {
                bit[idx] ^= val;
                idx += idx & -idx;
            }
        }
        void rangeUpdate(int l, int r, int val) {
            update(l, val);
            update(r + 1, val);
        }
        int query(int idx) {
            idx++;
            int ans = 0;
            while (idx > 0) {
                ans ^= bit[idx];
                idx -= idx & -idx;
            }
            return ans;
        }
    }
}