import java.util.ArrayList;
import java.util.List;

/**
 * This class will implement the C-SCAN 
 * disk scheduling algorithm.
 * 
 * @author ...
 */
public class CSCAN implements IDiskAlgorithm {

	@Override
	public int calculateDistance(List<DiskRequest> requests, int headPosition) {
		int totalDistance = 0;
		int currentPosition = headPosition;

		// Sort requests by track
		List<DiskRequest> sortedRequests = new ArrayList<>(requests);
		sortedRequests.sort((a, b) -> Integer.compare(a.getTrack(), b.getTrack()));

		List<DiskRequest> left = new ArrayList<>();
		List<DiskRequest> right = new ArrayList<>();


		for (DiskRequest request : sortedRequests) {
			if (request.getTrack() < currentPosition) {
				left.add(request);

			} else {
				right.add(request);
			}
		}


		for (DiskRequest request : right) {
			totalDistance += Math.abs(request.getTrack() - currentPosition);

			System.out.println(request.getTrack() + "-" + currentPosition + "=" + totalDistance);
			currentPosition = request.getTrack();
		}

		if (!right.isEmpty()) {
			totalDistance += Math.abs(currentPosition - 4999);
			currentPosition = 4999;
			System.out.println("Jump to 4999: " + totalDistance);
		}



		if (!left.isEmpty()) {
			totalDistance += Math.abs(currentPosition);
			currentPosition = 0;
			System.out.println("Jump to 0: " + totalDistance);

			for (DiskRequest request : left) {
				totalDistance += Math.abs(request.getTrack() - currentPosition);
				System.out.println(request.getTrack() + "-" + currentPosition + "=" + totalDistance);

				currentPosition = request.getTrack();
			}
		}

		return totalDistance;
	}

}
