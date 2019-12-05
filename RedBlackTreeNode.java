


public class RedBlackTreeNode {
	int buildingNum;
	int executedTime;
	int totalTime;
	char color = 'R';
	
	RedBlackTreeNode leftChild;
	RedBlackTreeNode rightChild;
	RedBlackTreeNode parent;
	
	RedBlackTreeNode(int bNum, int exeTime, int totTime){
		this.buildingNum = bNum;
		this.executedTime = exeTime;
		this.totalTime = totTime;
		
		
	}
	

}
