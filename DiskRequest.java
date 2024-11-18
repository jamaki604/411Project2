
public class DiskRequest {

	private int track;
	private int timeOfArrival;

	public DiskRequest(int track, int timeOfArrival) {
		super();
		this.track = track;
		this.timeOfArrival = timeOfArrival;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public int getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(int timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

}
