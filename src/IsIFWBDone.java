public class IsIFWBDone{
	private boolean IfDone;
	private boolean WbDone;
	public IsIFWBDone(boolean ifDone, boolean wbDone) {
		IfDone = ifDone;
		WbDone = wbDone;
	}
	public boolean isIfDone() {
		return IfDone;
	}
	public void setIfDone(boolean ifDone) {
		IfDone = ifDone;
	}
	public boolean isWbDone() {
		return WbDone;
	}
	public void setWbDone(boolean wbDone) {
		WbDone = wbDone;
	}
}