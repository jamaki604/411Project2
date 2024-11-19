import java.util.ArrayList;
import java.util.List;

/**
 * This class will implement the SCAN 
 * disk scheduling algorithm.
 * 
 * @author ...
 */
public class SCAN implements IDiskAlgorithm {

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

		// Process requests to the left, in reverse order
		if (!left.isEmpty()) {
			totalDistance += Math.abs(currentPosition - left.get(left.size() - 1).getTrack());
			currentPosition = left.get(left.size() - 1).getTrack();

			for (int i = left.size() - 1; i >= 0; i--) {
				DiskRequest request = left.get(i);
				totalDistance += Math.abs(request.getTrack() - currentPosition);
				currentPosition = request.getTrack();
			}
		}

		return totalDistance;
	}


}
