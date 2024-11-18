import java.util.List;

public interface IDiskAlgorithm {

	/**
	 * @param requests - List of disk requests to be serviced
	 * @param headPosition - The initial position of the head
	 * @return The total amount of head movement required 
	 */
	public int calculateDistance(List<DiskRequest> requests, int headPosition);
	
}
