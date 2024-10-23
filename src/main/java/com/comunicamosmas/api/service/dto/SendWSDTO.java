package com.comunicamosmas.api.service.dto;

public class SendWSDTO {

    public static class MsmPriority{
        private String toNumber;

        private String sms;

        private String flash;

        private String account;

        private String apiKey;

        private String token;

        public String getToNumber() {
            return toNumber;
        }

        public void setToNumber(String toNumber) {
            this.toNumber = toNumber;
        }

        public String getSms() {
            return sms;
        }

        public void setSms(String sms) {
            this.sms = sms;
        }

        public String getFlash() {
            return flash;
        }

        public void setFlash(String flash) {
            this.flash = flash;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        
    }
    
    public static class AccessKey{

        private String account;

        private String apiKey;

        private String url;

        private String token;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

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

        
    }
}
