package task.money.transfer.api.defination;

/**
 * 
 * @author Danish Ahmad
 *
 */
public enum APIConstants {
	
	RESPONSE_TYPE("application/json"),
	NOT_FOUND("404");
	
	private final String type;
    private APIConstants(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
