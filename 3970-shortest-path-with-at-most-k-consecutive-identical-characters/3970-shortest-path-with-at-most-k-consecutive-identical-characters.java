class Solution {
    public static boolean check(String s, int k){
        int n = s.length();
        char ch = ' ';
        int count =0 ;
        for(int i=n-1; i>=0;i--){
            if(ch == ' ') {
                ch = s.charAt(i);
                count =1;
                continue;
            }
            else if (ch != s.charAt(i)) return true;
            else{
                if(count +1 > k ) return false;
                else count++;
            }
        }
        return true;
    }
    public int shortestPath(int n, int[][] edge, String labels, int k) {
        List<int[]>[] list = new ArrayList[n];
        for(int i=0;i<n;i++) list[i] = new ArrayList<>();
        int len = edge.length;
        for(int i=0;i<len;i++) list[edge[i][0]] .add(new int[]{ edge[i][1] , edge[i][2] });
        long[][] dist = new long[n][k + 1];
        for (long[] d : dist) Arrays.fill(d, Long.MAX_VALUE);
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        if (k == 0) return -1;
        StringBuilder str = new StringBuilder();
        str.append(labels.charAt(0));
        dist[0][1] = 0;
        pq.offer(new long[]{0, 0, 1});
        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long wt = curr[0];
            int node = (int) curr[1];
            int count = (int) curr[2];
            if (wt != dist[node][count]) continue;
            if (node == n - 1) {
                return (int) wt;
            }
            for (int[] next : list[node]) {
                int v = next[0];
                int w = next[1];
                int newCount;
                if (labels.charAt(node) == labels.charAt(v)) newCount = count + 1;
                else newCount = 1;
                if (newCount > k) continue;
                long newWt = wt + w;
                if (newWt < dist[v][newCount]) {
                    dist[v][newCount] = newWt;
                    pq.offer(new long[]{
                        newWt, v, newCount
                    });
                }
            }
        }
        return -1;
    }
}