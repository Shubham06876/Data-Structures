
import java.io.FileWriter;
import java.io.IOException;

public class RedBlackTree {
	RedBlackTreeNode root;
	
	
	//this method is for inserting node in RedBlackTree
	public RedBlackTreeNode insert(int bNumInsert, int reqdTime) throws Exception {
		RedBlackTreeNode node = new RedBlackTreeNode(bNumInsert, 0, reqdTime);
		RedBlackTreeNode parent = null;
		RedBlackTreeNode node_pointer = root;
		RedBlackTreeNode grandParent = null;
		while (node_pointer != null) {
			if(node_pointer.buildingNum == bNumInsert) {
				throw new Exception("Building number already exists");
			}
			else if(node_pointer.buildingNum < bNumInsert) {
				parent = node_pointer;
				node_pointer = node_pointer.rightChild;
			}
			else {
				parent = node_pointer;
				node_pointer = node_pointer.leftChild;		
			}	
		}	
		if(parent == null) {
			root = node;
		}
		else if(parent.buildingNum > bNumInsert) {
			parent.leftChild = node;
			node.parent = parent;
			grandParent = parent.parent;
		}
		else {
			parent.rightChild = node;
			node.parent = parent;
			grandParent = parent.parent;
		}
		
		
		//Re-adjust RBT tree
		root = rebalanceTree(parent, node, grandParent,root);
		root.color = 'B';
		return node;
	}
	
	//This method is for rebalancing tree after insertion so that no property of rbt is violated
	public static RedBlackTreeNode rebalanceTree(RedBlackTreeNode parent,RedBlackTreeNode node,RedBlackTreeNode grandParent, RedBlackTreeNode root) {
		if(node.parent == null || node.parent.parent == null) {
			return root;
		}
		if(parent.color == node.color && node.color=='R') {
			
			//case1: LLr and LRr
			if(((grandParent.leftChild == parent && parent.rightChild == node) || (grandParent.leftChild == parent && parent.leftChild == node)) && grandParent.rightChild != null && grandParent.rightChild.color == 'R'){
//				System.out.println("LYr");
				parent.color = 'B';
				grandParent.color = 'R';
				grandParent.rightChild.color = 'B';
				
				node = grandParent;
				if(node.parent == null || node.parent.parent == null) {
					return root;
				}
				
				parent = node.parent;
				grandParent = parent.parent;
				root = rebalanceTree(parent, node, grandParent,root);
				return root;
				
			}
			//case1-1: RLr and RRr
			if(((grandParent.rightChild == parent && parent.leftChild == node) || (grandParent.rightChild == parent && parent.rightChild == node)) && grandParent.leftChild != null && grandParent.leftChild.color == 'R') {
//				System.out.println("RYr");
				parent.color = 'B';
				grandParent.color = 'R';
				grandParent.leftChild.color = 'B';
				
				node = grandParent;
				if(node.parent == null || node.parent.parent == null) {
					return root;
				}
				
				parent = node.parent;
				grandParent = parent.parent;
				root = rebalanceTree(parent, node, grandParent,root);
				return root;
			}
			
			
			
			
			//case2: LLb
			else if(grandParent.leftChild == parent && parent.leftChild == node) {
//				System.out.println("LLb");
				RedBlackTreeNode tempParentOfGrandparent = grandParent.parent;
				parent.color = 'B';
				grandParent.color = 'R';
				parent.parent = grandParent.parent;
				grandParent.parent = parent;
				if(parent.rightChild != null) {
					parent.rightChild.parent = grandParent;
					grandParent.leftChild = parent.rightChild;
				}
				else {
					grandParent.leftChild = null;
				}
				parent.rightChild = grandParent;
				if(tempParentOfGrandparent != null) {
					if(tempParentOfGrandparent.leftChild == grandParent) {
						tempParentOfGrandparent.leftChild = parent;
					}
					else {
						tempParentOfGrandparent.rightChild = parent;
					}
				}
				
				
				if(parent.parent == null) {
					return parent;
				}
				else {
					return root;
				}
				
			}
			//case3: RRb
			else if(grandParent.rightChild == parent && parent.rightChild == node) {
//				System.out.println("RRb");
				RedBlackTreeNode tempParentOfGrandparent = grandParent.parent;
				parent.color = 'B';
				grandParent.color = 'R';
				parent.parent = grandParent.parent;
				grandParent.parent = parent;
				
				if(parent.leftChild != null) {
					parent.leftChild.parent = grandParent;
					grandParent.rightChild = parent.leftChild;
				}
				else {
					grandParent.rightChild = null;
				}
				parent.leftChild = grandParent;	
				if(tempParentOfGrandparent != null) {
					if(tempParentOfGrandparent.leftChild == grandParent) {
						tempParentOfGrandparent.leftChild = parent;
					}
					else {
						tempParentOfGrandparent.rightChild = parent;
					}
				}
				if(parent.parent == null) {
					return parent;
				}
				else {
					return root;
				}
			}
			
			//case4: LRb
			else if(grandParent.leftChild == parent && parent.rightChild == node){
//				System.out.println("LRb");
				RedBlackTreeNode tempParentOfGrandparent = grandParent.parent;
				node.color = 'B';
				grandParent.color = 'R'; 
				node.parent = grandParent.parent;
				if(node.leftChild != null) {
					node.leftChild.parent = parent;
					parent.rightChild = node.leftChild;
				}
				else {
					parent.rightChild = null;
				}
				if(node.rightChild != null) {
					node.rightChild.parent = grandParent;
					grandParent.leftChild = node.rightChild;
				}
				else {
					grandParent.leftChild = null;
				}
				parent.parent = node;
				grandParent.parent = node;
				node.leftChild = parent;
				node.rightChild = grandParent;
				if(tempParentOfGrandparent != null) {
					if(tempParentOfGrandparent.leftChild == grandParent) {
						tempParentOfGrandparent.leftChild = node;
					}
					else {
						tempParentOfGrandparent.rightChild = node;
					}
				}
				
				if(node.parent == null) {
					return node;
				}
				else {
					return root;
				}
				
				
			}
			
			//case5: RLb
			else if(grandParent.rightChild == parent && parent.leftChild == node){
//				System.out.println("RLb");
				RedBlackTreeNode tempParentOfGrandparent = grandParent.parent;
				node.color = 'B';
				grandParent.color = 'R'; 
				node.parent = grandParent.parent;
				if(node.leftChild != null) {
					node.leftChild.parent = grandParent;
					grandParent.rightChild = node.leftChild;
				}
				else {
					grandParent.rightChild = null;
				}
				if(node.rightChild != null) {
					node.rightChild.parent = parent;
					parent.leftChild = node.rightChild;
				}
				else {
					parent.leftChild = null;
				}
				parent.parent = node;
				grandParent.parent = node;
				node.leftChild = grandParent;
				node.rightChild = parent;
				if(tempParentOfGrandparent != null) {
					if(tempParentOfGrandparent.leftChild == grandParent) {
						tempParentOfGrandparent.leftChild = node;
					}
					else {
						tempParentOfGrandparent.rightChild = node;
					}
				}
				
				
				if(node.parent == null) {
					return node;
				}
				else {
					return root;
				}
				
			}
			
		}
		return root;	
	}
	
	////this method is for in-order printing of RedBlackTree
	public void printOutput(RedBlackTreeNode node) {
		if(node.leftChild!=null) {
			printOutput(node.leftChild);
			
		}
		System.out.print(node.buildingNum);
		System.out.println(node.color);
		
		if(node.rightChild != null) {
			printOutput(node.rightChild);
		}
		
	}
	
	////this method is for deleting node in RedBlackTree
	public RedBlackTreeNode delete(RedBlackTreeNode node, RedBlackTreeNode root) {
		if(root.leftChild == null && root.rightChild == null && root == node) {
			//only element in rbt is root which needs to be deleted
			return null;
		}
		String degree;
		if(node.leftChild == null && node.rightChild == null) {
			degree = "leaf";
		}
		else if((node.leftChild == null && node.rightChild != null) || (node.leftChild != null && node.rightChild == null)) {
			degree = "degree1node";
		}
		else {
			degree = "degree2node";
			
		}
		//leaf node with color = red
		if(node.color == 'R' && degree == "leaf") {
			if(node.parent.leftChild == node) {
				node.parent.leftChild = null;
			}
			else {
				node.parent.rightChild = null;
			}
			node.parent = null;
			//No further rebalancing needed as deleted node is red node
			return root;
		}
		//leaf node with color = black
		else if(node.color == 'B' && degree == "leaf") {
			RedBlackTreeNode p = null;
			RedBlackTreeNode parent = node.parent;
			if(parent.leftChild == node) {
				parent.leftChild = null;
			}
			else {
				parent.rightChild = null;
			}
			root = rebalanceTreeOnDeletion(p, parent, root);
			return root;
		}
		
		//degree1node and color red
		else if(node.color == 'R' && degree == "degree1node") {
			RedBlackTreeNode p = null;
			RedBlackTreeNode parent;
			if(node.leftChild != null) {
				p = node.leftChild;
				node.leftChild = null;
			}
			else {
				p = node.rightChild;
				node.rightChild = null;				
			}
			//node to be deleted cannot be root node as color of  root node cannot be red
			
			parent = node.parent;
			p.parent = parent;
			if(parent.leftChild == node) {
				parent.leftChild = p;
			}
			else {
				parent.rightChild = p;
			}
			node.parent = null;
			
			return root;
		}
		
		//degree1node and color black
		else if(node.color == 'B' && degree == "degree1node") {
			RedBlackTreeNode p = null;
			RedBlackTreeNode parent;
			if(node.leftChild != null) {
				p = node.leftChild;
				node.leftChild = null;
			}
			else {
				p = node.rightChild;
				node.rightChild = null;				
			}
			//node to be deleted can be root node and needs to be considered
			if(node.parent == null) {
				root = p;
				p.parent = null;
				return root;
			}
			
			parent = node.parent;
			p.parent = parent;
			if(parent.leftChild == node) {
				parent.leftChild = p;
			}
			else {
				parent.rightChild = p;
			}
			node.parent = null;
			
			root = rebalanceTreeOnDeletion(p, parent, root);
			root.color = 'B';
			return root;			
		}
		
		//degree2node and color red or black
		else if( degree == "degree2node") {
			RedBlackTreeNode replNode = node.leftChild;
			while(replNode.rightChild != null) {
				replNode = replNode.rightChild;
			}
//			System.out.println("testing" + replNode.buildingNum);
			
			RedBlackTreeNode tempNodeLC = null;
			RedBlackTreeNode tempNodeRC = null;
			RedBlackTreeNode node_parent = null;
			RedBlackTreeNode replNode_parent = null;
			
			if(replNode.parent != node) {
				node_parent = node.parent;
				replNode_parent = replNode.parent;
				
				if(node_parent != null) {
					if(node_parent.leftChild == node) {
						node_parent.leftChild = replNode;
					}
					else {
						node_parent.rightChild = replNode;
						}
				}
				if(replNode_parent != null) {
					if(replNode_parent.leftChild == replNode) {
						replNode_parent.leftChild = node;
					}
					else {
						replNode_parent.rightChild = node;
						}
				}
				node.parent = replNode_parent;
				replNode.parent = node_parent;
				tempNodeLC = replNode.leftChild;
				tempNodeRC = replNode.rightChild;
				
				if(replNode.leftChild != null)
					replNode.leftChild.parent = node;
				if(replNode.rightChild != null)
					replNode.rightChild.parent = node;
				if(node.leftChild != null)
					node.leftChild.parent = replNode;
				if(node.rightChild != null)
					node.rightChild.parent = replNode;
				char tempColor = node.color;
				node.color = replNode.color;
				replNode.color = tempColor;
				replNode.leftChild = node.leftChild;
				replNode.rightChild = node.rightChild;
				node.leftChild = tempNodeLC;
				node.rightChild = tempNodeRC;
				
						
				
//				System.out.println("repl node pointe" + replNode.buildingNum);
//				System.out.println("node pointe" + node.buildingNum);
				
				
			}
			else {
				node_parent = node.parent;
				tempNodeLC = replNode.leftChild;
				tempNodeRC = replNode.rightChild;
				
				if(node_parent != null) {
					if(node_parent.leftChild == node) {
						node_parent.leftChild = replNode;
					}
					else {
						node_parent.rightChild = replNode;
						}
				}
				replNode.parent = node_parent;
				tempNodeLC = replNode.leftChild;
				tempNodeRC = replNode.rightChild;
				
				node.parent = replNode;
				replNode.leftChild = node;
				replNode.rightChild = node.rightChild;
				if(replNode.rightChild != null)
					replNode.rightChild.parent = replNode;
				
				node.leftChild = tempNodeLC;
				node.rightChild = tempNodeRC;
				
				if(node.leftChild != null)
					node.leftChild.parent = node;
				if(node.rightChild != null)
					node.rightChild.parent = node;
				
				char tempColor;
				tempColor = replNode.color;
				replNode.color = node.color;
				node.color = tempColor;
				
			}
			//change root if necessary
			if(node_parent == null)
				root = replNode;
			
			
			
			
			
//			int tempBuildingNum = replNode.buildingNum;
//			int tempExecutedTime = replNode.executedTime;
//			int tempTotalTime = replNode.totalTime;
//			
//			char tempColor = replNode.color;
//			
//			replNode.buildingNum = node.buildingNum;
//			replNode.executedTime = node.executedTime;
//			replNode.totalTime = node.totalTime;
//			replNode.color = node.color;
//			
//			node.buildingNum = tempBuildingNum;
//			node.executedTime = tempExecutedTime;
//			node.totalTime = tempTotalTime;
//			node.color = tempColor;
			root = delete(node, root);
			return root;
		}
		
		return root;
		
	}
	////this method is for rebalancing tree after node deletion in RedBlackTree
	public static RedBlackTreeNode rebalanceTreeOnDeletion(RedBlackTreeNode p, RedBlackTreeNode parent, RedBlackTreeNode root) {
			//Scenario1: p is red
			if(parent == null) {
				return root;
			}
			else if(p != null && p.color == 'R') {
//				System.out.println("Scenario1");
				p.color = 'B';		
				return root;
			}
			
			//Scenario2: Rb0, parent is black
			else if(parent.rightChild == p && parent.color == 'B' && parent.leftChild.color == 'B' && (parent.leftChild.leftChild == null || parent.leftChild.leftChild.color == 'B') && (parent.leftChild.rightChild == null || parent.leftChild.rightChild.color == 'B')) {
//				System.out.println("Rb0, parent is black");
				parent.leftChild.color = 'R';
				//Adjustments need to be carried on till top node
				p = parent;
				parent = parent.parent;
				root = rebalanceTreeOnDeletion(p, parent, root);
				return root;
			}
		
			//Scenario2.1 Rb0, parent is red
			else if(parent.rightChild == p && parent.color == 'R' && parent.leftChild.color == 'B' && (parent.leftChild.leftChild == null || parent.leftChild.leftChild.color == 'B') && (parent.leftChild.rightChild == null || parent.leftChild.rightChild.color == 'B')) {
//				System.out.println("Rb0, parent is red");
				parent.leftChild.color = 'R';
				parent.color = 'B';
				return root;
			}
			
//			Scenario2.2: Lb0, parent is black
			else if(parent.leftChild == p && parent.color == 'B' && parent.rightChild.color == 'B' && (parent.rightChild.leftChild == null || parent.rightChild.leftChild.color == 'B') && (parent.rightChild.rightChild == null || parent.rightChild.rightChild.color == 'B')) {
//				System.out.println("Lb0");
				parent.rightChild.color = 'R';
				//Adjustments need to be carried on till top node
				p = parent;
				parent = parent.parent;
				root = rebalanceTreeOnDeletion(p, parent, root);
				return root;
			}
		
			//Scenario2.3 Lb0, parent is red
			else if(parent.leftChild == p && parent.color == 'R' && parent.rightChild.color == 'B' && (parent.rightChild.leftChild == null || parent.rightChild.leftChild.color == 'B') && (parent.rightChild.rightChild == null || parent.rightChild.rightChild.color == 'B')) {
				parent.rightChild.color = 'R';
				parent.color = 'B';
				return root;
			}
			
			//Scenario3.1 Rb1, left child of left child of parent is red
			else if(parent.rightChild == p && parent.leftChild.color == 'B' && (parent.leftChild.leftChild != null && parent.leftChild.leftChild.color == 'R') && (parent.leftChild.rightChild == null || parent.leftChild.rightChild.color == 'B')) {
				
				RedBlackTreeNode tempGrandParent = parent.parent;
				
				RedBlackTreeNode v = parent.leftChild;
				v.parent = parent.parent;
				v.color = parent.color;
				parent.color = 'B';
				v.leftChild.color = 'B';
				
				if(v.rightChild != null) {
					v.rightChild.parent = parent;
					parent.leftChild = v.rightChild;
				}
				else {
					parent.leftChild = null;
				}
				v.rightChild = parent;
				parent.parent = v;
				
				//parent may be root
				if(tempGrandParent == null) {
					root = v;
				}
				else {
					if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
						tempGrandParent.leftChild = v;
					}
					else {
						tempGrandParent.rightChild = v;
					}
				}
			return root;
			}
			
			//Scenario3.2 Rb1, right child of left child of parent is red
			else if(parent.rightChild == p && parent.leftChild.color == 'B' && (parent.leftChild.rightChild != null && parent.leftChild.rightChild.color == 'R') && (parent.leftChild.leftChild == null || parent.leftChild.leftChild.color == 'B')) {
				parent.leftChild.rightChild.color = parent.color;
				RedBlackTreeNode tempGrandParent = parent.parent;
				RedBlackTreeNode w = parent.leftChild.rightChild;
				RedBlackTreeNode v = parent.leftChild;
				v.parent = w;
				parent.color = 'B';
				
				if(w.rightChild != null) {
					w.rightChild.parent = parent;
					parent.leftChild = w.rightChild;
				}
				else {
					parent.leftChild = null;
				}
				if(w.leftChild != null) {
					w.leftChild.parent = v;
					v.rightChild = w.leftChild;
				}
				else {
					v.rightChild = null;
				}
				w.leftChild = v;
				w.rightChild = parent;
				w.parent = parent.parent;
			
				parent.parent = w;
				
				
				//parent may be root
				if(tempGrandParent == null) {
					root = w;
				}
				else {
					if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
						tempGrandParent.leftChild = w;
					}
					else {
						tempGrandParent.rightChild = w;
					}
				}
				return root;
			}
			
			
			//Scenario3.3 Lb1, right child of right child of parent is red
			else if(parent.leftChild == p && parent.rightChild.color == 'B' && (parent.rightChild.rightChild != null && parent.rightChild.rightChild.color == 'R') && (parent.rightChild.leftChild == null || parent.rightChild.leftChild.color == 'B')) {
//				System.out.println("Lb1, right child of right child of parent is red");
				RedBlackTreeNode tempGrandParent = parent.parent;
				
				RedBlackTreeNode v = parent.rightChild;
				v.parent = parent.parent;
				v.color = parent.color;
				parent.color = 'B';
				v.rightChild.color = 'B';
				
				if(v.leftChild != null) {
					v.leftChild.parent = parent;
					parent.rightChild = v.leftChild;
				}
				else {
					parent.rightChild = null;
				}
				v.leftChild = parent;
				parent.parent = v;
				
				//parent may be root
				if(tempGrandParent == null) {
					root = v;
				}
				else {
					if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
						tempGrandParent.leftChild = v;
					}
					else {
						tempGrandParent.rightChild = v;
					}
				}
				return root;
			}
			
			//Scenario3.4 Lb1, left child of right child of parent is red
			else if(parent.leftChild == p && parent.rightChild.color == 'B' && (parent.rightChild.leftChild != null && parent.rightChild.leftChild.color == 'R') && (parent.rightChild.rightChild == null || parent.rightChild.rightChild.color == 'B')) {
				parent.rightChild.leftChild.color = parent.color;
				RedBlackTreeNode tempGrandParent = parent.parent;
				RedBlackTreeNode w = parent.rightChild.leftChild;
				RedBlackTreeNode v = parent.rightChild;
				v.parent = w;
				parent.color = 'B';
				
				if(w.leftChild != null) {
					w.leftChild.parent = parent;
					parent.rightChild = w.leftChild;
				}
				else {
					parent.rightChild = null;
				}
				if(w.rightChild != null) {
					w.rightChild.parent = v;
					v.leftChild = w.rightChild;
				}
				else {
					v.leftChild = null;
				}
				w.leftChild = parent;
				w.rightChild = v;
				w.parent = parent.parent;
			
				parent.parent = w;
				
				
				//parent may be root
				if(tempGrandParent == null) {
					root = w;
				}
				else {
					if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
						tempGrandParent.leftChild = w;
					}
					else {
						tempGrandParent.rightChild = w;
					}
				}
				return root;
			}
			
			//Scenario4: Rb2
			else if(parent.rightChild == p && parent.leftChild.color == 'B' && (parent.leftChild.leftChild != null && parent.leftChild.leftChild.color == 'R') && (parent.leftChild.rightChild != null && parent.leftChild.rightChild.color == 'R')) {
				
				RedBlackTreeNode tempGrandParent = parent.parent;
				RedBlackTreeNode w = parent.leftChild.rightChild;
				RedBlackTreeNode v = parent.leftChild;
				v.parent = w;
				w.color = parent.color;
				parent.color = 'B';
				
				if(w.rightChild != null) {
					w.rightChild.parent = parent;
					parent.leftChild = w.rightChild;
				}
				else {
					parent.leftChild = null;
				}
				if(w.leftChild != null) {
					w.leftChild.parent = v;
					v.rightChild = w.leftChild;
				}
				else {
					v.rightChild = null;
				}
				w.leftChild = v;
				w.rightChild = parent;
				w.parent = parent.parent;
			
				parent.parent = w;
				
				
				//parent may be root
				if(tempGrandParent == null) {
					root = w;
				}
				else {
					if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
						tempGrandParent.leftChild = w;
					}
					else {
						tempGrandParent.rightChild = w;
					}
				}
				return root;
			}
			
			//Scenario4.1 Lb2
			else if(parent.leftChild == p && parent.rightChild.color == 'B' && (parent.rightChild.leftChild != null && parent.rightChild.leftChild.color == 'R') && (parent.rightChild.rightChild == null || parent.rightChild.rightChild.color == 'B')) {
				
				RedBlackTreeNode tempGrandParent = parent.parent;
				RedBlackTreeNode w = parent.rightChild.leftChild;
				RedBlackTreeNode v = parent.rightChild;
				v.parent = w;
				w.color = parent.color;
				parent.color = 'B';
				
				if(w.leftChild != null) {
					w.leftChild.parent = parent;
					parent.rightChild = w.leftChild;
				}
				else {
					parent.rightChild = null;
				}
				if(w.rightChild != null) {
					w.rightChild.parent = v;
					v.leftChild = w.rightChild;
				}
				else {
					v.leftChild = null;
				}
				w.leftChild = parent;
				w.rightChild = v;
				w.parent = parent.parent;
			
				parent.parent = w;
				
				
				//parent may be root
				if(tempGrandParent == null) {
					root = w;
				}
				else {
					if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
						tempGrandParent.leftChild = w;
					}
					else {
						tempGrandParent.rightChild = w;
					}
				}
				return root;
			}
			
			
			//Rro, Rr1, Rr2
			else if(parent.rightChild == p && parent.color == 'B' && parent.leftChild.color == 'R') {
				RedBlackTreeNode v = parent.leftChild;
				//Rr0
				if(v.rightChild == null || ((v.rightChild.leftChild == null || v.rightChild.leftChild.color == 'B') && (v.rightChild.rightChild == null || v.rightChild.rightChild.color == 'B'))) {
					RedBlackTreeNode tempGrandParent = parent.parent;
					v.parent = parent.parent;
					v.color = 'B';
					
					if(v.rightChild != null) {
						v.rightChild.color = 'R';
						v.rightChild.parent = parent;
						parent.leftChild = v.rightChild;
					}
					else {
						parent.leftChild = null;
					}
					v.rightChild = parent;
					parent.parent = v;
					
					//parent may be root
					if(tempGrandParent == null) {
						root = v;
					}
					else {
						if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
							tempGrandParent.leftChild = v;
						}
						else {
							tempGrandParent.rightChild = v;
						}
					}
				return root;				
			}
				//Rr1.1
				else if((v.rightChild.leftChild != null && v.rightChild.leftChild.color == 'R') && (v.rightChild.rightChild == null || v.rightChild.rightChild.color == 'B')) {
					RedBlackTreeNode tempGrandParent = parent.parent;
					RedBlackTreeNode w = v.rightChild;
					v.parent = w;
					w.leftChild.color = 'B';
					
					if(w.rightChild != null) {
						w.rightChild.parent = parent;
						parent.leftChild = w.rightChild;
					}
					else {
						parent.leftChild = null;
					}
					
					w.leftChild.parent = v;
					v.rightChild = w.leftChild;
										
					w.leftChild = v;
					w.rightChild = parent;
					w.parent = parent.parent;
				
					parent.parent = w;
					
					
					//parent may be root
					if(tempGrandParent == null) {
						root = w;
					}
					else {
						if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
							tempGrandParent.leftChild = w;
						}
						else {
							tempGrandParent.rightChild = w;
						}
					}
					return root;
					
				}
				//Rr1.2
				else if(v.rightChild.rightChild != null && v.rightChild.rightChild.color == 'R') {
					RedBlackTreeNode tempGrandParent = parent.parent;
					RedBlackTreeNode w = v.rightChild;
					RedBlackTreeNode x = w.rightChild;
					v.parent = x;
					x.color = 'B';
					
					if(x.rightChild != null) {
						x.rightChild.parent = parent;
						parent.leftChild = x.rightChild;
					}
					else {
						parent.leftChild = null;
					}
					if(x.leftChild != null) {
						x.leftChild.parent = w;
						w.rightChild = x.leftChild;
					}
					else {
						w.rightChild = null;
					}
										
					x.leftChild = v;
					x.rightChild = parent;
					x.parent = parent.parent;
				
					parent.parent = x;
					
					
					//parent may be root
					if(tempGrandParent == null) {
						root = x;
					}
					else {
						if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
							tempGrandParent.leftChild = x;
						}
						else {
							tempGrandParent.rightChild = x;
						}
					}
					return root;
					
				}
			}
			
			//Lr0, Lr1, Lr2
			else if(parent.leftChild == p && parent.color == 'B' && parent.rightChild.color == 'R') {
				RedBlackTreeNode v = parent.rightChild;
				//Lr0
				if(v.leftChild == null || ((v.leftChild.leftChild == null || v.leftChild.leftChild.color == 'B') && (v.leftChild.rightChild == null || v.leftChild.rightChild.color == 'B'))) {
					RedBlackTreeNode tempGrandParent = parent.parent;
					v.parent = parent.parent;
					v.color = 'B';
					
					if(v.leftChild != null) {
						v.leftChild.color = 'R';
						v.leftChild.parent = parent;
						parent.rightChild = v.leftChild;
					}
					else {
						parent.rightChild = null;
					}
					v.leftChild = parent;
					parent.parent = v;
					
					//parent may be root
					if(tempGrandParent == null) {
						root = v;
					}
					else {
						if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
							tempGrandParent.leftChild = v;
						}
						else {
							tempGrandParent.rightChild = v;
						}
					}
				return root;				
			}
				//Lr1.1
				
				else if((v.leftChild.rightChild != null && v.leftChild.rightChild.color == 'R') && (v.leftChild.leftChild == null || v.leftChild.leftChild.color == 'B')) {
					RedBlackTreeNode tempGrandParent = parent.parent;
					RedBlackTreeNode w = v.leftChild;
					v.parent = w;
					w.rightChild.color = 'B';
					
					if(w.leftChild != null) {
						w.leftChild.parent = parent;
						parent.rightChild = w.leftChild;
					}
					else {
						parent.rightChild = null;
					}
					
					w.rightChild.parent = v;
					v.leftChild = w.rightChild;
										
					w.rightChild = v;
					w.leftChild = parent;
					w.parent = parent.parent;
				
					parent.parent = w;
					
					
					//parent may be root
					if(tempGrandParent == null) {
						root = w;
					}
					else {
						if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
							tempGrandParent.leftChild = w;
						}
						else {
							tempGrandParent.rightChild = w;
						}
					}
					return root;
					
				}
				//Lr1.2
				
				else if(v.leftChild.leftChild != null && v.leftChild.leftChild.color == 'R'){
					RedBlackTreeNode tempGrandParent = parent.parent;
					RedBlackTreeNode w = v.leftChild;
					RedBlackTreeNode x = w.leftChild;
					v.parent = x;
					x.color = 'B';
					
					if(x.leftChild != null) {
						x.leftChild.parent = parent;
						parent.rightChild = x.leftChild;
					}
					else {
						parent.rightChild = null;
					}
					if(x.rightChild != null) {
						x.rightChild.parent = w;
						w.leftChild = x.rightChild;
					}
					else {
						w.leftChild = null;
					}
										
					x.rightChild = v;
					x.leftChild = parent;
					x.parent = parent.parent;
				
					parent.parent = x;
					
					
					//parent may be root
					if(tempGrandParent == null) {
						root = x;
					}
					else {
						if(tempGrandParent.leftChild.buildingNum == parent.buildingNum) {
							tempGrandParent.leftChild = x;
						}
						else {
							tempGrandParent.rightChild = x;
						}
					}
					return root;
					
				}
			}
		return root;
	}
	
	//Search node in tree.. returns null if not found
	public RedBlackTreeNode searchNode(int bNum, RedBlackTreeNode node) {
		if(node == null) {
			node = root;
		}
		if(node.buildingNum == bNum) {
			return node;
		}
		else if(node.buildingNum < bNum) {
			node = node.rightChild;			
		}
		else {
			node = node.leftChild;
		}
		if(node == null) {
			return null;
		}
		return searchNode(bNum, node);
		
	}
	
	//prints triplets building number, executed time, total time for all building numbers in given range
	public void print(int bNum1, int bNum2, RedBlackTreeNode r, FileWriter fr) {
		try {
		String s = "";
		if(r == null) {
			fr.write("(0,0,0)" + "\n");
			return;
		}
		if(r.buildingNum >= bNum1 && r.buildingNum <= bNum2) {
			s = find_building_number(r, bNum1, bNum2, s);
			if(s == "")	
				fr.write("(0,0,0)" + "\n");
			else
				fr.write(s.substring(0, s.length()-1) + "\n");
			}
		else if(r.buildingNum < bNum1) {
			r = r.rightChild;
			print(bNum1, bNum2, r, fr);
		}
		else if(r.buildingNum > bNum2) {
			r = r.leftChild;
			print(bNum1, bNum2, r, fr);
		}
		
		
		return;
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}
	
	
	public static String find_building_number(RedBlackTreeNode node, int bNum1, int bNum2, String s) {
		if(node.buildingNum >= bNum1 && node.buildingNum <= bNum2 || node.parent != null && node.parent.buildingNum < bNum2) {
			
			if(node.leftChild != null) {
				s += find_building_number(node.leftChild, bNum1, bNum2, "");
			}
			if(node.buildingNum >= bNum1 && node.buildingNum <= bNum2) {
				s += "(" + node.buildingNum + "," + node.executedTime + "," + node.totalTime + ")"+",";
			}
			
			if(node.rightChild != null) {
				s += find_building_number(node.rightChild, bNum1, bNum2, "");
			}
		}
		else
			s = "";
		
//		System.out.println("s is " + s);
		return s;
	}
	
	//print triplet building number, executed time, total time for given building number
	public void print(int bNum1, FileWriter fr) {
		try
		{
		RedBlackTreeNode r = searchNode(bNum1, null);
		if(r == null)
			fr.write("(0,0,0)" + "\n");
		else {
			fr.write("(" + r.buildingNum + "," + r.executedTime + "," + r.totalTime + ")");
			}
		return;
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
