package com.comunicamosmas.api.service.dto;

public  class PaymentOnlineDTO {
    
    public static class Auth
    {
        private String url;

        private String login;

        private String token;
        

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        
    }

    public static class PagosOnline{
        
    }
}
