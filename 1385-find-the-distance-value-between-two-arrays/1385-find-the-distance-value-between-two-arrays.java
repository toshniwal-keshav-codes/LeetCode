class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int ans =0;
        int k =0;
        for(int x: arr1){
            k=0;
            for(int y: arr2){
                if(Math.abs(x-y) <= d){
                    k=1;break;
                }
            }
            ans = k==0 ? ans+1:ans;
        }
        return ans;
    }
}