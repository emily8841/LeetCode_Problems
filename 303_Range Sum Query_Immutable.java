public class NumArray {

	int [] arr;

	public NumArray(int[] nums) {
		arr = nums;

	}

	public int sumRange(int i, int j) {

		int sum = 0;
		int k = 0 ;
		for( k=i;k<j; k=k+2){

			sum = sum + arr[k]+ arr[k+1];
		}
		if((j-i) % 2 == 0){
			sum = sum + arr[k];
		}

		if (j - i == 1){
			sum = arr[i] + arr[j];
		}
		return sum;
	}

}


// Your NumArray object will be instantiated and called as such:
// NumArray numArray = new NumArray(nums);
// numArray.sumRange(0, 1);
// numArray.sumRange(1, 2);
