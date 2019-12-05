public class MinBinaryHeap {
	MinBinaryHeapNode[] minHeapArray = new MinBinaryHeapNode[2000];
	int index = 0;
	
	//this method is used to insert element in minHeap array
	public void insert(int bNumInsert, int executedTime, int reqdTime, RedBlackTreeNode correspondingRbt) {
		MinBinaryHeapNode node = new MinBinaryHeapNode(bNumInsert, executedTime, reqdTime, correspondingRbt);
		minHeapArray[index] = node;
		
		//Rearrange min heap after insertion
		minHeapArray = MinBinaryHeap.rearrangeMinHeapAfterInsert(index, minHeapArray);
		index += 1;
		return;
	}
	//method to rearrange minHeap array after insertion
	public static MinBinaryHeapNode[] rearrangeMinHeapAfterInsert(int index, MinBinaryHeapNode[] arr) {
		if(index == 0) {
			return arr;
		}
		
		int parentIndex;
		if(index%2 == 0)
			parentIndex = index/2 - 1;
		else
			parentIndex = (index-1)/2;
		
		if(arr[parentIndex].executedTime > arr[index].executedTime || (arr[parentIndex].executedTime == arr[index].executedTime && arr[parentIndex].buildingNum > arr[index].buildingNum)){
			MinBinaryHeapNode tempNode = arr[parentIndex];
			arr[parentIndex] = arr[index];
			arr[index] = tempNode;
			return rearrangeMinHeapAfterInsert(parentIndex, arr);
		}
		return arr;
	}
	
	//returns node with minimum execution time
	public MinBinaryHeapNode removeMin() {
		MinBinaryHeapNode retNode = minHeapArray[0];
		minHeapArray[0] = minHeapArray[index-1];
		minHeapArray[index-1] = null;
		index -= 1;
		minHeapArray = rearrangeMinHeapAfterDelete(minHeapArray, index-1);
		return retNode; 
	}
	
	public static MinBinaryHeapNode[] rearrangeMinHeapAfterDelete(MinBinaryHeapNode[] arr, int index) {
		int i = 0;
		int upperLimit;
		if(index%2 == 0)
			upperLimit = index/2;
		else
			upperLimit = (index - 1)/2;
		
		while(i <= upperLimit) {
			int leftIndex = 2*i + 1;
			int rightIndex = 2*i + 2;
			int comparisonIndex;
			if(leftIndex > index && rightIndex > index)
				break;
			else if(rightIndex > index) {
				comparisonIndex = leftIndex;
			}
			else {
				if(arr[rightIndex].executedTime > arr[leftIndex].executedTime)
					comparisonIndex = leftIndex;
				else if(arr[rightIndex].executedTime == arr[leftIndex].executedTime) {
					if(arr[rightIndex].buildingNum > arr[leftIndex].buildingNum)
						comparisonIndex = leftIndex;
					else
						comparisonIndex = rightIndex;
				}
				else
					comparisonIndex = rightIndex;	
			}
//			System.out.println(comparisonIndex);
			
			if(arr[i].executedTime > arr[comparisonIndex].executedTime || (arr[i].executedTime == arr[comparisonIndex].executedTime && arr[i].buildingNum > arr[comparisonIndex].buildingNum)) {
				MinBinaryHeapNode tempNode = arr[i];
				arr[i] = arr[comparisonIndex];
				arr[comparisonIndex] = tempNode;
				i = comparisonIndex;
			}
			else
				break;
		}
		return arr;
	}
	
	public void printArray() {
		for(int i=0;i<2000;i++) {
			if(minHeapArray[i] == null) {
				break;
			}
			System.out.println(minHeapArray[i].buildingNum + " " + minHeapArray[i].executedTime);
		}
	}
	
	
	

}
