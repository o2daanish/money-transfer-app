package task.money.transfer.domain;

public class Contact {

	private String email;
	private String mobileNo;

	public Contact(String email, String mobileNo) {
		this.email = email;
		this.mobileNo = mobileNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
