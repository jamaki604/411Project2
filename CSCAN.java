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

		// Split requests into those to the left and right of the initial position
		List<DiskRequest> left = new ArrayList<>();
		List<DiskRequest> right = new ArrayList<>();

		for (DiskRequest request : sortedRequests) {
			if (request.getTrack() < currentPosition) {
				left.add(request);
			} else {
				right.add(request);
			}
		}

		// Process requests to the right
		for (DiskRequest request : right) {
			totalDistance += Math.abs(request.getTrack() - currentPosition);
			currentPosition = request.getTrack();
		}

		// Jump to the start of the disk (simulate circular movement)
		if (!left.isEmpty()) {
			totalDistance += Math.abs(currentPosition);
			currentPosition = 0;

			// Process requests on the left
			for (DiskRequest request : left) {
				totalDistance += Math.abs(request.getTrack() - currentPosition);
				currentPosition = request.getTrack();
			}
		}

		return totalDistance;
	}

}
