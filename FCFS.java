import java.util.List;

/**
 * This class will implement the First Come First Serve 
 * disk scheduling algorithm.
 * 
 * @author ...
 */
public class FCFS implements IDiskAlgorithm {

	@Override
	public int calculateDistance(List<DiskRequest> requests, int headPosition) {
		int totalDistance = 0;
		int currentPosition = headPosition;

		for (DiskRequest request : requests) {
			totalDistance += Math.abs(request.getTrack() - currentPosition);
			currentPosition = request.getTrack();
		}

		return totalDistance;
	}

}
