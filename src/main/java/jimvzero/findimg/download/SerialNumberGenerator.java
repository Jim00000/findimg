package jimvzero.findimg.download;

public class SerialNumberGenerator {

	private int serialNumber;

	public SerialNumberGenerator() {
		this(0);
	}

	public SerialNumberGenerator(int start) {
		serialNumber = start;
	}

	public synchronized final int getSerialNumber() {
		return serialNumber++;
	}
	
	public synchronized final String getSerialNumberStrring() {
		return Integer.toString(getSerialNumber());
	}
	
}
