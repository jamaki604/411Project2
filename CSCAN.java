import java.util.ArrayList;
import java.util.Comparator;
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

		//Sort by timeOfArrival first, then track
		List<DiskRequest> sortedRequests = new ArrayList<>(requests);
		sortedRequests.sort(Comparator.comparingInt(DiskRequest::getTimeOfArrival)
				.thenComparingInt(DiskRequest::getTrack));

		//Debug: Print sorted requests
		//System.out.println("Sorted Requests: " + sortedRequests.stream()
		//		.map(r -> "(" + r.getTrack() + ", " + r.getTimeOfArrival() + ")").toList());

		List<DiskRequest> remainingRequests = new ArrayList<>(sortedRequests);
		int currentTime = 0;

		while (!remainingRequests.isEmpty()) {
			List<DiskRequest> left = new ArrayList<>();
			List<DiskRequest> right = new ArrayList<>();

			//Split dynamically based on current position and time
			for (DiskRequest request : remainingRequests) {
				if (request.getTimeOfArrival() <= currentTime) {
					if (request.getTrack() >= currentPosition) {
						right.add(request);
					} else {
						left.add(request);
					}
				}
			}

			//Debug: Print split lists
			//System.out.println("Current Time: " + currentTime);
			//System.out.println("Left Requests: " + left.stream().map(DiskRequest::getTrack).toList());
			//System.out.println("Right Requests: " + right.stream().map(DiskRequest::getTrack).toList());

			//Process right
			right.sort(Comparator.comparingInt(DiskRequest::getTrack));
			for (DiskRequest request : right) {
				int previousPosition = currentPosition;
				currentPosition = request.getTrack();
				int movementTime = Math.abs(currentPosition - previousPosition);
				currentTime += movementTime; // Updates time based on movement
				totalDistance += movementTime;
				System.out.println(previousPosition + " -> " + currentPosition + " = " + totalDistance + " (Time: " + currentTime + ")");
				remainingRequests.remove(request);
			}

			//Jump to disc end
			if (!right.isEmpty()) {
				int previousPosition = currentPosition;
				int movementTime = Math.abs(4999 - previousPosition);
				currentPosition = 4999;
				currentTime += movementTime; // Update time based on movement
				totalDistance += movementTime;
				System.out.println("Jump to 4999: " + totalDistance + " (Time: " + currentTime + ")");
			}

			//Jump to disc start
			if (!left.isEmpty()) {
				int previousPosition = currentPosition;
				int movementTime = Math.abs(previousPosition);
				currentPosition = 0;
				currentTime += movementTime; // Update time based on movement
				totalDistance += movementTime;
				System.out.println("Jump to 0: " + totalDistance + " (Time: " + currentTime + ")");

				// Process left
				left.sort(Comparator.comparingInt(DiskRequest::getTrack));
				for (DiskRequest request : left) {
					previousPosition = currentPosition;
					currentPosition = request.getTrack();
					movementTime = Math.abs(currentPosition - previousPosition);
					currentTime += movementTime; // Update time based on movement
					totalDistance += movementTime;
					System.out.println(previousPosition + " -> " + currentPosition + " = " + totalDistance + " (Time: " + currentTime + ")");
					remainingRequests.remove(request);
				}
			}

			// Advance time to the next arriving request if no requests are available
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
