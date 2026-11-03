class Solution {
    public int minMoves(int[] nums) {
        int sum = 0, max =0 , n= nums.length;
        for(int x : nums ){
            sum+= x;
            max = Math.max (max, x);
        }
        return (max *n) - sum;
    }
}