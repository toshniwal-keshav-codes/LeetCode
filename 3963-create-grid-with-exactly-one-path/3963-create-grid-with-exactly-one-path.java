class Solution {
    public String[] createGrid(int m, int n) {
        String grid[] = new String[m];
        String str = ".";
        grid[0] = str.repeat(n);
        str = "#";
        str = str.repeat(n-1) + ".";
        for(int i=1;i<m;i++) grid[i] = str;
        return grid;
    }
}