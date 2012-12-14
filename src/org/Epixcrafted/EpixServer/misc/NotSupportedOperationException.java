package org.Epixcrafted.EpixServer.misc;

public class NotSupportedOperationException extends Exception {

	private static final long serialVersionUID = -236586273326142713L;

	public NotSupportedOperationException() {
		
	}

	public NotSupportedOperationException(String arg0) {
		super(arg0);
	}

	public NotSupportedOperationException(Throwable arg0) {
		super(arg0);
		arg0.printStackTrace();
	}

	public NotSupportedOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		arg1.printStackTrace();
	}

	public NotSupportedOperationException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		arg1.printStackTrace();
	}

}
