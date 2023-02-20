package com.comunicamosmas.api.service.dto;

public class ClassErrorDTO {
	
	 	private boolean error;

	    private String msm;
	    
	    

		public ClassErrorDTO() {
		 
		}



		public ClassErrorDTO(boolean error, String msm) {
			 
			this.error = error;
			this.msm = msm;
		}



		public boolean isError() {
			return error;
		}



		public void setError(boolean error) {
			this.error = error;
		}



		public String getMsm() {
			return msm;
		}



		public void setMsm(String msm) {
			this.msm = msm;
		}



		@Override
		public String toString() {
			return "ClassErrorDTO [error=" + error + ", msm=" + msm + "]";
		}
		

	     
	    
	    

}
