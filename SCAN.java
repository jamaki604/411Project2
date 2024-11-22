import java.util.ArrayList;
import java.util.Comparator;
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

		//Sort by timeOfArrival
		List<DiskRequest> sortedRequests = new ArrayList<>(requests);
		sortedRequests.sort(Comparator.comparingInt(DiskRequest::getTimeOfArrival)
				.thenComparingInt(DiskRequest::getTrack));

		// Debug: Print sorted requests
		//System.out.println("Sorted Requests: " + sortedRequests.stream().map(r -> "(" + r.getTrack() + ", " + r.getTimeOfArrival() + ")").toList());

		List<DiskRequest> remainingRequests = new ArrayList<>(sortedRequests);
		int currentTime = 0;

		//Continuously process requests
		while (!remainingRequests.isEmpty()) {
			List<DiskRequest> left = new ArrayList<>();
			List<DiskRequest> right = new ArrayList<>();

			//Split into left/right based on current position and arrival time
			for (DiskRequest request : remainingRequests) {
				if (request.getTimeOfArrival() <= currentTime) {
					if (request.getTrack() < currentPosition) {
						left.add(request);
					} else {
						right.add(request);
					}
				}
			}

			//Debug: Print split lists
			//System.out.println("Current Time: " + currentTime);
			//System.out.println("Left Requests: " + left.stream().map(DiskRequest::getTrack).toList());
			//System.out.println("Right Requests: " + right.stream().map(DiskRequest::getTrack).toList());

			//Process right requests
			for (DiskRequest request : right) {
				int previousPosition = currentPosition;
				currentPosition = request.getTrack();
				int movementTime = Math.abs(currentPosition - previousPosition);
				totalDistance += movementTime;
				currentTime += movementTime; // Update time based on movement
				System.out.println(previousPosition + " -> " + currentPosition + " = " + totalDistance + " (Time: " + currentTime + ")");
				remainingRequests.remove(request);
			}

			// Process left in reverse order
			if (!left.isEmpty()) {
				left.sort(Comparator.comparingInt(DiskRequest::getTrack).reversed()); // Sort left in descending order
				for (DiskRequest request : left) {
					int previousPosition = currentPosition;
					currentPosition = request.getTrack();
					int movementTime = Math.abs(currentPosition - previousPosition);
					totalDistance += movementTime;
					currentTime += movementTime; // Update time based on movement
					System.out.println(previousPosition + " -> " + currentPosition + " = " + totalDistance + " (Time: " + currentTime + ")");
					remainingRequests.remove(request);
				}
			}

			//If no requests were processed, advance time to the next arrival
			if (left.isEmpty() && right.isEmpty() && !remainingRequests.isEmpty()) {
				currentTime = remainingRequests.stream()
						.mapToInt(DiskRequest::getTimeOfArrival)
						.min()
						.orElse(currentTime);
			}
		}

		return totalDistance;
	}

}
