package com.comunicamosmas.api.service.dto;

public class ClassResponseHablameDTO {

	 private String status;

	    private String account;

	    private String smsId;

	    private String execution_time;

	    private String ip;

		public ClassResponseHablameDTO() {
		 
		}

		public ClassResponseHablameDTO(String status, String account, String smsId, String execution_time, String ip) {
			 
			this.status = status;
			this.account = account;
			this.smsId = smsId;
			this.execution_time = execution_time;
			this.ip = ip;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getSmsId() {
			return smsId;
		}

		public void setSmsId(String smsId) {
			this.smsId = smsId;
		}

		public String getExecution_time() {
			return execution_time;
		}

		public void setExecution_time(String execution_time) {
			this.execution_time = execution_time;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		@Override
		public String toString() {
			return "ClassResponseHablameDTO [status=" + status + ", account=" + account + ", smsId=" + smsId
					+ ", execution_time=" + execution_time + ", ip=" + ip + "]";
		}
		
		
		

}
