class Solution {
    public String[] createGrid(int m, int n) {
        String grid[] = new String[m];
        String str = ".";
        grid[0] = str.repeat(n);
        str = "#";
        for(int i=1;i<m;i++) 
            grid[i] = str.repeat(n-1) + ".";
        
        return grid;
    }
}