import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<DiskRequest> requests1 = new ArrayList<DiskRequest>();
		requests1.add(new DiskRequest(1000, 0));
		requests1.add(new DiskRequest(2000, 0));
		requests1.add(new DiskRequest(3000, 0));
		requests1.add(new DiskRequest(4000, 0));
		
		IDiskAlgorithm diskAlgorithm;
		diskAlgorithm = new CSCAN();
		
		System.out.println(diskAlgorithm.calculateDistance(requests1, 1000));
	}
	
}
