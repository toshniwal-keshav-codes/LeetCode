class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];
        for(int i:nums) min = min> i? i: min;
        int ans=0;
        for(int i:nums) ans+= i-min;
        return ans;
    }
}