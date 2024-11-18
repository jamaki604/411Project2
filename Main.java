import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<DiskRequest> requests1 = new ArrayList<DiskRequest>();
		requests1.add(new DiskRequest(3, 0));
		requests1.add(new DiskRequest(6, 0));
		requests1.add(new DiskRequest(1, 0));
		requests1.add(new DiskRequest(0, 0));
		requests1.add(new DiskRequest(7, 0));
		
		IDiskAlgorithm diskAlgorithm;
		diskAlgorithm = new FCFS();
		
		System.out.println(diskAlgorithm.calculateDistance(requests1, 0));
	}
	
}
