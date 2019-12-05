
public class MinBinaryHeapNode {
	int buildingNum;
	int executedTime;
	int totalTime;
	RedBlackTreeNode correspondingRbt;
	public MinBinaryHeapNode(int bNum, int exeTime, int time, RedBlackTreeNode r) {
		this.buildingNum = bNum;
		this.executedTime = exeTime;
		this.totalTime = time;
		this.correspondingRbt = r;
//		System.out.println(r.buildingNum);
		// TODO Auto-generated constructor stub
	}

}
