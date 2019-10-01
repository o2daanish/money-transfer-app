package task.money.transfer.domain;

import com.google.gson.JsonElement;

public class ResponseData {
    private ResponseMessage status;
    private String message;
    private JsonElement data;

    public ResponseData(ResponseMessage status) {
        this.status = status;
    }

	
	public ResponseData(ResponseMessage status, String message) {
		this.status = status;
		this.message = message;
	}
	 

	public ResponseData(ResponseMessage status, JsonElement data) {
		this.status = status;
		this.data = data;
	}

    public ResponseMessage getStatus() {
        return status;
    }

    public void setStatus(ResponseMessage status) {
        this.status = status;
    }
    

    public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}