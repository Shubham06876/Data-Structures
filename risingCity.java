
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class risingCity {
	
	public static void main(String[] args) throws Exception {
		RedBlackTree rbt = new RedBlackTree();
		MinBinaryHeap minHeap = new MinBinaryHeap();
		File file1 = new File("output_file.txt");
        FileWriter fr = null;
		
		long globalCounter = 0L;
		//Read data from external file
    	File file = new File(args[0]); 
    	Scanner sc;
    	MinBinaryHeapNode m = null;
		try {
			fr = new FileWriter(file1);
			sc = new Scanner(file);
			String l;
			int flag = 0;
			int inpStartTime = -1;
			String operation = "";
			int ongoing_work = 5;
			
			//while loop for day(globalCounter) traversal
			while(true){
				
				//MinHeap is not empty, get a building to ork on
				if(ongoing_work == 5 && minHeap.index != 0) {
					ongoing_work = 0;
					m = minHeap.removeMin();
				}
				//If minHeap is empty and there's no more input in input file, break from loop
				if(m == null && !sc.hasNextLine()) {
//					System.out.println("final counter: " + globalCounter);
					fr.close();
					break;
				}
				
				//work on building
				if(m != null) {
					m.executedTime = m.executedTime + 1;
					m.correspondingRbt.executedTime += 1;
//					System.out.println(m.buildingNum + " " + m.executedTime + " " + m.totalTime);
					ongoing_work += 1;
					if(m.executedTime == m.totalTime) {
						//completed building
//						System.out.println("Before deletion");
//						rbt.printOutput(rbt.root);
//						System.out.println("BuildingNbr = " + m.correspondingRbt.buildingNum);
						deleteNode(rbt, m.correspondingRbt);
//						System.out.println("After Deletion");
//						rbt.printOutput(rbt.root);
//						System.out.println("root is" + rbt.root.buildingNum);
						String data = "(" + m.buildingNum + "," + globalCounter + ")";
//						System.out.println(data);
						fr.write(data + "\n");
						ongoing_work = 5;
						m = null;
					}
					else if(ongoing_work == 5) {
//						System.out.println(m.buildingNum + "------------------" + m.correspondingRbt.buildingNum);
						minHeap.insert(m.buildingNum,m.executedTime, m.totalTime, m.correspondingRbt);
						m = null;
					}
				}
				
				
				//New operation logic
				if(sc.hasNextLine() && flag == 0) {
					l = sc.nextLine();
					String[] arrOfStr = l.split(":", 2);
					inpStartTime = Integer.parseInt(arrOfStr[0]);
					operation = arrOfStr[1].trim();
					
				}
				if(globalCounter == inpStartTime) {
					flag = 0;
//					System.out.print(inpStartTime);
					
//					System.out.println(operation);
					
					switch (operation.charAt(0)) {
					case 'I':
						String[] arrOfArguments = operation.substring(7, operation.length()-1).split(",");
						int bNumInsert = Integer.parseInt(arrOfArguments[0].trim());
						int reqdTime = Integer.parseInt(arrOfArguments[1].trim());
						
						
						RedBlackTreeNode rbtInsertedNode = null;
						try {
							rbtInsertedNode = rbt.insert(bNumInsert, reqdTime);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(e);
							throw new Exception(e);
						}
						minHeap.insert(bNumInsert,0, reqdTime, rbtInsertedNode);
						
						break;
						
					case 'P':
						String[] arrOfArgumentsPrint = operation.substring(14, operation.length()-1).split(",");
						int bNumPrint1 = Integer.parseInt(arrOfArgumentsPrint[0].trim());
						if(arrOfArgumentsPrint.length == 2) {
							int bNumPrint2 = Integer.parseInt(arrOfArgumentsPrint[1].trim());
							rbt.print(bNumPrint1, bNumPrint2, rbt.root, fr);
						}
						else {
							rbt.print(bNumPrint1, fr);
						}
					}
					
				}
				else {
					flag = 1;
				}
				
				
				
				globalCounter += 1;
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		catch (IOException e) {
            e.printStackTrace();
        }
		finally{
            //close resources
            try {
                fr.close();
//                System.out.println("output file closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}


	public static RedBlackTreeNode searchNode(RedBlackTree rbt, int bNum) {
		RedBlackTreeNode retrievedNode = rbt.searchNode(bNum, null);
		System.out.print(retrievedNode.buildingNum);
		System.out.print(retrievedNode.color);
		if(retrievedNode.parent != null) {
		System.out.print(" p-");
		System.out.print(retrievedNode.parent.buildingNum);
		}
		if(retrievedNode.leftChild != null) {
		System.out.print(" l-");
		System.out.print(retrievedNode.leftChild.buildingNum);
		}
		if(retrievedNode.rightChild != null) {
		System.out.print(" r-");
		System.out.print(retrievedNode.rightChild.buildingNum);
		}
		return retrievedNode;
	}
	
	public static void deleteNode(RedBlackTree rbt, RedBlackTreeNode r) {
		rbt.root = rbt.delete(r, rbt.root);
		if(rbt.root != null)
			rbt.root.color = 'B';
		
	}
	
}	
